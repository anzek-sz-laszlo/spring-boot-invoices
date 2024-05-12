/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.service;


import hu.anzek.backend.invoce.datalayer.dto.PartnerCimHelysegDto;
import hu.anzek.backend.invoce.datalayer.mapper.PartnerCimMapper;
import hu.anzek.backend.invoce.datalayer.model.Cimadat;
import hu.anzek.backend.invoce.datalayer.model.Partner;
import hu.anzek.backend.invoce.datalayer.repository.PartnerRepository;
import hu.anzek.backend.invoce.service.interfaces.TorzsadatokCrudAndPrintService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author User
 */
@Service
public class PartnerService implements TorzsadatokCrudAndPrintService<Partner> {
    
    // JPA entitások "entityManager" -el való használathoz való injektor 
    // amely önállóan használható(az Autowired helyett)!
    @PersistenceContext    
    private EntityManager entityManager;    
    
    private final PartnerRepository partnerRepo;
    @Autowired
    private final CimadatService cimService;
    private final PartnerCimMapper cimMapper;
    
    @Autowired
    public PartnerService(PartnerRepository partnerRepo,
                          CimadatService cimService,
                          PartnerCimMapper cimMapper) {
        this.partnerRepo = partnerRepo;
        this.cimService = cimService;
        this.cimMapper = cimMapper;
    }
  
    public List<PartnerCimHelysegDto> getAllFulDtoList() {
   
        // ez JPQL szintaxissal -
        String jpql = "SELECT new PartenerCimHelysegDto("
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
                + ") "
                + " FROM Partner p JOIN p.partner_cim c JOIN c.telepules h"; 
        // és a JPQL lekérdezéssel:
        // Query query = entityManager.createQuery(jpql);
    
        // Ez natív MySql szintaxissal és lekérdezéssel:
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
        Optional<Partner> optPart = this.partnerRepo.findById(id);
        if(optPart.isPresent()){
            System.out.println("XX2: A KIOLVASOTT PARTNER ADATAI : \n" + optPart.get().toString() );
        }
        return this.partnerRepo.findById(id).orElse(null);
    }

    @Override
    public Partner create(Partner entity) {
        if (entity.getId() == null) {     
            return this.partnerRepo.save(entity);
        }
        return null;        
    }

    @Override
    public Partner update(Partner entity) {
        if ((entity.getId() != null) && (this.partnerRepo.existsById(entity.getId()))) {    
            return this.partnerRepo.save(entity);
        }
        return null;
    }

    @Override
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
            if(partner != null){                
                // kimeppeljük a címadatot a partner entitásból:
                Cimadat cim = this.cimMapper.setCimadatFromPartner(partner);
                // adatbázisba mentjük és rögvest vissza is olvassuk, hogy az "id" -je meglegyen!    
                cim = this.cimService.create(cim);
                // beírjuk a "cimadat_id" -t a partner entitásba (valójában átírjuk a teljes címet:
                partner = this.cimMapper.setPartnerCimFromCimadat(cim, partner);
                // a fenti művelet ezzel az alábbival equivalens, de ha már megírtuk jó az úgy:
                // partner.setPartner_cim(cim); 
                return this.create(partner);                
            }else{
                return null;
            }
        }else{
            // módosítás végrehajtása:
            return null;
        }
    }
}
