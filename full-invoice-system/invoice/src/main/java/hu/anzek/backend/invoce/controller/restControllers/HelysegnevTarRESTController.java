/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package hu.anzek.backend.invoce.controller.restControllers;


import hu.anzek.backend.invoce.datalayer.dto.HelysegnevTarDto;
import hu.anzek.backend.invoce.datalayer.mapper.HelysegnevTarMapper;
import hu.anzek.backend.invoce.datalayer.model.HelysegnevTar;
import hu.anzek.backend.invoce.service.HelysegnevTarService;
import io.swagger.v3.oas.annotations.Operation;
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
 * Szabóné Andi megoldása...
 * @author User
 */
@RestController
@RequestMapping("api/halysegnevtar")
public class HelysegnevTarRESTController {
	
    private final HelysegnevTarService helysegnevTarService;
    private final HelysegnevTarMapper helysegnevTarMapper;

    @Autowired
    public HelysegnevTarRESTController(HelysegnevTarService helysegnevTarService, 
                                       HelysegnevTarMapper helysegnevTarMapper)	{
        this.helysegnevTarService = helysegnevTarService;
        this.helysegnevTarMapper = helysegnevTarMapper;
    }		

    @GetMapping
    @Operation(summary = "Összes helységnév lekérdezése")
    public ResponseEntity<List<HelysegnevTarDto>> getAllHelysegnev(){
        List<HelysegnevTar> lista = this.helysegnevTarService.getAll();
        if(!lista.isEmpty()) {
            return ResponseEntity.ok(this.helysegnevTarMapper.helysegnevTarToDtos(lista));
        }else{
            return ResponseEntity.noContent().build();
        }        
    }

    @GetMapping("/{irszam}")
    @Operation(summary = "Egy helységnév lekérdezése")
    public ResponseEntity<HelysegnevTarDto> getHelysegnevById(@PathVariable 
                                                              String irszam) {
        HelysegnevTar helysegnev = this.helysegnevTarService.getById(irszam);
        if(helysegnev != null) {
            return ResponseEntity.ok(this.helysegnevTarMapper.helysegnevTarToDto(helysegnev));
        }else{
            return ResponseEntity.badRequest().build();
        }        
    }
	
    @PostMapping
    @Operation(summary = "Új helységnév hozzáadása")
    public ResponseEntity<HelysegnevTarDto> createHelysegnev(@RequestBody 
                                                             HelysegnevTarDto helysegnevTarDto) {	
        HelysegnevTar helysegnev = this.helysegnevTarService.create(helysegnevTarMapper.dtoToHelysegnevTar(helysegnevTarDto));
        if(helysegnev != null) {
            return ResponseEntity.ok(this.helysegnevTarMapper.helysegnevTarToDto(helysegnev));
        }else{
            return ResponseEntity.badRequest().build();	
        }
    }

    @PutMapping("/{irszam}")
    @Operation(summary = "Egy helységnév módosítása")
    public ResponseEntity<HelysegnevTarDto> updateHelysegnev(@PathVariable String irszam, 
                                                             @RequestBody HelysegnevTarDto helysegnevTarDto){
        if(this.helysegnevTarService.getById(irszam) == null) { 
            return ResponseEntity.notFound().build();
        }
        HelysegnevTar helysegnev = this.helysegnevTarService.update(this.helysegnevTarMapper.dtoToHelysegnevTar(helysegnevTarDto));
        if(helysegnev != null) {
            return ResponseEntity.ok(this.helysegnevTarMapper.helysegnevTarToDto(helysegnev));
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{irszam}")
    @Operation(summary = "Egy helységnév eltávolítása")
    public ResponseEntity<Void> deleteHelysegnev(@PathVariable String irszam) {  
        boolean deleted = this.helysegnevTarService.delete(irszam);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }        	
    }
}
