/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.config;


import hu.anzek.backend.invoce.datalayer.model.InvUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 *
 * @author User
 */
@Configuration
public class AppBeansConfig {
    
//    @Bean
//    public InvUserMapper invUserMapperImpl(){
//        return new InvUserMapperImpl();
//    }    
    
    @Bean
    public InvUser invUser(){
        return new InvUser();
    }    
}
