/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.controller;


import hu.anzek.backend.invoce.datalayer.dto.InvCikkDto;
import hu.anzek.backend.invoce.datalayer.mapper.InvCikkMapper;
import hu.anzek.backend.invoce.datalayer.model.InvCikk;
import hu.anzek.backend.invoce.service.InvCikkService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 *
 * @author User
 */
@Controller
@RequestMapping("/cikkek")
public class InvCikkController  {
    
    @Autowired
    private InvCikkService cikkService;    
    @Autowired
    private InvCikkMapper cikkMapper;
    
    @GetMapping 
    public String getCikkList(Map<String,List<InvCikkDto>> model1,
                              Map<String,InvCikkDto> model2) {
        this.cikkMapper.cikksToDtos(this.cikkService.getAll()).stream().forEach(e -> System.out.println(e.getId() + " " + e.getMegnevezes()));
        model1.put("cikkek", this.cikkMapper.cikksToDtos(this.cikkService.getAll()));
        model2.put("ujCikk", new InvCikkDto());   
        return "invCikkCrud";
    }

    @PostMapping("/add")
    public String createCikk( @ModelAttribute("cikkDto") 
                              InvCikkDto cikkDto ) {
        System.out.println("Rögzített újbevitel: " + this.cikkService.create(this.cikkMapper.dtoToCikk(cikkDto)) );
        return "redirect:/cikkek";
    }
    
    @GetMapping("/modify/{id}")    
    public String editCikk( Map<String, Object> model, 
                            @PathVariable 
                            long id ){                
        InvCikk cikk = this.cikkService.getById(id);
        if(cikk != null){            
            model.put("modifyCikk", this.cikkMapper.cikkToDto(cikk));
            return "invCikkModify";
        }else{            
            model.put("error", "Figyelem hibás cikk azonosító (nem található), így a módosítás sikertelen!");
            model.put("status", "1001");
            return "error_message";
        }
    }
                              
    @PostMapping("/modify")
    public String preModifyCikk( @ModelAttribute("cikkDto") 
                                 InvCikkDto cikkDto){         
        System.out.println("Rögzített módosítás: " + this.cikkService.update(this.cikkMapper.dtoToCikk(cikkDto)) );
        return "redirect:/cikkek";
    }

    @GetMapping("/delete/{id}")    
    public String deleteCikk( @PathVariable 
                              long id) {
        System.out.println("Rögzített törlés: " + this.cikkService.delete(id) );
        return "redirect:/cikkek";
    }  
}
