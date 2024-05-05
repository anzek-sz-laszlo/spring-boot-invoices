/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.dto;


import hu.anzek.backend.invoce.datalayer.model.InvCikk;
import java.time.LocalDateTime;
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
public class StockDto {

    private Long id;
    private InvCikk cikk;
    private LocalDateTime mozgas_idopont;
    private int bevetelezett_mennyiseg;
    private int kiadott_mennyiseg;
}
