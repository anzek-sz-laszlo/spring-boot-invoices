/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package hu.anzek.backend.invoce.service.enumeralt;


/**
 *
 * @author User
 */
public enum PartnerStatus {
    VEVEO_CUSTOMER(0),
    SZALLITO_SUPPLIER(1),
    MINDKETTO_BOTH(2);
    
    public final int status;
    
    private PartnerStatus(int status) {
        this.status = status;
    }
    
    public int getIntValue(){          
        return this.status;
    }
        
    public PartnerStatus getValue(int status){
        switch(status){
            case 0 : return this.VEVEO_CUSTOMER;
            case 1 : return this.SZALLITO_SUPPLIER;
            case 2 : return this.MINDKETTO_BOTH;
        }
        
        return this.VEVEO_CUSTOMER;
    }    
}
