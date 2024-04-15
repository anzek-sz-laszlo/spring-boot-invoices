/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.controller;


import hu.anzek.backend.invoce.InvoceSystemApplication;
import hu.anzek.backend.invoce.datalayer.mapper.InvUserMapper;
import hu.anzek.backend.invoce.datalayer.model.InvUserDto;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 *
 * @author User
 */
@Controller
public class MenuController {

    @Autowired
    private InvUserMapper mapper;
    
    @GetMapping("/appmenu")
    public String showMenu(Map<String,InvUserDto> model){
        
        model.put("userInfo", this.mapper.invUserToDto(InvoceSystemApplication.aktivUser));
        return "menu_form";
    }
}
