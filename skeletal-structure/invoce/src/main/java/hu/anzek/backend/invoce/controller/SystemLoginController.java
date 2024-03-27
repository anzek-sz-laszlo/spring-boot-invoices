/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.controller;


import hu.anzek.backend.invoce.datalayer.model.InvUser;
import hu.anzek.backend.invoce.service.InvoiceSystemAutheticator;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


/**
 *
 * @author User
 */
@Controller
public class SystemLoginController {    
    
    @Autowired
    InvoiceSystemAutheticator isauth;
    
    @GetMapping("/applogin")
    public String showLoginForm(Map<String,InvUser> model){
        model.put("ujBelepo", new InvUser());        
        return "login_form";
    }
    
    @PostMapping("/applogin")
    public String login(@ModelAttribute("ujBelepo")
                        InvUser ujBelepo,
                        Map<String,String> model){
        String sVissza="";
        System.out.println("Fhasz.név: " + ujBelepo.getUserName() +"\nPassw: " + ujBelepo.getPw());
        // ellenőrzés, létezőségre:
        InvUser invUser = this.isauth.isTrueUser(ujBelepo.getPw());
        if((invUser != null) && (this.isauth.isComparePassword(invUser, ujBelepo.getPw()))){
            sVissza = "redirect:/appmenu";
        }
        model.put("error", "Hibás bejelentkezés!");
        model.put("status", "401/403");
        sVissza = "error_message";

        return sVissza;
    }
}
