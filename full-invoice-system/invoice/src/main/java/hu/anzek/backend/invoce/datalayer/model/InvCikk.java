/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.stereotype.Repository;


/**
 *
 * @author User
 */
@Repository
@Entity
public class InvCikk {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String megnevezes;
    private String mennyegys;
    private Double afa_kulcs;
    private Double egyseg_ar;
    private Integer keszleten_van;    

    public InvCikk() {
    }

    public InvCikk(Long id,
                   String megnevezes,
                   String mennyegys,
                   Double afa_kulcs,
                   Double egyseg_ar,
                   Integer keszleten_van) {
        this.id = id;
        this.megnevezes = megnevezes;
        this.mennyegys = mennyegys;
        this.afa_kulcs = afa_kulcs;
        this.egyseg_ar = egyseg_ar;
        this.keszleten_van = keszleten_van;
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

    public String getMennyegys() {
        return mennyegys;
    }

    public void setMennyegys(String mennyegys) {
        this.mennyegys = mennyegys;
    }

    public Double getAfa_kulcs() {
        return afa_kulcs;
    }

    public void setAfa_kulcs(Double afa_kulcs) {
        this.afa_kulcs = afa_kulcs;
    }

    public Double getEgyseg_ar() {
        return egyseg_ar;
    }

    public void setEgyseg_ar(Double egyseg_ar) {
        this.egyseg_ar = egyseg_ar;
    }

    public Integer getKeszleten_van() {
        return keszleten_van;
    }

    public void setKeszleten_van(Integer keszleten_van) {
        this.keszleten_van = keszleten_van;
    }
}
