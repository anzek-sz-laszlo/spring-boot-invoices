/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hu.anzek.backend.invoce.service.interfaces;


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
    T create(T entity);
    T update(T entity);
    boolean delete(Long id);
    
    // nyomtatások:
    List<String> printFullList();
    List<String> printEntityDetails(Long id);
}
