/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.service;


import hu.anzek.backend.invoce.datalayer.dto.PartnerCimHelysegDto;
import hu.anzek.backend.invoce.datalayer.mapper.CimadatMapper;
import hu.anzek.backend.invoce.datalayer.mapper.PartnerCimMapper;
import hu.anzek.backend.invoce.datalayer.mapper.PartnerMapper;
import hu.anzek.backend.invoce.datalayer.mapper.PartnerViewMapper;
import hu.anzek.backend.invoce.datalayer.model.Cimadat;
import hu.anzek.backend.invoce.datalayer.model.Partner;
import hu.anzek.backend.invoce.datalayer.repository.PartnerRepository;
import hu.anzek.backend.invoce.service.interfaces.TorzsadatokCrudAndPrintService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author User
 */
@Service
public class PartnerService implements TorzsadatokCrudAndPrintService<Partner> {
   // JPA entitások "entityManager" -el való használathoz való injektor 
    // amely önállóan használható(az Autowired helyett)!
    @PersistenceContext    
    private final EntityManager entityManager;
    private final PartnerRepository partnerRepo;
    private final CimadatService cimService;
    private final PartnerViewMapper viewMapper;
    private final PartnerCimMapper partnerbolCimMapper;

    @Autowired
    public PartnerService(EntityManager entityManager,
                          PartnerRepository partnerRepo,
                          CimadatService cimService,
                          PartnerViewMapper viewMapper,
                          PartnerMapper partnerMapper,
                          CimadatMapper cimadatMapper,
                          PartnerCimMapper partnerbolCimMapper) {
        this.entityManager = entityManager;
        this.partnerRepo = partnerRepo;
        this.cimService = cimService;
        this.viewMapper = viewMapper;
        this.partnerbolCimMapper = partnerbolCimMapper;
    }
    
    public List<PartnerCimHelysegDto> getAllFulDtoList() {

        // Ez natív MySql szintaxissal és lekérdezéssel:
        String jpql;
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
                + "     c.hazszam AS cimadat_hazszam, "
                + "     p.egyeb_info AS partner_egyeb_info "
                + " FROM partner p "
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
    public List<Partner> getAll() {
        return this.partnerRepo.findAll();
    }

    @Override
    public Partner getById(Long id) {        
        return this.partnerRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Partner create(Partner entity) {
        if((entity == null) || (entity.getId() != null)) {
            return null;
        }        
        return this.partnerRepo.save(entity);        
    }

    @Override
    @Transactional
    public Partner update(Partner entity) {
        if((entity == null) || (entity.getId() != null)) {
            return null;
        }        
        if (this.partnerRepo.existsById(entity.getId())) {    
            return this.partnerRepo.save(entity);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if (id != null){
            if (this.partnerRepo.existsById(id)) { 
                this.partnerRepo.deleteById(id);
                if ( ! this.partnerRepo.existsById(id)) { 
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

    public Partner partnerGrafMentes(boolean ujBevitel, Partner partner) {
        if(ujBevitel){
            // újbevitel végrehajtása:
            if((partner != null) &&(partner.getId() == null)){    
                if(this.partnerSave(partner)) return partner; else return null;               
            }else{
                return null;
            }
        }else{
            // módosítás végrehajtása:
            if((partner != null) && (partner.getId() != null) && (this.getById(partner.getId()) != null)){ 
                if (this.partnerSave(partner)){         
                    return partner;
                }
            }
        }
        return null;
    }
    
    // gyorsítótár ürítése (itt, úgy, mint a gráf csúcspontján!)
    @CacheEvict 
    // a tranzakció kezelés ezen hívások alatti mindegyik tranzakció
    //   egy perzisztenci kontextusban (más néven tranzakciós hatókörben, tranazkciós proxiben) lesz kezelve
    // - ha Exception történik a a kontextus RollBack/Revert -el zárul be - vissza vonással, máskülönben Apply -al, elfogadással
    @Transactional
    private boolean partnerSave(Partner partner) {
        // 1, kimeppeljük a címadatot a partner entitásból (hogy előszőr ezt, külön, lementhessük)
        //      mert akkor lesz ID -je, amit visszaírhatunk a partner entitásba...
        Cimadat cim = this.partnerbolCimMapper.setCimadatFromPartner(partner);
        
        // a címhez adhatunk létező címkódot (id-t) -ha már létező címmel vittük fel a partnert, 
        // vagy azzal módosítottuk:
        // Ha a címkód "null", akkor újként mentjük és rögvest vissza is olvassuk, hogy az "id" -je meglegyen!            
        if(cim.getId() == null) cim = this.cimService.create(cim);
        else cim = this.cimService.update(cim);
        
        if (cim != null) {
            cim.toConsol("Sikeres Cimadat adatbazis mentes: ");
            // 3, átírjuk a "cimadat_id" -t a partner entitásba (valójában átírjuk bele a teljes címet):
            // partner = this.partnerbolCimMapper.setPartnerCimFromCimadat(cim, partner);
            partner.setPartner_cim(cim);
            // 4, kimentjuk adatbazisba:
            partner = this.partnerRepo.save(partner);
            return true;
        } else {
            System.out.println("Cimadat == null - adatmentes sikertelen");
        }
        return false;
    }
    
    public PartnerCimHelysegDto createFromDto(PartnerCimHelysegDto createdPartner) {
        return this.viewMapper
                   .partnerToDto(this.partnerGrafMentes(true,this.viewMapper.dtoToPartner(createdPartner)));        
    } 
        
    public PartnerCimHelysegDto updateFromDto(PartnerCimHelysegDto updatedPartner) {
        return this.viewMapper
                   .partnerToDto(this.partnerGrafMentes(false,this.viewMapper.dtoToPartner(updatedPartner)));        
    }

    @Override
    public Partner getById(String id) {
        return this.getById(Long.getLong(id));
    }
}
