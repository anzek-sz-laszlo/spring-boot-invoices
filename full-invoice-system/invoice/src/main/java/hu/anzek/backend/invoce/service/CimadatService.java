/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.service;


import hu.anzek.backend.invoce.datalayer.dto.CimadatDto;
import hu.anzek.backend.invoce.datalayer.mapper.CimadatMapper;
import hu.anzek.backend.invoce.datalayer.model.Cimadat;
import hu.anzek.backend.invoce.datalayer.repository.CimadatRepository;
import hu.anzek.backend.invoce.service.interfaces.TorzsadatokCrudAndPrintService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


/**
 *
 * @author User
 */
@Service
public class CimadatService implements TorzsadatokCrudAndPrintService<Cimadat> {

    private final CimadatRepository cimRepo;
    private final CimadatMapper cimMapper;
    private final HelysegnevTarService helysegService;
    @Autowired
    public CimadatService(CimadatRepository cimRepo,
                          HelysegnevTarService helysegService,
                          CimadatMapper cimMapper) {
        this.cimRepo = cimRepo;
        this.cimMapper = cimMapper;
        this.helysegService = helysegService;
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
    @Transactional
    public Cimadat create(Cimadat cimadat) {
        if (cimadat.getId() == null) {   
            // a cimadat -> helysegnevTar tranzitív függőségét kell legelsőként rendezni
            cimadat.setTelepules(this.helysegService.helysegnevTarSave(cimadat));
            return this.cimRepo.save(cimadat);
        }
        return null;        
    }

    @Override
    @Transactional
    public Cimadat update(Cimadat cimadat) {
        if ((cimadat.getId() != null) && (this.cimRepo.existsById(cimadat.getId()))) {    
            // a cimadat -> helysegnevTar tranzitív függőségét kell legelsőként rendezni
            cimadat.setTelepules(this.helysegService.helysegnevTarSave(cimadat));
            return this.cimRepo.save(cimadat);
        }
        return null;
    }

    @Override
    @Transactional
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

    @Override
    public Cimadat getById(String id) {
        return this.getById(Long.getLong(id));
    }
        
    public Cimadat entitasKiolvaso(ResponseEntity<CimadatDto> response){
        if(response == null){
            return null;
        }
        return this.cimMapper.dtoToCimadat(response.getBody());
    }

    public List<Cimadat> listaKiolvaso(ResponseEntity<List<CimadatDto>> response){
        if(response == null){
            return null;
        }
        return this.cimMapper.dtosToCimadats(response.getBody());
    }            
}
