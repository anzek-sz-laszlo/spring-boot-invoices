/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.model;


import hu.anzek.backend.invoce.service.enumeralt.FizetesiModok;
import hu.anzek.backend.invoce.service.enumeralt.PartnerStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.springframework.stereotype.Repository;


/**
 * "lombok" nyelvi kiegészítés
 * @author User
 */
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Repository
@Entity
public class Partnerek {
        
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String megnevezes;
    private String adoszam;
    private String kozossegi_asz;
    // vevő,szallító,mindkettő
    private PartnerStatus vevo_szallito;
    @ManyToOne
    private Cimadat partner_cim;  
    private FizetesiModok fizmod;
    private String egyeb_info;

    public Partnerek() {
    }

    public Partnerek(Long id,
                     String megnevezes,
                     String adoszam,
                     String kozossegi_asz,
                     PartnerStatus vevo_szallito,
                     Cimadat partner_cim,
                     FizetesiModok fizmod,
                     String egyeb_info) {
        this.id = id;
        this.megnevezes = megnevezes;
        this.adoszam = adoszam;
        this.kozossegi_asz = kozossegi_asz;
        this.vevo_szallito = vevo_szallito;
        this.partner_cim = partner_cim;
        this.fizmod = fizmod;
        this.egyeb_info = egyeb_info;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMegnevezes() {
        return megnevezes;
    }

    public void setMegnevezes(String megnevezes) {
        this.megnevezes = megnevezes;
    }

    public String getAdoszam() {
        return adoszam;
    }

    public void setAdoszam(String adoszam) {
        this.adoszam = adoszam;
    }

    public String getKozossegi_asz() {
        return kozossegi_asz;
    }

    public void setKozossegi_asz(String kozossegi_asz) {
        this.kozossegi_asz = kozossegi_asz;
    }

    public PartnerStatus getVevo_szallito() {
        return vevo_szallito;
    }

    public void setVevo_szallito(PartnerStatus vevo_szallito) {
        this.vevo_szallito = vevo_szallito;
    }

    public Cimadat getPartner_cim() {
        return partner_cim;
    }

    public void setPartner_cim(Cimadat partner_cim) {
        this.partner_cim = partner_cim;
    }

    public FizetesiModok getFizmod() {
        return fizmod;
    }

    public void setFizmod(FizetesiModok fizmod) {
        this.fizmod = fizmod;
    }

    public String getEgyeb_info() {
        return egyeb_info;
    }

    public void setEgyeb_info(String egyeb_info) {
        this.egyeb_info = egyeb_info;
    }

}
