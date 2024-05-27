/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.controller.restControllers;


import hu.anzek.backend.invoce.datalayer.mapper.InvCikkMapper;
import hu.anzek.backend.invoce.datalayer.model.InvCikk;
import hu.anzek.backend.invoce.datalayer.dto.InvCikkDto;
import hu.anzek.backend.invoce.service.InvCikkService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/cikkek")
public class InvCikkRESTController {

    private final InvCikkService cikkService;
    private final InvCikkMapper mapper;

    @Autowired
    public InvCikkRESTController(InvCikkService cikkService, InvCikkMapper modelMapper) {
        this.cikkService = cikkService;
        this.mapper = modelMapper;
    }

    @GetMapping("")
    public List<InvCikkDto> getAllCikkek() {
        List<InvCikk> cikkek = cikkService.getAll();
        return cikkek.stream()
                     .map(this.mapper::cikkToDto)
                     .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public InvCikkDto addCikk(@RequestBody 
                              InvCikkDto cikkDto) {
        InvCikk cikk = this.mapper.dtoToCikk(cikkDto);
        InvCikk createdCikk = cikkService.create(cikk);
        return this.mapper.cikkToDto(createdCikk);
    }

    @PutMapping("/modify/{id}")
    public InvCikkDto modifyCikk(@PathVariable 
                                 Long id, 
                                 @RequestBody 
                                 InvCikkDto cikkDto) {
        InvCikk cikk = this.mapper.dtoToCikk(cikkDto);
        InvCikk updatedCikk = cikkService.update(cikk);
        return this.mapper.cikkToDto(updatedCikk);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteCikk(@PathVariable 
                              Long id) {
        return this.cikkService.delete(id);
    }
}
