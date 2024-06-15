/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.repository;


import hu.anzek.backend.invoce.datalayer.model.Megrendeles;
import jakarta.persistence.LockModeType;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 *
 * @author User
 */
@Repository
public interface MegrendelesRepo extends JpaRepository<Megrendeles, Long> {
    
     @Query(value = "SELECT * "
                 + " FROM megrendeles m "
                 + " WHERE (m.zarolva = false) "
                 + "     AND (m.mikorra <= :now) "
                 + "     AND ((m.szamlaszam IS NULL) OR (m.szamlaszam = '')) ORDER BY m.mikorra,m.id", nativeQuery = true)
    /**
     * az éppen most számlázhatók előzetes (teljes, de ellenőrzés nélküli) listáját adja vissza:
     */
    public Optional<List<Megrendeles>> findAllBillable(@Param("now") LocalDate now);  
    
    /**
     * Csak azokat a rekordokat adja vissza, amelyek lockolhatóak (zárolhatóak)
     * ...és itt már azonnal zárol is!
     * @param now az aktuális dátum
     * @return a lekérdezés sikerétül megjelenő listakollekció
     */
    // @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT) // Pesszimista - teljes zárolás, de növeli a verziószámot
    // @Lock(LockModeType.PESSIMISTIC_READ)            // teljes zárolás, de SELECT során ellenőrzi, hogy verzió mező változott-e
    // @Lock(LockModeType.PESSIMISTIC_WRITE)           // teljes zárolás, de UPDATE előtt ellenőrzi, hogy verzió mező változott-e
    @Lock(LockModeType.PESSIMISTIC_WRITE)              // zárolja rekordokat más tranzakciók nem férhetnek hozzá, amíg a zárolás tart.
    @Query(value = "SELECT * "
                 + " FROM megrendeles m "
                 + " WHERE (m.zarolva = false) "
                 + "     AND (m.mikorra <= :now) "
                 + "     AND ((m.szamlaszam IS NULL) OR (m.szamlaszam = '')) ORDER BY m.mikorra,m.id", nativeQuery = true)
    /**
     * az éppen most számlázhatók előzetes (teljes, de ellenőrzés nélküli) listáját adja vissza:
     */
    public Optional<List<Megrendeles>> findAllBillablePessimist(@Param("now") LocalDate now);    
    
    @Query(value = "SELECT * "
                 + " FROM megrendeles m "
                 + " WHERE (m.zarolva = false) "
                 + "     AND (m.mikorra <= :now) "
                 + "     AND ((m.szamlaszam IS NULL) OR (m.szamlaszam = '')) ORDER BY m.mikorra,m.id", nativeQuery = true)
    /**
     * az éppen most számlázhatók előzetes (teljes, de ellenőrzés nélküli) listáját adja vissza:
     */        
    @Lock(LockModeType.OPTIMISTIC)  // Optimista zárolás beleírja a verziószámunkat (ami csak ami futó proigramkódunké)
    //@Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT) // Optimista, de kéynszerítve van, hogy mindig növelje az verziószámunkat     
    //@Lock(LockModeType.READ)      //Optimista - SELECT során ellenőrzi, hogy a rekord verzió mezője változott-e
    //@Lock(LockModeType.WRITE)     //Optimista - UPDATE során ellenőrzi, hogy a rekord verzió mezője változott-e  
    public Optional<List<Megrendeles>> findAllBillableOptimist(@Param("now") LocalDate now);   
}
