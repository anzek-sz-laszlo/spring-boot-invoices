/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.controller;

import hu.anzek.backend.invoce.datalayer.model.Cimadat;
import hu.anzek.backend.invoce.service.CimadatService;
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
public class CimController {

    @Autowired
    private CimadatService cimService;

    @GetMapping("/cim/modositas/{id}")
    public String modositCim(@PathVariable Long id, Model model) {
        Cimadat cim = cimService.getById(id);
        model.addAttribute("cimadat", cim);
        // Add more attributes if needed
        return "cim-form";
    }

    @PostMapping("/cim/mentes")
    public String mentesCim(@ModelAttribute("cimadat") Cimadat cimadat) {
        cimService.create(cimadat);
        return "redirect:/"; // Redirect to homepage or any other appropriate page
    }

    @GetMapping("/cim/torles/{id}")
    public String torolCim(@PathVariable Long id) {
        cimService.delete(id);
        return "redirect:/"; // Redirect to homepage or any other appropriate page
    }
}
