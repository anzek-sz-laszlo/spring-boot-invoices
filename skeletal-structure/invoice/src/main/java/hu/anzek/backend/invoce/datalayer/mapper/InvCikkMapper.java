/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.mapper;


/**
 *
 * @author User
 */
import hu.anzek.backend.invoce.datalayer.model.InvCikk;
import hu.anzek.backend.invoce.datalayer.dto.InvCikkDto;
import java.util.List;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface InvCikkMapper {

    // @Mapping(target = "id", ignore = true)
    InvCikkDto cikkToDto(InvCikk invCikk);    
    List<InvCikkDto> cikksToDtos(List<InvCikk> invCikkek);
    InvCikk dtoToCikk(InvCikkDto invCikkDto);
    List<InvCikk> dtosToCikks(List<InvCikkDto> invCikkDtoList);
}

