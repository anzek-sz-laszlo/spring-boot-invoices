/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.repository;


import hu.anzek.backend.invoce.datalayer.model.Megrendeles;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
