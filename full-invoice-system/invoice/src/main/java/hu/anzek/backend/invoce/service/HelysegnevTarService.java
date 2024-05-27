/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.service;


import hu.anzek.backend.invoce.datalayer.model.Cimadat;
import hu.anzek.backend.invoce.datalayer.model.HelysegnevTar;
import hu.anzek.backend.invoce.datalayer.repository.HelysegnevTarRepo;
import hu.anzek.backend.invoce.service.interfaces.TorzsadatokCrudAndPrintService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author User
 */
@Service
public class HelysegnevTarService implements TorzsadatokCrudAndPrintService<HelysegnevTar>{
    
    @Autowired
    HelysegnevTarRepo nevtarRepo;

    public List<HelysegnevTar> getAll() {
        return this.nevtarRepo.findAll();
    }

    @Override
    public HelysegnevTar getById(String id) {
        return this.nevtarRepo.getByIrszam(id);
    }

    @Override
    @Transactional
    public HelysegnevTar create(HelysegnevTar entity) {
        return this.nevtarRepo.save(entity);
    }

    @Override
    @Transactional
    public HelysegnevTar update(HelysegnevTar entity) {
        return this.nevtarRepo.save(entity);
    }
    
    public HelysegnevTar helysegnevTarSave(Cimadat cimadat) {
        if((cimadat != null) &&(cimadat.getTelepules().getIrszam() != null)){
            HelysegnevTar telepules = new HelysegnevTar(cimadat.getTelepules().getIrszam(), cimadat.getTelepules().getHelyseg());
            // mivel az Irszam a PrimaryKey (vagyis a kalsszikus id helyett ez van)
            // Ilyenformában itt soha nem fordulhat elő null érték!
            if(this.getById(telepules.getIrszam()) != null){
                return this.update(telepules);
            }else{
                return this.create(telepules);
            }
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return this.delete(String.valueOf(0));
    }
  
    //@Transactional
    public boolean delete(String irszam) {
        this.nevtarRepo.deleteByIrszam(irszam);
        if(this.getById(irszam) == null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<String> printFullList() {
        return null;
    }

    @Override
    public List<String> printEntityDetails(Long id) {
        return null;
    }

    @Override
    public HelysegnevTar getById(Long id) {
        return this.getById(String.valueOf(id));
    }    
}
