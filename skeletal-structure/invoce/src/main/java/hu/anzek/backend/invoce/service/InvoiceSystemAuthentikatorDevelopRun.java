/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.service;


import hu.anzek.backend.invoce.datalayer.model.InvUser;


/**
 *
 * @author User
 */
public class InvoiceSystemAuthentikatorDevelopRun implements InvoiceSystemAutheticator{

    public InvoiceSystemAuthentikatorDevelopRun() {
    }
    
    @Override
    public InvUser isTrueUser(String userName) {
        return new InvUser(userName, "password", "");
    }

    @Override
    public boolean isComparePassword(InvUser invUser, String pw) {
        return true;
    }  
}
