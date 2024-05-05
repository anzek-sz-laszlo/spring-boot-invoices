/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.service;

import hu.anzek.backend.invoce.datalayer.model.InvCikk;
import hu.anzek.backend.invoce.datalayer.model.Stock;
import hu.anzek.backend.invoce.datalayer.repository.StockRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class StockService {

    public final StockRepository stockRepo;    
    public final EntityManager entityManager;
    public final InvCikkService cikkService;
    
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
    
    public void ujMozgas(Stock raktarMozgas) {
        // Ellenőrzés, hogy a bevétel és kiadás nem lehet negatív
        if (raktarMozgas.getBevetelezett_mennyiseg() < 0 || raktarMozgas.getKiadott_mennyiseg() < 0) {
            throw new IllegalArgumentException("A bevétel és kiadás nem lehet negatív szám.");
        }
        this.stockRepo.save(raktarMozgas);
        this.osszMennyisegFrissitese(raktarMozgas);
    }

    @SuppressWarnings("unchecked")
    public List<Stock> getStockMozgas(String startDate, String endDate) {
        //  létrehozunk itt egy formázót:
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // ezt használva átalakítjuk a beérkező (sztring) adatokat:
        LocalDateTime startDateTime = LocalDateTime.parse(startDate + " 00:00:00", formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate + " 23:59:59", formatter);

        //Query query = this.entityManager.createQuery("SELECT s FROM stock s WHERE s.mozgas_idopont BETWEEN :startDate AND :endDate");
        Query query = entityManager.createNativeQuery("SELECT * FROM invoices176s.stock s WHERE s.mozgas_idopont BETWEEN :startDate AND :endDate", Stock.class);
        query.setParameter("startDate", startDateTime);
        query.setParameter("endDate", endDateTime);
        
        return query.getResultList();
    }

    public void modositasMozgas(Stock raktarMozgas) {
        if( this.stockRepo.findById(raktarMozgas.getId()).isPresent()) {                
            // Ellenőrzés, arra, hogy a bevétel és kiadás nem lehet negatív
            if (raktarMozgas.getBevetelezett_mennyiseg() < 0 || raktarMozgas.getKiadott_mennyiseg() < 0) {
                throw new IllegalArgumentException("A bevétel és kiadás nem lehet negatív szám.");
            }
            // ha rendben, kiíratjuk:
            this.stockRepo.save(raktarMozgas);
            // Készletmennyiség frissítése:
            this.osszMennyisegFrissitese(raktarMozgas);       
        }
    }

    public void deletekMozgas(Long id) {
        Stock raktarMozgas = stockRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nem található ilyen raktármozgás azonosítóval: " + id));
        // Törlés:
        this.stockRepo.delete(raktarMozgas);
        // Készletmennyiség frissítése:
        // kissé rafkós a dolog, mert törlésnél ellenkező értelemben kell helyesbíteni az össz készlet mennyiséget:
        raktarMozgas.setKiadott_mennyiseg(raktarMozgas.getKiadott_mennyiseg() *-1);
        raktarMozgas.setBevetelezett_mennyiseg(raktarMozgas.getBevetelezett_mennyiseg() *-1);
        // most lehet módosítani:
        this.osszMennyisegFrissitese(raktarMozgas);   
    }

    private void osszMennyisegFrissitese(Stock raktarMozgas) {
        if(raktarMozgas != null){
            InvCikk cikk = this.cikkService.getById(raktarMozgas.getCikk().getId());
            System.out.println("CIKK-ÖSSZKÉSZLET-KORREKCIÓ ELŐTT : " + cikk.getMegnevezes() + " " + cikk.getKeszleten_van());
            cikk.setKeszleten_van(cikk.getKeszleten_van() + raktarMozgas.getBevetelezett_mennyiseg() - raktarMozgas.getKiadott_mennyiseg());
            System.out.println("CIKK-ÖSSZKÉSZLET-KORREKCIÓ UTÁN : " + cikk.getKeszleten_van());
            this.cikkService.update(cikk);
        }
    }
}

