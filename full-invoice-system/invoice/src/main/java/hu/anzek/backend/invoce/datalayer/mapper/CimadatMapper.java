/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.mapper;

import hu.anzek.backend.invoce.datalayer.dto.CimadatDto;
import hu.anzek.backend.invoce.datalayer.model.Cimadat;
import java.util.List;
import org.mapstruct.Mapper;


/**
 *
 * @author User
 */
@Mapper(componentModel = "spring")
public interface CimadatMapper {
    CimadatDto cimadatToDto(Cimadat cimadat);
    Cimadat dtoToCimadat(CimadatDto cimadatDto);   
    List<CimadatDto> cimadatsToDtos(List<Cimadat> cimadat);
    List<Cimadat> dtosToCimadats(List<CimadatDto> cimadatDtos);   
}
