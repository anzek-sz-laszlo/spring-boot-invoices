/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


/**
 *
 * @author User
 */
@Entity
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    
    // számlaszám
    private String szamlaszam;
    // szallitoi adatok:
    private String szallito_megnevezes;
    private String szallito_adoszam;
    private String szallito_kozossegi_asz;
    private String szallito_irszam;  
    private String szallito_telepules;
    private String szallito_utca;
    private String szallito_kozterulet;
    private String szallito_hazszam;
    // vevő adatok
    private String vevo_megnevezes;
    private String vevo_adoszam;
    private String vevo_kozossegi_asz;
    private String vevo_irszam;  
    private String vevo_telepules;
    private String vevo_utca;
    private String vevo_kozterulet;
    private String vevo_hazszam;
    // a számla hivatkozási alapja 
    // ez egy sima, egy az egyhez kapcsolat!
    // - A fetch "EAGER betöltési stratégia" -> a kapcsolódó "Megrendeles" entitás mindenkor be lesz betöltve!
    // de ez itt csak egy rekord (viszont igaz, ott meg már nem mindegy, hogy a tételei felé hogyan van a kapcsolat szabályozva)!
    @OneToOne(fetch = FetchType.EAGER)
    private Megrendeles megrendeles;
    // A számla tételei:
    // A "mappedBy" attribútum jelzi, hogy a "túloldalon", az "InvoiceItem" ben, van az "invoice" mező, 
    // amely visszahivatkozik erre az Invoice entitásra!
    // róka fogta csuka - csuka fogta róka...
    // - A "cascade" attribútum megadja, hogy milyen műveleteket kell továbbvinni a kapcsolódó entitásokra. 
    // Az "ALL" érték szerint az összes adatbázis művelet átkerül a kapcsolódó "InvoiceItem" entitásokra is. 
    // Így, ha egy Invoice entitást elmentünk, akkor az összes kapcsolódó InvoiceItem entitás is automatikusan mentésre kerül!
    // Ha törölnénk akkor azokat is törli (ha minden jól van paraméterezve)....
    // - A fetch "LAZY betöltési stratégia" -> a kapcsolódó "InvoiceItem" entitások csak akkor lesznek betöltve, 
    // - amikor ténylegesen szükség van rá, vagyis, ha az "tetelek" mező először lekédezzük. Ez csökkenti az adatbázis lekérdezések számát
    // és a memóriahasználatot:
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InvoiceItem> tetelek;
    // a számla dátum adatok:
    private LocalDate keszult;
    private LocalDate fizetesi_hatarido;
    private LocalDate szallitas_napja;
    private String elszamolasi_valuta;
    // érték adatok
    private double netto_ertek;
    private double ado_ertek;
    private double brutto_ertek;
    // egyebek:
    private String megjegyzes;

    public Invoice() {
    }
    
    @Override
    public String toString(){
        String tetelLista = "{ ";
        for(InvoiceItem e : this.tetelek){
            tetelLista += e.toString() + " ";
        }
        tetelLista += " }";
        return "Invoice{ - id = " + this.id + "\n"
                + "        - szamlaszam = " + this.szamlaszam + "\n"
                + "        - -  szallito_megnevezes = " + this.szallito_megnevezes  + "\n"
                + "        - -  szallito_adoszam = " + this.szallito_adoszam  + "\n"
                + "        - -  szallito_kozossegi_asz = " + this.szallito_kozossegi_asz  + "\n"
                + "        - -  szallito_telepules = " + this.szallito_telepules + "\n"
                + "        - -  szallito_utca = " + this.szallito_utca + "\n"
                + "        - -  szallito_kozterulet = " + this.szallito_kozterulet + "\n"
                + "        - -  szallito_hazszam = " + this.szallito_hazszam  + "\n"      
                + "        - -  vevo_megnevezes = " + this.vevo_megnevezes  + "\n"
                + "        - -  vevo_adoszam = " + this.vevo_adoszam  + "\n"
                + "        - -  vevo_kozossegi_asz = " + this.vevo_kozossegi_asz  + "\n"
                + "        - -  vevo_telepules = " + this.vevo_telepules + "\n"
                + "        - -  vevo_utca = " + this.vevo_utca + "\n"
                + "        - -  vevo_kozterulet = " + this.vevo_kozterulet + "\n"
                + "        - -  vevo_hazszam = " + this.vevo_hazszam  + "\n"                    
                + "        - keszult = " + this.keszult + "\n"
                + "        - szallitas_napja = " + this.szallitas_napja + "\n"
                + "        - elszamolasi_valuta = " + this.elszamolasi_valuta + "\n"
                + "        - netto_ertek = " + this.netto_ertek + "\n"
                + "        - ado_ertek = " + this.ado_ertek + "\n"
                + "        - brutto_ertek = " + this.brutto_ertek + "\n"
                + "        - elszamolasi_valuta = " + this.elszamolasi_valuta + "\n"
                + "        - megjegyzes = " + this.megjegyzes + "\n"
                + "        " + tetelLista
                + "     }" + "\n";
    }
    
    public void toConsol(String s){
        System.out.println( s + "\n" + this.toString());
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSzamlaszam() {
        return szamlaszam;
    }

    public void setSzamlaszam(String szamlaszam) {
        this.szamlaszam = szamlaszam;
    }

    public Megrendeles getMegrendeles() {
        return megrendeles;
    }

    public void setMegrendeles(Megrendeles megrendeles) {
        this.megrendeles = megrendeles;
    }

    public String getSzallito_megnevezes() {
        return szallito_megnevezes;
    }

    public void setSzallito_megnevezes(String szallito_megnevezes) {
        this.szallito_megnevezes = szallito_megnevezes;
    }

    public String getSzallito_adoszam() {
        return szallito_adoszam;
    }

    public void setSzallito_adoszam(String szallito_adoszam) {
        this.szallito_adoszam = szallito_adoszam;
    }

    public String getSzallito_kozossegi_asz() {
        return szallito_kozossegi_asz;
    }

    public void setSzallito_kozossegi_asz(String szallito_kozossegi_asz) {
        this.szallito_kozossegi_asz = szallito_kozossegi_asz;
    }

    public String getSzallito_irszam() {
        return szallito_irszam;
    }

    public void setSzallito_irszam(String szallito_irszam) {
        this.szallito_irszam = szallito_irszam;
    }

    public String getSzallito_telepules() {
        return szallito_telepules;
    }

    public void setSzallito_telepules(String szallito_telepules) {
        this.szallito_telepules = szallito_telepules;
    }

    public String getSzallito_utca() {
        return szallito_utca;
    }

    public void setSzallito_utca(String szallito_utca) {
        this.szallito_utca = szallito_utca;
    }

    public String getSzallito_kozterulet() {
        return szallito_kozterulet;
    }

    public void setSzallito_kozterulet(String szallito_kozterulet) {
        this.szallito_kozterulet = szallito_kozterulet;
    }

    public String getSzallito_hazszam() {
        return szallito_hazszam;
    }

    public void setSzallito_hazszam(String szallito_hazszam) {
        this.szallito_hazszam = szallito_hazszam;
    }

    public String getVevo_megnevezes() {
        return vevo_megnevezes;
    }

    public void setVevo_megnevezes(String vevo_megnevezes) {
        this.vevo_megnevezes = vevo_megnevezes;
    }

    public String getVevo_adoszam() {
        return vevo_adoszam;
    }

    public void setVevo_adoszam(String vevo_adoszam) {
        this.vevo_adoszam = vevo_adoszam;
    }

    public String getVevo_kozossegi_asz() {
        return vevo_kozossegi_asz;
    }

    public void setVevo_kozossegi_asz(String vevo_kozossegi_asz) {
        this.vevo_kozossegi_asz = vevo_kozossegi_asz;
    }

    public String getVevo_irszam() {
        return vevo_irszam;
    }

    public void setVevo_irszam(String vevo_irszam) {
        this.vevo_irszam = vevo_irszam;
    }

    public String getVevo_telepules() {
        return vevo_telepules;
    }

    public void setVevo_telepules(String vevo_telepules) {
        this.vevo_telepules = vevo_telepules;
    }

    public String getVevo_utca() {
        return vevo_utca;
    }

    public void setVevo_utca(String vevo_utca) {
        this.vevo_utca = vevo_utca;
    }

    public String getVevo_kozterulet() {
        return vevo_kozterulet;
    }

    public void setVevo_kozterulet(String vevo_kozterulet) {
        this.vevo_kozterulet = vevo_kozterulet;
    }

    public String getVevo_hazszam() {
        return vevo_hazszam;
    }

    public void setVevo_hazszam(String vevo_hazszam) {
        this.vevo_hazszam = vevo_hazszam;
    }

    public List<InvoiceItem> getItems() {
        return tetelek;
    }

    public void setItems(List<InvoiceItem> items) {
        this.tetelek = items;
    }

    public LocalDate getKeszult() {
        return keszult;
    }

    public void setKeszult(LocalDate keszult) {
        this.keszult = keszult;
    }

    public LocalDate getFizetesi_hatarido() {
        return fizetesi_hatarido;
    }

    public void setFizetesi_hatarido(LocalDate fizetesi_hatarido) {
        this.fizetesi_hatarido = fizetesi_hatarido;
    }

    public LocalDate getSzallitas_napja() {
        return szallitas_napja;
    }

    public void setSzallitas_napja(LocalDate szallitas_napja) {
        this.szallitas_napja = szallitas_napja;
    }

    public String getElszamolasi_valuta() {
        return elszamolasi_valuta;
    }

    public void setElszamolasi_valuta(String elszamolasi_valuta) {
        this.elszamolasi_valuta = elszamolasi_valuta;
    }

    public double getNetto_ertek() {
        return netto_ertek;
    }

    public void setNetto_ertek(double netto_ertek) {
        this.netto_ertek = netto_ertek;
    }

    public double getAdo_ertek() {
        return ado_ertek;
    }

    public void setAdo_ertek(double ado_ertek) {
        this.ado_ertek = ado_ertek;
    }

    public double getBrutto_ertek() {
        return brutto_ertek;
    }

    public void setBrutto_ertek(double brutto_ertek) {
        this.brutto_ertek = brutto_ertek;
    }

    public String getMegjegyzes() {
        return megjegyzes;
    }

    public void setMegjegyzes(String megjegyzes) {
        this.megjegyzes = megjegyzes;
    }
    
}
