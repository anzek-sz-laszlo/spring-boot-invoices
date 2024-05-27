/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hu.anzek.backend.invoce.service.interfaces;


import jakarta.transaction.Transactional;
import java.util.List;


/**
 * Generikus interfész a törzsadatok kezeléséhez<br>
 * @author User Anzek to 176 backend<br>
 * @param <T> a törzstípusa<br>
 */
public interface TorzsadatokCrudAndPrintService<T> {    
    // törzskezelés:
    List<T> getAll();
    T getById(Long id);
    T getById(String id);
    @Transactional
    T create(T entity);
    @Transactional
    T update(T entity);
    @Transactional
    boolean delete(Long id);
    
    // nyomtatások:
    List<String> printFullList();
    List<String> printEntityDetails(Long id);
}
