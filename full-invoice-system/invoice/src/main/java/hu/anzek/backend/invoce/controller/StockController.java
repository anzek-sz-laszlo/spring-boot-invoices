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
import hu.anzek.backend.invoce.datalayer.dto.StockDto;
import hu.anzek.backend.invoce.datalayer.mapper.StockMapper;
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
    @Autowired
    private StockMapper mapper;

    @GetMapping("/kiadottlista")
    public String kiadottMozgasokLista(Model model) {    
        model.addAttribute("stockLista",this.mapper.stocksToDtos(this.stockService.kiadottNotEmpty()));          
        return "stockLista";
    }    

    @GetMapping("/bevetlista")
    public String bevetMozgasokLista(Model model) {    
        model.addAttribute("stockLista",this.mapper.stocksToDtos(this.stockService.bevetNotEmpty()));          
        return "stockLista";
    }  
    
    @GetMapping("/listapont")
    public String mozgasokLista(Model model) {    
        model.addAttribute("stockLista",this.mapper.stocksToDtos(this.stockService.getStockMozgas("2000-01-01", "2100-12-31")));        
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
                           StockDto stockDtoMozgas,
                           Model model) {
        String uzenet = this.stockService.ujMozgas(this.mapper.dtoToStock(stockDtoMozgas));
        if( uzenet == null){
            return "redirect:/stock/mozgas/listapont";
        }else{
            model.addAttribute("error", uzenet);
            model.addAttribute("status", "500");
            return "error_message";
        }    
    }

    @PostMapping("/list")
    public String listStockMozgasok(@RequestParam("startDate") String startDate,
                                    @RequestParam("endDate") String endDate,
                                    Model model) {
        List<StockDto> stockLista = this.mapper.stocksToDtos(this.stockService.getStockMozgas(startDate, endDate));
        System.out.println("LISTÁSBA MEGY: " + stockLista.getFirst().getCikk().getId() + " " + 
                                               stockLista.getFirst().getCikk().getMegnevezes() );
        model.addAttribute("stockDtoLista", stockLista);
        
        return "stockLista";
    }

    @GetMapping("/modositas/{id}")
    public String editStockMozgas(@PathVariable 
                                  Long id,
                                  Model model) {                
        // erre az erőforrásra küldünk a listából egy GET http kérést,
        // majd ez elindítja a módosítást: kiolvassa az eredeti rekordot  
        StockDto dto = this.mapper.stockToDto(this.stockService.getById(id));            
        model.addAttribute("modifyDto", dto);     
        // átdobja karbantartásra a sablonnak:
        return "stockModify";
    }
    
    @PostMapping("/modify")
    public String modifyMozgas(@ModelAttribute("modifyDto")
                               StockDto modifyDto,                               
                               Model model) {        
        // ez visszaveszi a sablontól a DTO-t és átmappeli az adatokat, végül kiírja adatbázisba:
        // de van itt egy probléma:
        // - nem adja vissza a Raktármozgáson belüli "InvCikk" entitást! 
        // - - egyelőre nem tudtam megoldani, hogy miért nem! 
        // - - Ezért a service szinten újra kiegészítem a cikk adatokkal!
        String message = this.stockService.modositasMozgas(this.mapper.dtoToStock(modifyDto) );
        if( (message == null) || (message.isEmpty()) ) {        
            return "redirect:/stock/mozgas/listapont";
        }else{
            model.addAttribute("error",message);
            model.addAttribute("status", "500");
            return "error_message";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteStockMozgas(@PathVariable 
                                    Long id,
                                    Model model) throws Exception {
        this.stockService.deletekMozgas(id);
            return "redirect:/stock/mozgas/listapont";
    }
}

