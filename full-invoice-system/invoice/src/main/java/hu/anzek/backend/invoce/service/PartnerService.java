/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.service;


import hu.anzek.backend.invoce.datalayer.dto.PartnerCimHelysegDto;
import hu.anzek.backend.invoce.datalayer.model.Partnerek;
import hu.anzek.backend.invoce.datalayer.repository.PartnerRepository;
import hu.anzek.backend.invoce.service.interfaces.TorzsadatokCrudAndPrintService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author User
 */
@Service
public class PartnerService implements TorzsadatokCrudAndPrintService<Partnerek> {
    
    // JPA entitások "entityManager" -el való használathoz való injektor 
    // amely önállóan használható(az Autowired helyett)!
    @PersistenceContext    
    private EntityManager entityManager;    
    private final PartnerRepository repository;
    
    @Autowired
    public PartnerService(PartnerRepository partnerRepo) {
        this.repository = partnerRepo;
    }
  
    public List<PartnerCimHelysegDto> getAllFulDtoList() {
        String jpql = "SELECT NEW hu.anzek.backend.invoce.datalayer.dto.PartenerCimHelysegDto(p.id, p.megnevezes, p.adoszam, p.kozossegi_asz, p.vevo_szallito, c.id, h.irszam, h.helyseg, c.utca, c.kozterulet, c.hazszam) " +
                      " FROM Partnerek p JOIN p.partner_cim c JOIN c.telepules h";       
        // Query query = entityManager.createQuery(jpql);
        
        jpql = "SELECT "
                + "     p.id AS partner_id, "
                + "     p.megnevezes AS partner_megnevezes, "
                + "     p.adoszam AS partner_adoszam, "
                + "     p.kozossegi_asz AS partner_kozossegi_asz, "
                + "     p.vevo_szallito AS partner_vevo_szallito, "
                + "     p.fizmod AS partner_fizmod, "
                + "     c.id AS cimadat_id, "
                + "     h.irszam AS helyseg_irszam, "
                + "     h.helyseg AS helyseg_helyseg, "
                + "     c.utca AS cimadat_utca, "
                + "     c.kozterulet AS cimadat_kozterulet, "
                + "     c.hazszam AS cimadat_hazszam "
                + " FROM partnerek p "
                + "     LEFT JOIN cimadat c "
                + "         ON p.partner_cim_id = c.id "
                + "     LEFT JOIN helysegnev_tar h "
                + "         ON c.helyseg_irszam = h.irszam";
        Query query = this.entityManager.createNativeQuery(jpql, PartnerCimHelysegDto.class);
        @SuppressWarnings("unchecked")
        List<PartnerCimHelysegDto> resultList = query.getResultList();
        
        // Vegyük ki a null, null, null értékeket és írjuk bele: "nincs címadat":
        resultList.stream()
                  .map(dto -> { if (dto.getCimadat_id() == null) {                                    
                                    dto.setCimadat_id(0L);
                                    dto.setCimadat_utca(" ");
                                    dto.setCimadat_kozterulet(" ");
                                    dto.setCimadat_hazszam(" ");
                                    dto.setHelyseg_irszam("nincs rögzítve címadat!");
                                    dto.setHelyseg_helyseg(" ");
                                }
                                return dto;
                               } )
                  .collect(Collectors.toList());                
        return resultList;
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
