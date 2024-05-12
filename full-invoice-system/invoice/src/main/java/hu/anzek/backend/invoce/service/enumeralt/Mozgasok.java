/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package hu.anzek.backend.invoce.service.enumeralt;


/**
 *
 * @author User
 */
public enum Mozgasok {
    BEVET(0),
    VISSZAVET(1),
    VISSZARU(2),
    FHASZN(3),
    ELADAS(4),
    SELEJT(5),
    EGXEBKIADAS(6),
    EGYEBBEVET(7);
    
    public final int mozgas;
    private Mozgasok(int mozgas) {
        this.mozgas = mozgas;
    }
    
    public int getIntValue(){        
        return this.mozgas;
    }       
    
    public Mozgasok getValue(int intValue){
        switch(intValue){
            case 0 : return this.BEVET;
            case 1 : return this.VISSZAVET;
            case 2 : return this.VISSZARU;
            case 3 : return this.FHASZN;            
            case 4 : return this.ELADAS;
            case 5 : return this.SELEJT;
            case 6 : return this.EGXEBKIADAS;
            case 7 : return this.EGYEBBEVET;             
        }        
        return this.BEVET;
    }    
}
