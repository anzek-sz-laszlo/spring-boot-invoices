/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.dto;


import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
@Data
@NoArgsConstructor
public class MegrendelesDto {
    private Long id;
    private Long szallitoId;
    private Long vevoId;
    private LocalDate mikorra;
    private String megjegyzes;
    private LocalDate rogzitve;
    private List<MegrendelesTetelDto> tetelek;
    private String szamlaszam;
    private boolean lezarva;
    private boolean zarolva;

}
