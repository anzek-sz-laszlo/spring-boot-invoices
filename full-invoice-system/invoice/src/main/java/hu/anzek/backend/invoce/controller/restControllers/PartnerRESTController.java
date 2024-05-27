/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.controller.restControllers;

import hu.anzek.backend.invoce.datalayer.dto.PartnerCimHelysegDto;
import hu.anzek.backend.invoce.datalayer.mapper.PartnerViewMapper;
import hu.anzek.backend.invoce.datalayer.model.Partner;
import hu.anzek.backend.invoce.service.PartnerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 *
 * @author User
 */
@RestController
@RequestMapping("/api/partners")
public class PartnerRESTController {
    
    private final PartnerService partnerService; 
    private final PartnerViewMapper viewMapper;
    
    @Autowired
    public PartnerRESTController(PartnerService partnerService,             
                                 PartnerViewMapper viewMapper) {
        this.partnerService = partnerService; 
        this.viewMapper = viewMapper;
    }
    
    /**
     * GET no body
     * @return 
     */
    @GetMapping
    @RequestMapping(method = RequestMethod.GET)    
    public ResponseEntity<List<PartnerCimHelysegDto>> getAllPartners() {
        List<PartnerCimHelysegDto> partners = this.partnerService.getAllFulDtoList();
        return ResponseEntity.ok(partners);
    }
    
    /**
     * GET url/{id} no body - lekérdezés
     * @param id
     * @return 
     */
    @GetMapping("/{id}")
    @RequestMapping(value = "/{id}", 
                    produces = "application/json", 
                    method = RequestMethod.GET)
    public ResponseEntity<PartnerCimHelysegDto> getPartnerById(@PathVariable Long id) {
        Partner partner = this.partnerService.getById(id);
        if (partner != null) {
            return ResponseEntity.ok(this.viewMapper.partnerToDto(partner));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST body{PartnerCimHelysegDto} - újbevitel
     * @param partnerDto
     * @return 
     */
    @PostMapping
    @RequestMapping(produces = "application/json", 
                    method = RequestMethod.POST)    
    public ResponseEntity<PartnerCimHelysegDto> createPartner(@RequestBody PartnerCimHelysegDto partnerDto) {
        PartnerCimHelysegDto partner = this.partnerService.createFromDto(partnerDto);
        if (partner != null) {
            return ResponseEntity.ok(partner);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * PUT url/{id} body{PartnerCimHelysegDto} - Módositás
     * @param id
     * @param partnerDto
     * @return 
     */
    @PutMapping("/{id}")
    @RequestMapping(value = "/{id}", 
                    produces = "application/json", 
                    method = RequestMethod.PUT)    
    public ResponseEntity<PartnerCimHelysegDto> updatePartner(@PathVariable Long id, 
                                                              @RequestBody PartnerCimHelysegDto partnerDto) {    
        if (this.partnerService.getById(id) == null) {            
            return ResponseEntity.notFound().build();
        }
        PartnerCimHelysegDto updatedPartner = this.partnerService.updateFromDto(partnerDto);
        if (updatedPartner != null) {
            return ResponseEntity.ok(updatedPartner);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * DELETE url/{id} - törlés
     * @param id
     * @return 
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        boolean deleted = this.partnerService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }     
}