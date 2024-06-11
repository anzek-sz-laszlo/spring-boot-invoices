/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package hu.anzek.backend.invoce.controller.restControllers;


import hu.anzek.backend.invoce.datalayer.dto.MegrendelesDto;
import hu.anzek.backend.invoce.service.InvoiceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author User
 */
@RestController
@RequestMapping("/api/szamlak")
public class InvoiceRESTController {

    private final InvoiceService invService;
    
    @Autowired
    public InvoiceRESTController(InvoiceService invService) {
        this.invService = invService;
    }    
    
    /**
     * automatikus készrejelntő/számlakészítő
     * @return megrendelések listát ad vissza (mégpedig amelyekről sikeresen számla készült)
     */
    @GetMapping("/automata_keszrejelento")
    public ResponseEntity<List<MegrendelesDto>> autoKeszreJelento(){
        // viszakapjuk az automatikusan most leszamlazott megrendeléseket:
        // közben konzolra kiírja sorban az "új számla készült : ... számlaadatok ... -at is)
        List<MegrendelesDto> lista = this.invService.automataKeszrejelento();
        // feloldás..?
        if(lista != null){
            // sikeres volt az előkészítés
            return ResponseEntity.ok(lista);        
        }else{
            // nem található ilyen adat
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * konrétan egy megrendelést jelöl meg és kényszeríti a rendszert arra, hogy készüljön belőle számla
     * @param id ez a megrendelés száma
     * @return sikeres volt vagy sem? (igen/nem)
     */
    @GetMapping("/keszrejelento/{id}")
    public ResponseEntity<Boolean> manualisKeszreJelento(@PathVariable("id") Long id){
        // kenyszerítjük a rendszert, hogy ennek a megrendelésnek az aktuálisan teljesíthető részét számlázza le!
        // (ez esetben a megrendelés többi része nyilván elveszik)
        return ResponseEntity.ok(this.invService.manualisKeszrejelento(id));
    }
}
