/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.model;

import hu.anzek.backend.invoce.service.enumeralt.Mozgasok;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Repository;

/**
 *
 * @author User
 */
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Repository
@EntityScan
@Entity(name = "Stock")
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private InvCikk cikk;
    private LocalDateTime mozgas_idopont; 
    private Mozgasok szoveg;
    private int bevetelezett_mennyiseg;
    private int kiadott_mennyiseg; 

    public Stock() {
    }

    public Stock(Long id,
                 InvCikk cikk,
                 LocalDateTime mozgas_idopont,
                 Mozgasok szoveg,
                 int bevetelezett_mennyiseg,
                 int kiadott_mennyiseg) {
        this.id = id;
        this.cikk = cikk;
        this.mozgas_idopont = mozgas_idopont;
        this.szoveg = szoveg;
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

    public LocalDateTime getMozgas_idopont() {
        return mozgas_idopont;
    }

    public void setMozgas_idopont(LocalDateTime mozgas_idopont) {
        this.mozgas_idopont = mozgas_idopont;
    }

    public Mozgasok getSzoveg() {
        return szoveg;
    }

    public void setSzoveg(Mozgasok szoveg) {
        this.szoveg = szoveg;
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
