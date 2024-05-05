/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


/**
 *
 * @author User
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class PartnerCimHelysegDto {
    private Long partner_id;
    private String partner_megnevezes;
    private String partner_adoszam;
    private String partner_kozossegi_asz;
    private int partner_vevo_szallito;
    private int partner_fizmod;
    private Long cimadat_id;
    private String helyseg_irszam;
    private String helyseg_helyseg;
    private String cimadat_utca;
    private String cimadat_kozterulet;
    private String cimadat_hazszam;
}
