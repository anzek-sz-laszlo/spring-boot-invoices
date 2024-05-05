/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package hu.anzek.backend.invoce.service.enumeralt;


/**
 *
 * @author User
 */
public enum FizetesiModok {
    KESZPENZ_CASH(0),
    ATUTALAS_TRANSFER(1),
    BANKKARTYA_HITEL_BANKDEBIT_CREDIT(2),
    EGYEB_OTHER(3);
    
    public final int fizmod;
    private FizetesiModok(int fizmod){        
        this.fizmod=fizmod;
    }
}
