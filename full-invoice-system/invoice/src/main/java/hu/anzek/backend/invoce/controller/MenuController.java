/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 *
 * @author User
 */
@Controller
public class MenuController {
    
    @GetMapping("/appmenu")
    public String showMenu(){
    
        System.out.println("showMenu()-ben vagyunk...");
        return "404";
    }
}
