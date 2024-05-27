/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.controller.webControllers;


import hu.anzek.backend.invoce.datalayer.dto.PartnerCimHelysegDto;
import hu.anzek.backend.invoce.datalayer.mapper.PartnerViewMapper;
import hu.anzek.backend.invoce.datalayer.model.Cimadat;
import hu.anzek.backend.invoce.datalayer.model.Partner;
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
    private final PartnerViewMapper viewMapper;
    
    @Autowired
    public PartnerController(PartnerService partnerService,
                             HelysegnevTarService helysegnevTarService,
                             CimadatService cimadatService,
                             PartnerViewMapper viewMapper) {
        this.partnerService = partnerService;
        this.helysegnevTarService = helysegnevTarService;
        this.cimadatService = cimadatService;
        this.viewMapper = viewMapper;
    }    
    
    @GetMapping("/partner/lista")
    public String getAllPartner(Model model) {
        // ez a listához kell:
        model.addAttribute("partnerekDto",this.partnerService.getAllFulDtoList());
        // model.addAttribute("partnerek", this.partnerService.getAll());
        // ez pedig az újpartner felviteléhez kell:
        model.addAttribute("ujPartnerDto",new PartnerCimHelysegDto());        
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
    public String mentesPartner(@ModelAttribute("ujPartnerDto") 
                                PartnerCimHelysegDto ujPartnerDto) {
        this.partnerService.partnerGrafMentes(true, this.viewMapper.dtoToPartner(ujPartnerDto));
        return "redirect:/partner/lista"; 
    }       
    
    @GetMapping("/partner/modositas/{id}")
    public String modositPartner(@PathVariable 
                                 Long id, 
                                 Model model) {
        
        Partner partner = partnerService.getById(id);        
        // model.addAttribute("partnerDto", this.partnerMapper.partnerToDto(partner));
        model.addAttribute("irszamokHelysegek", this.helysegnevTarService.getAll());
        model.addAttribute("cimek", this.cimadatService.getCimekByIrszam(partner.getPartner_cim().getTelepules().getIrszam()));
        return "partnerekModify";
    }
        
    @PostMapping("/partner/modify")
    public String modifyPartner(@ModelAttribute("partnerDto")
                               PartnerCimHelysegDto partnerDto) {        
        this.partnerService.partnerGrafMentes(false,this.viewMapper.dtoToPartner(partnerDto));
        return "redirect:/partner/lista";
    }
    
    @GetMapping("/partner/torles/{id}")
    public String torolPartner(@PathVariable 
                               Long id) {        
        partnerService.delete(id);
        return "redirect:/partner/lista";
    }
}