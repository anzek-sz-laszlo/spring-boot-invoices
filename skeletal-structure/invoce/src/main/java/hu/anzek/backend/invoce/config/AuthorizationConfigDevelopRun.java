/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.config;


import hu.anzek.backend.invoce.service.InvoiceSystemAuthentikatorDevelopRun;
import hu.anzek.backend.invoce.service.InvoiceSystemAutheticator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


/**
 *
 * @author User
 */
@Configuration
@Profile("!prod")
public class AuthorizationConfigDevelopRun {
    
    @Bean
    public InvoiceSystemAutheticator invoiceSystemDevelopAutheticator(){
        return new InvoiceSystemAuthentikatorDevelopRun();
    }
}
