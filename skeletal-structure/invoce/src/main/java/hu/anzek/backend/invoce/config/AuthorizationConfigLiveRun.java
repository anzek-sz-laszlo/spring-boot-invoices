/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Beans/Bean.java to edit this template
 */
package hu.anzek.backend.invoce.config;

import hu.anzek.backend.invoce.service.InvoiceSystemAuthentikatorLiveRun;
import hu.anzek.backend.invoce.service.InvoiceSystemAutheticator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


/**
 *
 * @author User
 */
@Configuration
@Profile("prod")
public class AuthorizationConfigLiveRun{

    @Bean
    public InvoiceSystemAutheticator invoiceSystemLiveAutheticator(){
        return new InvoiceSystemAuthentikatorLiveRun();
    }
}
