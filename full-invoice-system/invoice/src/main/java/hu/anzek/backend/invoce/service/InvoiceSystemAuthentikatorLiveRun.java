/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.service;


import hu.anzek.backend.invoce.datalayer.model.InvUser;
import hu.anzek.backend.invoce.datalayer.repository.InvUserRepo;
import hu.anzek.backend.invoce.service.interfaces.InvoiceSystemAutheticator;
import java.util.AbstractList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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
        List<SimpleGrantedAuthority> authList = new AbstractList<SimpleGrantedAuthority>() {
            @Override
            public SimpleGrantedAuthority get(int index) {
                return new SimpleGrantedAuthority("user");
            }

            @Override
            public int size() {
                return 1;
            }
        };
        return new InvUser("user","pwuser","user",null);
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
