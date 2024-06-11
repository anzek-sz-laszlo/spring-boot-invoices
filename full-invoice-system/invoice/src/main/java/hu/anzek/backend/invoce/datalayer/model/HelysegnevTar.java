/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.Serializable;
import org.springframework.stereotype.Repository;


/**
 *
 * @author User
 */
@Repository
@Entity
public class HelysegnevTar implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String irszam;
    private String helyseg;

    public HelysegnevTar() {
    }

    public HelysegnevTar(String irszam,
                         String helyseg) {
        this.irszam = irszam;
        this.helyseg = helyseg;
    }

    public String getIrszam() {
        return irszam;
    }

    public void setIrszam(String irszam) {
        this.irszam = irszam;
    }

    public String getHelyseg() {
        return helyseg;
    }

    public void setHelyseg(String helyseg) {
        this.helyseg = helyseg;
    }
    
}
