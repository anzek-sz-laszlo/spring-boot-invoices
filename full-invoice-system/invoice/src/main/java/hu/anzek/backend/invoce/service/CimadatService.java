/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.service;


import hu.anzek.backend.invoce.datalayer.model.Cimadat;
import hu.anzek.backend.invoce.datalayer.repository.CimadatRepository;
import hu.anzek.backend.invoce.service.interfaces.TorzsadatokCrudAndPrintService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author User
 */
@Service
public class CimadatService implements TorzsadatokCrudAndPrintService<Cimadat> {

    private final CimadatRepository cimRepo;

    @Autowired
    public CimadatService(CimadatRepository cimRepo) {
        this.cimRepo = cimRepo;
    }
    
    @Override
    public List<Cimadat> getAll() {
        return this.cimRepo.findAll();
    }

    @Override
    public Cimadat getById(Long id) {
        return this.cimRepo.findById(id).orElse(null);
    }

    @Override
    public Cimadat create(Cimadat entity) {
        if (entity.getId() == null) {     
            return this.cimRepo.save(entity);
        }
        return null;        
    }

    @Override
    public Cimadat update(Cimadat entity) {
        if ((entity.getId() != null) && (this.cimRepo.existsById(entity.getId()))) {    
            return this.cimRepo.save(entity);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        if (id != null){
            if (this.cimRepo.existsById(id)) { 
                this.cimRepo.deleteById(id);
                if ( ! this.cimRepo.existsById(id)) { 
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<String> printFullList() {
        // Implementáció a teljes lista nyomtatásához
        return null;
    }

    @Override
    public List<String> printEntityDetails(Long id) {
        // Implementáció a konkrét entitás részleteinek nyomtatásához
        return null;
    }

    public List<Cimadat> getCimekByIrszam(String irszam) {
        return this.cimRepo.findByIrszam(irszam);
    }
}
