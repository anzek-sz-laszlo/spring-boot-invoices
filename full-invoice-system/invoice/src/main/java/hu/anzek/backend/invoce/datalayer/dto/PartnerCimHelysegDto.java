/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.dto;


import hu.anzek.backend.invoce.service.enumeralt.FizetesiModok;
import hu.anzek.backend.invoce.service.enumeralt.PartnerStatus;
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
    private String partner_egyeb_info;
    
@Override
    public String toString(){      
        return  "Partner { \n" + 
                " - Id: " + this.partner_id + ", \n" + 
                " - megnevezes: " + this.partner_megnevezes + ", \n" +
                " - adoszam: " + this.partner_adoszam + ", \n" +
                " - kozossegi_asz: " + this.partner_kozossegi_asz + ", \n" +
                " - vevo_szallito: " + PartnerStatus.values()[this.partner_vevo_szallito] + ", \n" +
                " - fizmod: " + FizetesiModok.values()[this.partner_fizmod] + ", \n" +
                " - egyebInfo: " + this.partner_egyeb_info + ", \n" +
                " - cimadat { \n" +
                "            - id: " + this.cimadat_id + ", \n" +
                "            - helyseg { \n" +
                "                       - irszam: " + this.helyseg_irszam + ", \n" +   
                "                       - helyseg: " + this.helyseg_helyseg + ", \n" +  
                "                      }, \n" +
                "            - utca: " + this.cimadat_utca + ", \n" +
                "            - kozterulet: " + this.cimadat_kozterulet + ", \n" +
                "            - hazszam: " + this.cimadat_hazszam + ", \n" +
                "           } \n" +  
                "}\n";
    }    
        
    public void toConsol(String param){
        System.out.println((param != null ? param : "")+ this.toString());        
    }    
}
