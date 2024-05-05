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
}
