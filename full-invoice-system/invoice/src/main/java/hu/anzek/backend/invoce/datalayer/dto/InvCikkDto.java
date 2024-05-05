/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.dto;

/**
 * Egy minta a "lombok" osztálykönyvtár egyik érdekességére<br> *  
 * @author User
 * @Data létrehozza a gettereket/settereket<br>
 * @NoArgsConstructor létrehozza az argumentum nélküli konstruktort<br>
 * @AllArgsConstructor létrehoz egy argumentumsoros konstruktort<br>
 */
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class InvCikkDto {
    
    private Long id;
    
    private String megnevezes;
    private String mennyegys;
    private double afa_kulcs;
    private double egyseg_ar;
    private int keszleten_van;

    public InvCikkDto() {
    }

    public InvCikkDto(Long id,
                      String megnevezes,
                      String mennyegys,
                      double afa_kulcs,
                      double egyseg_ar,
                      int keszleten_van) {
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

    public double getAfa_kulcs() {
        return afa_kulcs;
    }

    public void setAfa_kulcs(double afa_kulcs) {
        this.afa_kulcs = afa_kulcs;
    }

    public double getEgyseg_ar() {
        return egyseg_ar;
    }

    public void setEgyseg_ar(double egyseg_ar) {
        this.egyseg_ar = egyseg_ar;
    }

    public int getKeszleten_van() {
        return keszleten_van;
    }

    public void setKeszleten_van(int keszleten_van) {
        this.keszleten_van = keszleten_van;
    }
    
    
}
