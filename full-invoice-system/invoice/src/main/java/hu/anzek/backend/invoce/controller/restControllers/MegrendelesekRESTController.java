/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package hu.anzek.backend.invoce.controller.restControllers;


import hu.anzek.backend.invoce.datalayer.dto.MegrendelesDto;
import hu.anzek.backend.invoce.datalayer.dto.MegrendelesTetelDto;
import hu.anzek.backend.invoce.datalayer.model.Megrendeles;
import hu.anzek.backend.invoce.datalayer.model.MegrendelesTetel;
import hu.anzek.backend.invoce.datalayer.repository.InvCikkRepository;
import hu.anzek.backend.invoce.datalayer.repository.PartnerRepository;
import hu.anzek.backend.invoce.service.MegrendelesService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * A REST kontroller használata egy KLIENS alkalmazást igényel!
 * A legjobb választás ennek helyettesítésér a POSTMAN alkalmazás...
 * @author User
 */
@RestController
@RequestMapping("/api/megrendelesek")
public class MegrendelesekRESTController {
  
    private final MegrendelesService service;
    private final PartnerRepository partnerRepository;
    private final InvCikkRepository invCikkRepository;    
    
    @Autowired
    public MegrendelesekRESTController(MegrendelesService service,
                                       PartnerRepository partnerRepository,
                                       InvCikkRepository invCikkRepository){
        this.service = service;
        this.partnerRepository = partnerRepository;
        this.invCikkRepository = invCikkRepository;        
    }
    // újbevitel
    @PostMapping
    public ResponseEntity<Object> createMegrendeles(@RequestBody MegrendelesDto megrendelesDto) {
        // inicializálunk egy új megrendelést:
        Megrendeles megrendeles = new Megrendeles();
        // átmeppeljük az adatokat, nem generált mepperrel csináljuk, csak amúgy gyalogosan:
        megrendeles.setSzallito(this.partnerRepository.findById(megrendelesDto.getSzallitoId()).orElseThrow(() -> new RuntimeException("Szallito nem talalhato!")));
        megrendeles.setVevo(this.partnerRepository.findById(megrendelesDto.getVevoId()).orElseThrow(() -> new RuntimeException("Vevo nem talalhato")));
        megrendeles.setMikorra(megrendelesDto.getMikorra());
        megrendeles.setMegjegyzes(megrendelesDto.getMegjegyzes());
        megrendeles.setRogzitve(megrendelesDto.getRogzitve());
        megrendeles.setSzamlaszam(megrendelesDto.getSzamlaszam());
        megrendeles.setLezarva(megrendelesDto.isLezarva());

        // inicializálunk egy tételek listát:
        List<MegrendelesTetel> tetelek = new ArrayList<>();
        // átmeppeljük az adatokat (emiatt a pár sor miatt nem írunk külön interfészt és mappert):
        megrendelesDto.getTetelek().size();
        for (MegrendelesTetelDto tetelDto : megrendelesDto.getTetelek()) {
            MegrendelesTetel tetel = new MegrendelesTetel();
            tetel.setInv_cikk(this.invCikkRepository.findById(tetelDto.getInvCikkId()).orElseThrow(() -> new RuntimeException("Cikk nem talalhato")));
            tetel.setMennyiseg(tetelDto.getMennyiseg());
            tetel.setMegrendeles(megrendeles);
            tetelek.add(tetel);
        }
        megrendeles.setTetelek(tetelek);
        // adatmentés - a teljes entitás gráf mentésre kerül,
        // mégpedig a fő entitás, a "megrendeles.tetelek" -re tett annotáció (cascade = CascadeType.ALL) miatt!:
        megrendeles = this.service.createMegrendeles(megrendeles);
        // visszadjuk a letárolt megrendelést (most már az azonosítókkal visszaolvasva!):
        if(megrendeles != null)
            return ResponseEntity.ok(megrendeles);
        else
            return ResponseEntity.internalServerError().body("\nHibás kérés!"
                    + "\nValószínűleg létező ID-t adott meg!"
                    + "\nÚj bevitelnél ne adjon meg, ha van, törölje az ID tulajdonágokat (a tételeknél is)!\n");
    }
    // módosítás:
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateMegrendeles(@PathVariable("id") Long id,
                                                         @RequestBody MegrendelesDto megrendelesDto) {
        // inicializálunk egy új megrendelést:
        Megrendeles megrendeles = new Megrendeles();
        // átmeppeljük az adatokat, nem generált mepperrel csináljuk, csak amúgy gyalogosan:
        megrendeles.setId(megrendelesDto.getId());
        megrendeles.setSzallito(this.partnerRepository.findById(megrendelesDto.getSzallitoId()).orElseThrow(() -> new RuntimeException("Szallito nem talalhato!")));
        megrendeles.setVevo(this.partnerRepository.findById(megrendelesDto.getVevoId()).orElseThrow(() -> new RuntimeException("Vevo nem talalhato")));
        megrendeles.setMikorra(megrendelesDto.getMikorra());
        megrendeles.setMegjegyzes(megrendelesDto.getMegjegyzes());
        megrendeles.setRogzitve(megrendelesDto.getRogzitve());
        megrendeles.setSzamlaszam(megrendelesDto.getSzamlaszam());
        megrendeles.setLezarva(megrendelesDto.isLezarva());

        // inicializálunk egy tételek listát:
        List<MegrendelesTetel> tetelek = new ArrayList<>();
        // átmeppeljük az adatokat (emiatt a pár sor miatt nem írunk külön interfészt és mappert):
        megrendelesDto.getTetelek().size();
        for (MegrendelesTetelDto tetelDto : megrendelesDto.getTetelek()) {
            MegrendelesTetel tetel = new MegrendelesTetel();
            tetel.setId(tetelDto.getId());
            tetel.setInv_cikk(this.invCikkRepository.findById(tetelDto.getInvCikkId()).orElseThrow(() -> new RuntimeException("Cikk nem talalhato")));
            tetel.setMennyiseg(tetelDto.getMennyiseg());
            tetel.setMegrendeles(megrendeles);
            tetelek.add(tetel);
        }
        megrendeles.setTetelek(tetelek);
        // adatmentés - a teljes entitás gráf mentésre kerül,
        // mégpedig a fő entitás, a "megrendeles.tetelek" -re tett annotáció (cascade = CascadeType.ALL) miatt!:
        megrendeles = this.service.updateMegrendeles(megrendeles);
        // visszadjuk a letárolt megrendelést (most már az azonosítókkal visszaolvasva!):
        if(megrendeles != null)
            return ResponseEntity.ok(megrendeles);
        else
            return ResponseEntity.internalServerError().body("\nHibás kérés!"
                    + "\nValószínűleg nem adott meg-, vagy nem létező ID-t adott meg!"
                    + "\nMódosításhoz vegyen fel egy {id: [intValue]} mezőt, és adjon meg egy létező ID-t (a tételeknél is)!\n");
    }
    // egy komplett megrendelés lekérdezése
    @GetMapping("/{id}")
    public ResponseEntity<Object> getMegrendeles(@PathVariable Long id) {
        Megrendeles megrendeles = this.service.findById(id);
        if(megrendeles != null)
            return ResponseEntity.ok(megrendeles);
        else
            return ResponseEntity.internalServerError().body("\nHibás kérés!"
                    + "\nValószínűleg nem adott meg-, vagy nem létező ID-t adott meg!"
                    + "\nA lekérdezéshez adjon meg egy létező ID-t az URL-ben (pl így:../api/megrendelesek/125)!\n");
    }
    // az összes megrendelés éekérdezése:
    @GetMapping
    public ResponseEntity<List<Megrendeles>> getAllMegrendelesek() {
        List<Megrendeles> megrendelesek = this.service.getAllMegrendeless();
         if( ! megrendelesek.isEmpty())
            return ResponseEntity.ok(megrendelesek);
        else
            return ResponseEntity.internalServerError().body(null);
    }
    // egy megrendelés törlése:
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteMegrendeles(@PathVariable Long id) {
        Megrendeles megrendeles = this.service.findById(id);
        this.service.delete(megrendeles);
        if(this.service.findById(id) == null){
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.ok(false);
        }
    }    
}
