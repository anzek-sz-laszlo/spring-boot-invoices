/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.controller;


/**
 *
 * @author User
 */
// StockController.java
import hu.anzek.backend.invoce.datalayer.model.InvCikk;
import hu.anzek.backend.invoce.datalayer.model.Stock;
import hu.anzek.backend.invoce.service.InvCikkService;
import hu.anzek.backend.invoce.service.StockService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stock/mozgas")
public class StockController {

    private List<InvCikk> invCikks = new ArrayList<>();
    
    @Autowired
    private StockService stockService;    
    @Autowired
    private InvCikkService invCikkService;

    @GetMapping("/listapont")
    public String mozgasokLista(Model model) {
        List<Stock> stockLista = this.stockService.getStockMozgas("2000-01-01", "2100-12-31");
        model.addAttribute("stockLista", stockLista);
      
        return "stockLista";
    }
    
    @GetMapping("/bevitel")
    public String ujForgalmiAdatsor(Model model) {
        if(this.invCikks.isEmpty()) {
            this.invCikks = this.invCikkService.getAll();
        }
        model.addAttribute("cikkLista", this.invCikks);
        model.addAttribute("ujStock", new Stock());        
        this.invCikks.stream().forEach(e -> System.out.println(e.getMegnevezes()));
        
        return "stockCrud";
    }

    @PostMapping("/ujbevitel")
    public String ujMozgas(@ModelAttribute("stockMozgas") 
                           Stock stockMozgas) {
        this.stockService.ujMozgas(stockMozgas);
        return "redirect:/stock/mozgas/listapont";
    }

    @PostMapping("/list")
    public String listStockMozgasok(@RequestParam("startDate") String startDate,
                                    @RequestParam("endDate") String endDate,
                                    Model model) {
        List<Stock> stockLista = this.stockService.getStockMozgas(startDate, endDate);
        model.addAttribute("stockLista", stockLista);
        
        return "stockLista";
    }

    @GetMapping("/modositas/{id}")
    public String modifyStockMozgas(@PathVariable 
                                    Long id,
                                    Model model) {
        // ez indítja a módosítást: kiolvassa az eredeti rekordot
        model.addAttribute("modiMozgas", this.stockService.getById(id));
        // átdobja karbantartásra a sablonnak
        return "stockModify";
    }

    @PostMapping("/modify")
    public String modifyMozgas(@ModelAttribute("stockMozgas") 
                               Stock stockMozgas) {
        // ez visszaveszi a sablontól és kiírja adatbázisba:
        this.stockService.modositasMozgas(stockMozgas);        
        return "redirect:/stock/mozgas/listapont";
    }

    @GetMapping("/delete/{id}")
    public String deleteStockMozgas(@PathVariable Long id) {
        this.stockService.deletekMozgas(id);
        
        return "redirect:/stock/mozgas/listapont";
    }
}

