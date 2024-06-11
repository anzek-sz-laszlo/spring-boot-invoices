/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package hu.anzek.backend.invoce.controller.webControllers;

import hu.anzek.backend.invoce.datalayer.model.Invoice;
import hu.anzek.backend.invoce.service.InvoiceService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 *
 * @author User
 */
@Controller
@RequestMapping("/api/szamlak")
public class InvoiceController {
    
    private final InvoiceService invoiceService;
    
    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }
    
    /**
     * Egy számla nyomtatása sablonba
     * @param id a számlaszáma
     * @param model az MVC model (paraméterekj átadásához)
     * @return a számlakép sablon neve 
     */
    @GetMapping("/szamla/{id}")
    @SuppressWarnings({"null", "UnnecessaryBoxing"})
    public String getInvoice(@PathVariable("id") Long id, 
                             Model model) {
        Invoice invoice = this.invoiceService.getInvoice(id);
        if(invoice != null){
            invoice.getItems().size();
            // valamiért lát benne a cikkódnál null értéket 
            // de nincs benne... hát lekezeljük:
            invoice.getItems().stream().forEach(e -> e.setCikk_kod(e.getCikk_kod() == null ? Long.valueOf(0L) : e.getCikk_kod()));
        }
        model.addAttribute("invoice", invoice);
        model.addAttribute("iTetelek",invoice.getItems() );
        model.addAttribute("afaTartalom", this.invoiceService.getAfaOsszesito(id));
        
        // a sablon meghívása:
        return "szamla";
    }
    
    /**
     * egy számla áafa-összesítése
     * @param id a számlaszáma
     * @param model az MVC model (paraméterekj átadásához)
     * @return az áfa-összesítő sablon neve
     */
    @GetMapping("/szamla/{id}/afa_osszesito")
    public String getAfaOsszesito(@PathVariable("id") Long id, Model model) {
        Map<Double, Double> afaOsszesito = this.invoiceService.getAfaOsszesito(id);
        model.addAttribute("afaOsszesito", afaOsszesito);
        return "afa_osszesito";
    }
}
