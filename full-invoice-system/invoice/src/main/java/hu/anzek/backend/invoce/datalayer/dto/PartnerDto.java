/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.dto;


import hu.anzek.backend.invoce.datalayer.model.Cimadat;
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
public class PartnerDto {
    private Long id;    
    private String megnevezes;
    private String adoszam;
    private String kozossegi_asz;
    private PartnerStatus vevo_szallito;
    private Cimadat partner_cim;
    private FizetesiModok fizmod;
    private String egyeb_info;
}
