/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.controller;


import hu.anzek.backend.invoce.datalayer.model.Partnerek;
import hu.anzek.backend.invoce.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


/**
 *
 * @author User
 */
@Controller
public class PartnerController {
    
    @Autowired
    private PartnerService partnerService;

    @GetMapping("/partner/lista")
    public String getAllPartner(Model model) {
        // ez a listához kell:
        model.addAttribute("partnerek",this.partnerService.getAll());
        // ez pedig az újpartner felviteléhez kell:
        model.addAttribute("ujPartner",new Partnerek());        
        return "partnerekCrud";
    }
 
    @PostMapping("/partner/ujbevitel")
    public String mentesPartner(@ModelAttribute("partner") 
                                Partnerek partner) {
        this.partnerService.create(partner);
        return "redirect:/partner/lista"; 
    }       
    
    @GetMapping("/partner/modositas/{id}")
    public String modositPartner(@PathVariable 
                                 Long id, 
                                 Model model) {
        Partnerek partner = partnerService.getById(id);
        model.addAttribute("partner", partner);
        // Add more attributes if needed
        return "partnerekModify";
    }

    @GetMapping("/partner/torles/{id}")
    public String torolPartner(@PathVariable 
                               Long id) {
        partnerService.delete(id);
        return "redirect:/partner/lista";
    }
}