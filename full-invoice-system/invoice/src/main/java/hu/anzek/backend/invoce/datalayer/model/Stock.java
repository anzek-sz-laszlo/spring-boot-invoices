/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.springframework.stereotype.Repository;

/**
 *
 * @author User
 */
@Repository
@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private InvCikk cikk;
    
    private int bevetelezett_mennyiseg;
    private int kiadott_mennyiseg;

    public Stock() {
    }
    
    public Stock(Long id,
                 InvCikk cikk,
                 int bevetelezett_mennyiseg,
                 int kiadott_mennyiseg) {
        this.id = id;
        this.cikk = cikk;
        this.bevetelezett_mennyiseg = bevetelezett_mennyiseg;
        this.kiadott_mennyiseg = kiadott_mennyiseg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InvCikk getCikk() {
        return cikk;
    }

    public void setCikk(InvCikk cikk) {
        this.cikk = cikk;
    }

    public int getBevetelezett_mennyiseg() {
        return bevetelezett_mennyiseg;
    }

    public void setBevetelezett_mennyiseg(int bevetelezett_mennyiseg) {
        this.bevetelezett_mennyiseg = bevetelezett_mennyiseg;
    }

    public int getKiadott_mennyiseg() {
        return kiadott_mennyiseg;
    }

    public void setKiadott_mennyiseg(int kiadott_mennyiseg) {
        this.kiadott_mennyiseg = kiadott_mennyiseg;
    }    
}
