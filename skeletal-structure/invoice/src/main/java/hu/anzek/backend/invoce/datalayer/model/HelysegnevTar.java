/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;


/**
 *
 * @author User
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository
@Entity
public class HelysegnevTar {
    
    @Id
    private String irszam;
    private String helyseg;
}
