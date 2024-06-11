/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import org.springframework.stereotype.Repository;


/**
 *
 * @author User
 */
@Entity
@Repository
public class MegrendelesTetel implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // ez itt a kapcsolat, azaz a főadatok-tételek közötti vonatkozásban, a "túloldal" -  bár ez valójában csak nézőpont kérdése!
    // Alább egy szabályozott kapcsolatot látunk a számla főadatokkal:
    // a "JoinColumn" annotáció az adatbázisban lévő forgenKey (idegen kulcs) oszlopát jelöli meg a Hibernate számára:
    // az sq táblában a "megrendeles_id" -> jelentese -> "megrendeles.id"!
    // eddig azt láttuk, hogy valójában magától is létrehozza az ORM eszköz, 
    // - de így "magunk/másook/ és a Hibernate" számára is egyértelműen jelezzük a szándékunkat:
    // - a "nullable" false jelentése, hogy minden itteni ("MegrendelesTetel") kapcsolódnia kell egy "Megrendeles" entitáshoz.
    // bár ez explicit módon így van, azért kíván némi körültekintést!    
    @ManyToOne
    @JoinColumn(name = "megrendeles_id", nullable = false)
    @JsonIgnoreProperties("tetelek")
    @JsonBackReference
    private Megrendeles megrendeles;
    // Itt létrehoznuk egy külső kapcsolatot, mert a rendelés teljesüléséig változhat minden a cikkadatokban is!
    @ManyToOne(fetch = FetchType.EAGER)
    private InvCikk inv_cikk;
    private Integer mennyiseg;

    public MegrendelesTetel() {
    }

    public MegrendelesTetel(Megrendeles megrendeles,
                            InvCikk inv_cikk,
                            Integer mennyiseg) {
        this.megrendeles = megrendeles;
        this.inv_cikk = inv_cikk;
        this.mennyiseg = mennyiseg;
    }
    

    @Override
    public String toString(){
        return "MegrendelesTetel{ - inv_cikk = " + this.inv_cikk.getId() + "\n"
                + "                  - rendelesiSzam = " + this.megrendeles.getId() + "\n"
                + "                  - megnevezes = " + this.inv_cikk.getMegnevezes() + "\n"
                + "                  - afa_kulcs = " + this.inv_cikk.getAfa_kulcs() + " % " + "\n"
                + "                  - egyseg_ar = " + this.inv_cikk.getEgyseg_ar() + "HUF " + "\n"
                + "                  - mennyiseg = " + this.mennyiseg + " " + this.inv_cikk.getMennyegys() + "\n"
                + "                }" + "\n";
    }
    
    public void toConsol(String s){
        System.out.println( s + " " + this.toString());
    }    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Megrendeles getMegrendeles() {
        return megrendeles;
    }

    public void setMegrendeles(Megrendeles megrendeles) {
        this.megrendeles = megrendeles;
    }

    public InvCikk getInv_cikk() {
        return inv_cikk;
    }

    public void setInv_cikk(InvCikk inv_cikk) {
        this.inv_cikk = inv_cikk;
    }

    public Integer getMennyiseg() {
        return mennyiseg;
    }

    public void setMennyiseg(Integer mennyiseg) {
        this.mennyiseg = mennyiseg;
    }
    
}
