/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.controller;


import hu.anzek.backend.invoce.datalayer.model.Cimadat;
import hu.anzek.backend.invoce.datalayer.model.Partnerek;
import hu.anzek.backend.invoce.service.CimadatService;
import hu.anzek.backend.invoce.service.HelysegnevTarService;
import hu.anzek.backend.invoce.service.PartnerService;
import java.util.List;
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
   
    private final PartnerService partnerService;    
    private final HelysegnevTarService helysegnevTarService;    
    private final CimadatService cimadatService;
    
    @Autowired
    public PartnerController(PartnerService partnerService,
                             HelysegnevTarService helysegnevTarService,
                             CimadatService cimadatService) {
        this.partnerService = partnerService;
        this.helysegnevTarService = helysegnevTarService;
        this.cimadatService = cimadatService;
    }    
    
    @GetMapping("/partner/lista")
    public String getAllPartner(Model model) {
        // ez a listához kell:
        model.addAttribute("partnerek",this.partnerService.getAllFulDtoList());
        // model.addAttribute("partnerek", this.partnerService.getAll());
        // ez pedig az újpartner felviteléhez kell:
        model.addAttribute("ujPartner",new Partnerek());        
        // HelysegnevTar listája:
        model.addAttribute("irszamHelysegList", this.helysegnevTarService.getAll());   
        
        return "partnerekCrud";
    }

    @GetMapping("/cimadat/list/{irszam}")
    public String showCimadatList(@PathVariable 
                                  String irszam, 
                                  Model model) {
        // Az adott irszamhoz tartozo cimadatok lekerdezese
        List<Cimadat> cimadatLista = this.cimadatService.getCimekByIrszam(irszam);
        model.addAttribute("cimadatLista", cimadatLista);

        return "cimadatLista";
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
        Partnerek partnerek = partnerService.getById(id);
        model.addAttribute("partner", partnerek);
        model.addAttribute("irszamokHelysegek", this.helysegnevTarService.getAll());
        model.addAttribute("cimek", cimadatService.getCimekByIrszam(partnerek.getPartner_cim().getTelepules().getIrszam()));
        return "partnerekModify";
    }

    @GetMapping("/partner/torles/{id}")
    public String torolPartner(@PathVariable 
                               Long id) {
        partnerService.delete(id);
        return "redirect:/partner/lista";
    }
}