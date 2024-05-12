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
    private FizetesiModok(int fizmod) {
        this.fizmod = fizmod;
    }
    
    public int getIntValue(){        
        return this.fizmod;
    }

    public FizetesiModok getValue(int intValue){
        switch(intValue){
            case 0 : return this.KESZPENZ_CASH;
            case 1 : return this.ATUTALAS_TRANSFER;
            case 2 : return this.BANKKARTYA_HITEL_BANKDEBIT_CREDIT;
            case 3 : return this.EGYEB_OTHER;            
        }        
        return this.KESZPENZ_CASH;
    }
}
