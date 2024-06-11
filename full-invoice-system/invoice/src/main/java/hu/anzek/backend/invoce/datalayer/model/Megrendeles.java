/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Beans/Bean.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;


/**
 *
 * @author User
 */
@Entity
@Repository
// A Jackson alapértelmezett beállításai szerint minden publikus getter metódust bevesz a JSON szerializálás során. 
// Tehát ha van egy publikus getter metódusunk, mint a "getReadOnlyTetelek()", 
// akkor a Jackson ezt is automatikusan belefoglalja a JSON válaszba... 
// nagyon vicces fiú!
@JsonIgnoreProperties({"readOnlyTetelek"})
public class Megrendeles implements Serializable {    
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;   
    // a "fetch = EAGER" sql perzisztencia kontextus startégiája : mindenkor be lesznek töltve a partner adatok!
    @ManyToOne(fetch = FetchType.EAGER)
    private Partner szallito; 
    @ManyToOne(fetch = FetchType.EAGER)
    private Partner vevo;
    private LocalDate mikorra; 
    // azt mondjuk alább a Hibernate -nek, hogy meppelj fel egy kapcsolatot a megrendeles táblával
    // és a "mappedBy" attribútum azt jelzi, hogy ez az oldal a kapcsolat inverz oldala, 
    // a "MegrendelesTetel" entitásban van a "Megrendeles" típusú "megrendeles" mező.., 
    // amely visszahivatkozik erre az entitásra...
    @OneToMany(mappedBy = "megrendeles", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("megrendeles")
    @JsonManagedReference
    private List<MegrendelesTetel> tetelek;
    private String megjegyzes;
    private LocalDate rogzitve;
    private boolean lezarva;
    // ha le van számlázva ideírjuk a számlaszámot:
    // így leblokkoljuk a további, vagy az újra leszámlázását!
    private String szamlaszam;
    private boolean zarolva;
    
    public Megrendeles() {
    }

    public Megrendeles(Long id,
                       Partner szallito,
                       Partner vevo, 
                       LocalDate mikorra,
                       String megjegyzes,
                       LocalDate update) {
        this.id = id;
        this.szallito = szallito;
        this.vevo = vevo;
        this.mikorra = mikorra;
        this.megjegyzes = megjegyzes;
        this.rogzitve = update;
        // ezek nem dzükséges beállítások, de így nem dobhat sehol nullabla kivételt!
        this.szamlaszam = "";
        this.lezarva = false;
        this.zarolva = false;
    }

    @Override
    public String toString() {
    String tetelei = "";
        for(MegrendelesTetel e : this.tetelek){
            tetelei += e.toString();
        }
        return "Megrendeles{ - id = " + this.id + "\n"
                + "           - -  szallito_megnevezes = " + this.szallito.getMegnevezes()  + "\n"
                + "           - -  szallito_adoszam = " + this.szallito.getAdoszam()  + "\n"
                + "           - -  szallito_kozossegi_asz = " + this.szallito.getKozossegi_asz()  + "\n"
                + "           - -  szallito_telepules = " + this.szallito.getPartner_cim().getTelepules().getIrszam() + "-" 
                + this.szallito.getPartner_cim().getTelepules().getHelyseg() + "\n"
                + "           - -  szallito_utca = " + this.szallito.getPartner_cim().getKozterulet() + "\n"
                + "           - -  szallito_kozterulet = " + this.szallito.getPartner_cim().getKozterulet() + "\n"
                + "           - -  szallito_hazszam = " + this.szallito.getPartner_cim().getHazszam()  + "\n"      
                + "           - -  vevo_megnevezes = " + this.szallito.getMegnevezes()  + "\n"
                + "           - -  vevo_adoszam = " + this.szallito.getAdoszam()  + "\n"
                + "           - -  vevo_kozossegi_asz = " + this.szallito.getKozossegi_asz() + "\n"
                + "           - -  vevo_telepules = " + this.szallito.getPartner_cim().getTelepules().getIrszam() + "-" 
                + this.szallito.getPartner_cim().getTelepules().getHelyseg() + "\n"
                + "           - -  vevo_utca = " + this.szallito.getPartner_cim().getKozterulet() + "\n"
                + "           - -  vevo_kozterulet = " + this.szallito.getPartner_cim().getKozterulet() + "\n"
                + "           - -  vevo_hazszam = " + this.szallito.getPartner_cim().getHazszam()  + "\n"                    
                + "        - megjegyzes = " + this.megjegyzes + "\n"
                + "        - mikorra (a szallitas elvart napja) = " + this.mikorra + "\n"
                + "        - update (a legutolso hozzaferes) = " + this.rogzitve + "\n"
                + "     " + tetelei
                + "        - isReadyForInvoicing(" + this.isReadyForInvoicing() + ") {"
                + "             - lezarva (szamlazhatova teve?) = " + this.lezarva + "\n"
                + "             - szamlaszam (ha mer szamlazva lett) = " + this.szamlaszam + "\n"
                + "             - zarolva (szamlazas alatt lefoglalt rekord?) = " + this.zarolva + "\n"
                + "        } "
                + "     }" + "\n";
    }
    
    public void toConsol(String s){
        System.out.println( s + " " + this.toString());
    }
    
    /**
     * Lekérdezi, hogy számlázható-e?
     * Csak ekkor : "lezarva && szamlaszam.isEmpty() && zarolva" számlázható!
     * Vagyis:
     * - ha a megrendelés le van zárva
     * - ha üres a számlaszám mező (vagyis még nem volt számlázva)
     * - ha sikeresen zároltuk a rekordot
     * @return igen/sem
     */
    public boolean isReadyForInvoicing(){
        return lezarva && szamlaszam.isEmpty() && zarolva;
    }
    
    /**
     * Manuális beállítással kényszerítjük a számlázhatóságot!
     */
    public void setReadyForInvoicing(){
        lezarva = true;
    }

    /**
     * readOnly tételek kollekciója
     * @return csak olvasható tételek lista-kollekciója
     */
    public List<MegrendelesTetel> getReadOnlyTetelek() {
        return Collections.unmodifiableList(tetelek);
    }

    public boolean isLezarva() {
        return lezarva;
    }

    public void setLezarva(boolean lezarva) {
        this.lezarva = lezarva;
    }
    
    /**
     * read/write tételek kollekciója
     * @return írható és olvasható tételek lista-kollekciója
     */
    @SuppressWarnings("ReturnOfCollectionOrArrayField")
    public List<MegrendelesTetel> getTetelek() {
        return tetelek;
    }
    
    public void setTetelek(List<MegrendelesTetel> tetelek) {
        this.tetelek = tetelek;
        for (MegrendelesTetel tetel : tetelek) {
            tetel.setMegrendeles(this);
        }
    }

    public LocalDate getRogzitve() {
        return rogzitve;
    }

    public void setRogzitve(LocalDate rogzitve) {
        this.rogzitve = rogzitve;
    }
    
    public String getMegjegyzes() {
        return megjegyzes;
    }

    public String getSzamlaszam() {
        return szamlaszam;
    }

    /**
     * beállítjuk a leszámlázott státuszt!
     * @param szamlaszam 
     */
    public void setSzamlaszam(String szamlaszam) {
        this.szamlaszam = szamlaszam;
    }

    public void setMegjegyzes(String megjegyzes) {
        this.megjegyzes = megjegyzes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Partner getSzallito() {
        return szallito;
    }

    public void setSzallito(Partner szallito) {
        this.szallito = szallito;
    }

    public Partner getVevo() {
        return vevo;
    }

    public void setVevo(Partner vevo) {
        this.vevo = vevo;
    }

    public LocalDate getMikorra() {
        return mikorra;
    }

    public void setMikorra(LocalDate mikorra) {
        this.mikorra = mikorra;
    }

    /**
     * párhuzamosítás elkerülése miatt már rekordzárolt-e?
     * @return true/false
     */
    public boolean isZarolva() {
        return zarolva;
    }

    /**
     * párhuzamosítás elkerülése miatti rekordzárolás/feloldás
     * @param zarolva true/false
     */
    public void setZarolva(boolean zarolva) {
        this.zarolva = zarolva;
    }
    
}
