/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.dto;


/**
 *
 * @author User
 */
public class HelysegnevTarDto {
    private String irszam;
    private String helyseg;

    public HelysegnevTarDto() {
    }

    public HelysegnevTarDto(String irszam,
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