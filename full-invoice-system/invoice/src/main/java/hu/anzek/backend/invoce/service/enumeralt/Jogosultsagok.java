/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package hu.anzek.backend.invoce.service.enumeralt;


/**
 *
 * @author User
 */
public enum Jogosultsagok {
    GLOBAL_USER(0),
    GLOBAL_SYSTEMADMIN(1),
    OBSERVER_USER(2),
    INVOICING_USER(3),
    MASTER_DATA_ACCESS_USER(4);
    
    public final int author;
    private Jogosultsagok(int author){        
        this.author=author;
    }

    public Jogosultsagok getValue(int intValue){
        switch(intValue){
            case 0 : return this.GLOBAL_USER;
            case 1 : return this.GLOBAL_SYSTEMADMIN;
            case 2 : return this.OBSERVER_USER;
            case 3 : return this.INVOICING_USER; 
            case 4 : return this.MASTER_DATA_ACCESS_USER; 
        }        
        return this.GLOBAL_USER;
    }    
}
