/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.repository;


import hu.anzek.backend.invoce.datalayer.model.Cimadat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 *
 * @author User
 */
@Repository
public interface CimadatRepository extends JpaRepository<Cimadat,Long> {    
    
    @Query(value = "SELECT * FROM cimadat WHERE helyseg_irszam = :irszam", nativeQuery = true)
    List<Cimadat> findByIrszam(@Param("irszam") String irszam);    
}
