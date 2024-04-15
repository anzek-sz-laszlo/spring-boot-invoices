/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hu.anzek.backend.invoce.service;


import hu.anzek.backend.invoce.datalayer.model.InvUser;
import org.springframework.stereotype.Component;


/**
 *
 * @author User
 */
@Component
public interface InvoiceSystemAutheticator {

    public InvUser isTrueUser(String userName);
    public boolean isComparePassword(InvUser invUser, String pw);
    
}
