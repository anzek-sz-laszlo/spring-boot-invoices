/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.service;

import hu.anzek.backend.invoce.datalayer.model.InvCikk;
import hu.anzek.backend.invoce.datalayer.model.Stock;
import hu.anzek.backend.invoce.datalayer.repository.StockRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class StockService {

    private final StockRepository stockRepo;    
    // JPA entitások "entityManager" -el való használathoz való injektor 
    // amely önállóan is használható(az Autowired helyett)!
    @PersistenceContext    
    private final EntityManager entityManager;
    private final InvCikkService cikkService;
    
    @Autowired
    public StockService(StockRepository stockRepo,
                        EntityManager entityManager,
                        InvCikkService cikkService){
        this.stockRepo = stockRepo;
        this.entityManager = entityManager;
        this.cikkService = cikkService;
    }
    
    public Stock getById(Long id) {    
        return this.stockRepo.findById(id).orElse(null);
    }
    
    @Transactional
    public String ujMozgas(Stock raktarMozgas) {
        // Ellenőrzés, hogy a bevétel és kiadás nem lehet negatív
        if (raktarMozgas.getBevetelezett_mennyiseg() < 0 || raktarMozgas.getKiadott_mennyiseg() < 0) {
            return "A bevétel és kiadás nem lehet negatív szám.";
        }
        this.stockRepo.save(raktarMozgas);
        this.osszMennyisegFrissitese(null,raktarMozgas);
        return null;
    }

    // gyorsító tár ürítése!
    @CacheEvict
    // nem ellenőrizhető típusegezőség:
    @SuppressWarnings("unchecked")
    // adatbázis manipulációt jelző annotáció
    @Transactional
    public List<Stock> getStockMozgas(String startDate, String endDate) {
        //  létrehozunk itt egy formázót:
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // ezt használva átalakítjuk a beérkező (sztring) adatokat:
        LocalDateTime startDateTime = LocalDateTime.parse(startDate + " 00:00:00", formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate + " 23:59:59", formatter);
        // JPQL - 
        // Query query = this.entityManager.createQuery("SELECT s FROM stock s WHERE s.mozgas_idopont BETWEEN :startDate AND :endDate", Stock.class);
        Query query = this.entityManager.createNativeQuery("SELECT * FROM invoices176s.stock s WHERE s.mozgas_idopont BETWEEN :startDate AND :endDate", Stock.class);
        query.setParameter("startDate", startDateTime);
        query.setParameter("endDate", endDateTime);
        
        return query.getResultList();
    }

    @Transactional
    public String modositasMozgas(Stock raktarMozgas) {
        Stock original = this.stockRepo.findById(raktarMozgas.getId()).get();
        if( original.getCikk() != null) {          
            // kiegészítjük a cikkadatokal:
            raktarMozgas.setCikk(original.getCikk());
            // Ellenőrzés, arra, hogy a bevétel és kiadás nem lehet negatív
            if (raktarMozgas.getBevetelezett_mennyiseg() < 0 || raktarMozgas.getKiadott_mennyiseg() < 0) {
                // hiba volt, megüzenjük:
                return "A bevétel és kiadás nem lehet negatív szám!";
            }           
            // ha eddig rendben van, akkor mostz már kiíratjuk:
            this.stockRepo.save(raktarMozgas);
            // A cikkadatokban pedig az összevont készletmennyiséget lefrissítjük:
            this.osszMennyisegFrissitese(original ,raktarMozgas); 
            // nem volt hiba:
            return "";
        }
        return "A mozgáshoz tartozó cikkadatok hiányoznak!";
    }

    @Transactional
    public void deletekMozgas(Long id) {
        Stock raktarMozgas =  this.stockRepo.findById(id)
                                            .orElseThrow(() -> new IllegalArgumentException("Nem található raktármozgás ilyen azonosítóval: " + id ) );
        // Törlés:
        this.stockRepo.delete(raktarMozgas);
        // Készletmennyiség frissítése:
        // kissé rafkós a dolog, mert törlésnél ellenkező értelemben kell helyesbíteni az össz készlet mennyiséget:
        raktarMozgas.setBevetelezett_mennyiseg(raktarMozgas.getBevetelezett_mennyiseg() *-1);
        raktarMozgas.setKiadott_mennyiseg(raktarMozgas.getKiadott_mennyiseg() *-1);        
        // Ellenőrzés, arra, hogy a cikk nem null értékű-e?
        if (raktarMozgas.getCikk() != null) {
             // Nem az, akkor most lehet módosítani:
            this.osszMennyisegFrissitese(null, raktarMozgas);
        } 
    }

    public void osszMennyisegFrissitese(Stock original, Stock raktarMozgas) {        
        if(raktarMozgas != null){
            InvCikk cikk = this.cikkService.getById(raktarMozgas.getCikk().getId());
            int updateMenny = cikk.getKeszleten_van();
            if(original != null){
                // visszakorrigáljuk a módosítás előtti állapottal:
                updateMenny -= original.getBevetelezett_mennyiseg();  
                updateMenny += original.getKiadott_mennyiseg();                
            }            
            // korrigáljuk az újbevitellel, vagy a módosítást követő állapottal:
            updateMenny += raktarMozgas.getBevetelezett_mennyiseg(); 
            updateMenny -= raktarMozgas.getKiadott_mennyiseg();
            cikk.setKeszleten_van(updateMenny);
            // kimentjük az sql-be:
            this.cikkService.update(cikk);   
        }
    }

    @SuppressWarnings("unchecked")
    public List<Stock> kiadottNotEmpty() {
        String jpql = "SELECT * FROM invoices176s.stock where kiadott_mennyiseg > 0 order by cikk_id,mozgas_idopont";
        Query query = this.entityManager.createNativeQuery(jpql, Stock.class);        
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Stock> bevetNotEmpty() {
        String jpql = "SELECT * FROM invoices176s.stock where bevetelezett_mennyiseg > 0 order by cikk_id,mozgas_idopont";
        Query query = this.entityManager.createNativeQuery(jpql, Stock.class);
        return query.getResultList();
    }
}

