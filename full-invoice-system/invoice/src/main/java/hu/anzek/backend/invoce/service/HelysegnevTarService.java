/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.service;


import hu.anzek.backend.invoce.datalayer.model.HelysegnevTar;
import hu.anzek.backend.invoce.datalayer.repository.HelysegnevTarRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author User
 */
@Service
public class HelysegnevTarService {
    
    @Autowired
    HelysegnevTarRepo nevtarRepo;

    public List<HelysegnevTar> getAll() {
        return this.nevtarRepo.findAll();
    }
    
}
