/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.stereotype.Repository;


/**
 *
 * @author User
 */
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Repository
@Entity
public class Cimadat {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;    
    @ManyToOne
    @JoinColumn(name = "helyseg_irszam", referencedColumnName = "irszam")
    private HelysegnevTar telepules;
    private String utca;
    private String kozterulet;
    private String hazszam;    

    public Cimadat() {
    }

    public Cimadat(Long id,
                   HelysegnevTar telepules,
                   String utca,
                   String kozterulet,
                   String hazszam) {
        this.id = id;
        this.telepules = telepules;
        this.utca = utca;
        this.kozterulet = kozterulet;
        this.hazszam = hazszam;
    }

    @Override
    public String toString(){
        if(this != null){
        return  "Cimadat { \n" +
                "          - id: " + this.id + ", \n" +
                "          - helyseg { \n" +
                (this.telepules != null
                ?
                    "                       - irszam: " + this.telepules.getIrszam() + ", \n" +   
                    "                       - helyseg: " + this.telepules.getHelyseg() + ", \n" +  
                    "                    }, \n" 
                :
                    "                       - null ~n" + 
                    "                    }, "
                ) +
                "          - utca: " + this.utca + ", \n" +
                "          - kozterulet: " + this.kozterulet + ", \n" +      
                "          - hazszam: " + this.hazszam+ ", \n" +      
                "}\n";
        }else{
            return "Cimadat { - helyseg { null }, null }";
        }
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HelysegnevTar getTelepules() {
        return telepules;
    }

    public void setTelepules(HelysegnevTar telepules) {
        this.telepules = telepules;
    }

    public String getUtca() {
        return utca;
    }

    public void setUtca(String utca) {
        this.utca = utca;
    }

    public String getKozterulet() {
        return kozterulet;
    }

    public void setKozterulet(String kozterulet) {
        this.kozterulet = kozterulet;
    }

    public String getHazszam() {
        return hazszam;
    }

    public void setHazszam(String hazszam) {
        this.hazszam = hazszam;
    }
    
}
