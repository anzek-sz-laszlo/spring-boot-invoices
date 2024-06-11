/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.dto;


import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *
 * @author User
 */
@Data
@NoArgsConstructor
public class MegrendelesTetelDto {
    private Long id;
    private Long invCikkId;
    private Integer mennyiseg;
}
