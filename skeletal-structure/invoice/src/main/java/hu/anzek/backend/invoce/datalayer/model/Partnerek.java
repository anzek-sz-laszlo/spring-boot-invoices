/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.model;


import hu.anzek.backend.invoce.service.enumeralt.FizetesiModok;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Partnerek {
        
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String megnevezes;
    private String adoszam;
    private String kozossegi_asz;
    private String vevo_szallito;
    @ManyToOne
    private Cimadat partner_cim;
    private FizetesiModok fizmod;
    private String egyeb_info;
}
