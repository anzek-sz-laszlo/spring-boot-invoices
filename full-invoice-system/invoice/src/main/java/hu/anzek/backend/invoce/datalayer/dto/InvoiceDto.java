/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.dto;


import hu.anzek.backend.invoce.datalayer.model.Megrendeles;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *
 * @author User
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDto {
    Long id;    
    // számlaszám
    private String szamlaszam;
    // szallitoi adatok:
    private String szallito_megnevezes;
    private String szallito_adoszam;
    private String szallito_kozossegi_asz;
    private String szallito_irszam;  
    private String szallito_telepules;
    private String szallito_utca;
    private String szallito_kozterulet;
    private String szallito_hazszam;
    // vevő adatok
    private String vevo_megnevezes;
    private String vevo_adoszam;
    private String vevo_kozossegi_asz;
    private String vevo_irszam;  
    private String vevo_telepules;
    private String vevo_utca;
    private String vevo_kozterulet;
    private String vevo_hazszam;
    private Megrendeles megrendeles;
    private List<InvoiceItemDto> tetelek;
    // a számla dátum adatok:
    private LocalDate keszult;
    private LocalDate fizetesi_hatarido;
    private LocalDate szallitas_napja;
    private String elszamolasi_valuta;
    // érték adatok
    private double netto_ertek;
    private double ado_ertek;
    private double brutto_ertek;
    // egyebek:
    private String megjegyzes;
}
