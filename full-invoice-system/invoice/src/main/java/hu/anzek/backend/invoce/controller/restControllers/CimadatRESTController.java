/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Beans/Bean.java to edit this template
 */
package hu.anzek.backend.invoce.controller.restControllers;


import hu.anzek.backend.invoce.datalayer.dto.CimadatDto;
import hu.anzek.backend.invoce.datalayer.mapper.CimadatMapper;
import hu.anzek.backend.invoce.datalayer.model.Cimadat;
import hu.anzek.backend.invoce.service.CimadatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
 *
 * @author User
 */
@RestController
@RequestMapping("/api/cimadat")
public class CimadatRESTController {
        
    private final CimadatService cimadatService;
    private final CimadatMapper cimMapper; 
    
    @Autowired
    public CimadatRESTController(CimadatService cimadatService,
                                 CimadatMapper cimMapper) {
        this.cimadatService = cimadatService;
        this.cimMapper = cimMapper;
    }

    @GetMapping
    @Operation(summary = "Összes címadat lekérdezése")
    //    @ApiResponse(responseCode = "200", 
    //                 description = "Sikeres lekérdezés", 
    //                 content = {@Content(mediaType = "application/json", 
    //                                     schema = @Schema(implementation = Cimadat.class))}) 
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", 
                     description = "Sikeres lekérdezés", 
                     content = {
                                @Content(mediaType = "application/json", 
                                         schema = @Schema(implementation = CimadatDto.class)) 
                               }),
        @ApiResponse(responseCode = "400", 
                     description = "Hibás lekérdezés - nincs találat", 
                     content = {
                                @Content(mediaType = "application/json")
                               }),
        @ApiResponse(responseCode = "500", 
                     description = "Szerveroldali hiba", 
                     content = {
                                @Content(mediaType = "application/json")
                               })
    })    
    public ResponseEntity<List<CimadatDto>> getAllCimadat() {
        List<Cimadat> lista = this.cimadatService.getAll();
        if( ! lista.isEmpty()){
            return ResponseEntity.ok(this.cimMapper.cimadatsToDtos(lista));
        }else{
            return ResponseEntity.badRequest().build();
        }       
    }

    @GetMapping("/{id}")
    @Operation(summary = "Egy címadat lekérdezése")
    public ResponseEntity<CimadatDto> getCimadatById(@PathVariable Long id) {
        Cimadat cimadat = this.cimadatService.getById(id);
        if(cimadat != null){
            return ResponseEntity.ok(this.cimMapper.cimadatToDto(cimadat));
        }else{
            return ResponseEntity.badRequest().build();
        }        
    }

    @PostMapping
    @Operation(summary = "Új címadat hozzáadása")
    public ResponseEntity<CimadatDto> createCimadat(@RequestBody CimadatDto cimadatDto) {
        Cimadat cimadat = this.cimadatService.create(this.cimMapper.dtoToCimadat(cimadatDto));
        if(cimadat != null){
            return ResponseEntity.ok(this.cimMapper.cimadatToDto(cimadat));
        }else{
            return ResponseEntity.badRequest().build();
        }       
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Egy címadat módosítása")
    public ResponseEntity<CimadatDto> updateCimadat(@PathVariable Long id, 
                                                    @RequestBody CimadatDto cimadatDto) {
        if (this.cimadatService.getById(id) == null) {            
            return ResponseEntity.notFound().build();
        }        
        Cimadat cimadat = this.cimadatService.update(this.cimMapper.dtoToCimadat(cimadatDto));
        if(cimadat != null){
            return ResponseEntity.ok(this.cimMapper.cimadatToDto(cimadat));
        }else{
            return ResponseEntity.badRequest().build();
        }
    }    
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Egy címadat eltávolítása")
    public ResponseEntity<Void> deleteCimadat(@PathVariable Long id) {         
        boolean deleted = this.cimadatService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }        
    }    
}
