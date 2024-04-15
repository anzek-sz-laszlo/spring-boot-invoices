/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.controller;


import hu.anzek.backend.invoce.InvoceSystemApplication;
import hu.anzek.backend.invoce.datalayer.mapper.InvUserMapper;
import hu.anzek.backend.invoce.datalayer.model.InvUser;
import hu.anzek.backend.invoce.datalayer.model.InvUserDto;
import hu.anzek.backend.invoce.service.InvoiceSystemAutheticator;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 *
 * @author User
 */
@Controller
public class SystemLoginController {
    
    @Autowired
    InvoiceSystemAutheticator isauth; 
    @Autowired
    InvUserMapper invUserMapper;
    
    public SystemLoginController() {
    }
        
    /**
     * Bejelentkezés/Bejelenetkezési hiba esetén erre az URL-re érkezik egy GET kérés<br>
     * @param error a hibahelenség<br>
     * @param status a hibakód (szerverkód)<br>
     * @param model1 egy paraméter injektálás a bejelentkezéshez<br>
     * @param model2 egy paraméter injektálás a hibakezeléshez<br>
     * @return Ha hiba volt, a login_error.html" sablont, másként és először mindig, a "login_form.html" sablont jeléeníti meg<br>
     */
    @GetMapping("/applogin")
    public String showLoginForm(@RequestParam(name = "error", required = false) 
                                String error,
                                @RequestParam(name = "status", required = false) 
                                String status,
                                Map<String,InvUserDto> model1,
                                Map<String,String> model2) {
        
        if (error != null) {     
            // System.out.println("error = " + error + " statusz = " + status);
            model2.put("error", error);
            model2.put("status", status);
            model2.put("fejlec", "A bejelentkezés sikertelen volt! Kérem újra próbálni!");
            return "error_message";
        }else{            
            model1.put("ujBelepo", new InvUserDto());
            return "login_form"; 
        }
    }    
    
    /**
     * A bekért felhasználói belépési adatok visszaPOST-olása utáni ellenőrzés<br>
     * @param ujBelepo a felhazsnáló név<br>
     * @return és redirectoljuk egy GET kéréssel a /menu erőforrást - ha jogosult, illetve a logint      
     */
    @PostMapping("/applogin")
    public String login(@ModelAttribute("ujBelepo") 
                        InvUserDto ujBelepo) {       
        // a DTO átmappelése a normál InvUser entitasba:
        InvUser invUser = this.invUserMapper.dtoToInvUser(ujBelepo);
        invUser = this.isauth.isTrueUser(invUser.getUserName());
        if( (invUser != null) && (this.isauth.isComparePassword(invUser, ujBelepo.getPw()))){        
            
            // Sikeres bejelentkezés, kitöltjük a statikus felhasználót, amelyre immár mindenhonnan tudunk hivatkozni: 
            InvoceSystemApplication.aktivUser = invUser;
            // majd átirányítjuk a felhasználót a menü erőforrásra:
            return "redirect:/appmenu"; 
        } else {            
            // Sikertelen bejelentkezés, visszatérhetünk ide, egy hibaüzenet paramétertel:  
            if(invUser == null){
                return "redirect:/applogin?error=Forbidden-Tiltott hozzaferes - nincs ilyen felhasznalo&status=403"; 
            }else{
                // -Bejelentkezesi hiba - hibas jelszot adott meg
                return "redirect:/applogin?error=Unauthorized-Bejelentkezesi hiba - hibas jelszot adott meg&status=401"; 
            }            
        }
    }    
    
    /**
     * Bármilyen hiba esetén - amit a böngészőből indult - erre az URL-re érkezik egy GET kérés<br>
     * @param error a hibahelenség<br>
     * @return Ha hiba volt, a "404.html" sablont sablont jeleníti meg.<br>
     */
    @GetMapping("/error")
    public String showErrorForm(@RequestParam(name = "error", required = false) 
                                String error) {
        String status = "404";
        error = "Nem beazonosítható erőforrás lekezelési hiba...";
        return "404";         
    }       
}
