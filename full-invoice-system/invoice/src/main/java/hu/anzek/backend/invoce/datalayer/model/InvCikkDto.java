/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Egy minta a "lombok" osztálykönyvtár egyik érdekességére<br> *  
 * @author User
 * @Data létrehozza a gettereket/settereket<br>
 * @NoArgsConstructor létrehozza az argumentum nélküli konstruktort<br>
 * @AllArgsConstructor létrehoz egy argumentumsoros konstruktort<br>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvCikkDto {
    
    private Long id;
    
    private String megnevezes;
    private String mennyegys;
    private double afa_kulcs;
    private double egyseg_ar;
    private int keszleten_van;
}
