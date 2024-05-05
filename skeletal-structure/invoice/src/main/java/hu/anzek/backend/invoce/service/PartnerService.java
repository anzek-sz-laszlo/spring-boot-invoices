/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.service;


import hu.anzek.backend.invoce.datalayer.model.Partnerek;
import hu.anzek.backend.invoce.datalayer.repository.PartnerRepository;
import hu.anzek.backend.invoce.service.interfaces.TorzsadatokCrudAndPrintService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author User
 */
@Service
public class PartnerService implements TorzsadatokCrudAndPrintService<Partnerek> {

    private final PartnerRepository repository;

    @Autowired
    public PartnerService(PartnerRepository partnerRepo) {
        this.repository = partnerRepo;
    }
    
    @Override
    public List<Partnerek> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Partnerek getById(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Partnerek create(Partnerek entity) {
        if (entity.getId() == null) {     
            return this.repository.save(entity);
        }
        return null;        
    }

    @Override
    public Partnerek update(Partnerek entity) {
        if ((entity.getId() != null) && (this.repository.existsById(entity.getId()))) {    
            return this.repository.save(entity);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        if (id != null){
            if (this.repository.existsById(id)) { 
                this.repository.deleteById(id);
                if ( ! this.repository.existsById(id)) { 
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
}
