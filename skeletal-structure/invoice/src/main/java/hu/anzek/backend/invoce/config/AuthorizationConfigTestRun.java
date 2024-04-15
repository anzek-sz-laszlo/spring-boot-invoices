/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.config;


import hu.anzek.backend.invoce.datalayer.model.InvUser;
import hu.anzek.backend.invoce.service.InvoiceSystemAuthentikatorTestRun;
import hu.anzek.backend.invoce.service.InvoiceSystemAutheticator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


/**
 *
 * @author User
 */
@Configuration
@Profile("!normaluzem")
public class AuthorizationConfigTestRun {
    
    @Bean
    public InvoiceSystemAutheticator invoiceSystemAuthentikatorTestRun(){
        return new InvoiceSystemAuthentikatorTestRun();
    }
    
    @Bean
    public InvUser invUser(){
        return new InvUser();
    }    
}
