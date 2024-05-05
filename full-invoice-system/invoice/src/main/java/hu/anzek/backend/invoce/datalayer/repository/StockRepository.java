/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.repository;


import hu.anzek.backend.invoce.datalayer.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 *
 * @author User
 */
@Repository
public interface StockRepository extends JpaRepository<Stock, Long>{    
}
