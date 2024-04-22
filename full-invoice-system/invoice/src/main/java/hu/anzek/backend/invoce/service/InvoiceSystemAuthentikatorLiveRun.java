/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.service;


import hu.anzek.backend.invoce.service.interfaces.InvoiceSystemAutheticator;
import hu.anzek.backend.invoce.datalayer.model.InvUser;
import hu.anzek.backend.invoce.datalayer.repository.InvUserRepo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author User
 */
public class InvoiceSystemAuthentikatorLiveRun implements InvoiceSystemAutheticator{

    @Autowired
    private InvUser invUser;
    @Autowired
    private InvUserRepo userRepo;

    public InvoiceSystemAuthentikatorLiveRun() {
    }
        
    @Override
    public InvUser isTrueUser(String userName){        
        this.invUser = this.userRepo.findByUserName(userName);
        if(this.invUser != null ){
            return this.invUser;            
        }        
        return null;
    }

    @Override
    public boolean isComparePassword(InvUser invUser, String pw) { 
        this.invUser = invUser;
        if(this.invUser.getPw().equals(pw)){        
            return true;
        }
        return false;
    }
}
