/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.mapper;
import hu.anzek.backend.invoce.datalayer.dto.HelysegnevTarDto;
import hu.anzek.backend.invoce.datalayer.model.HelysegnevTar;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HelysegnevTarMapper {
    HelysegnevTarDto helysegnevTarToDto(HelysegnevTar helysegnevTar);
    HelysegnevTar dtoToHelysegnevTar(HelysegnevTarDto helysegnevTarDto);
    List<HelysegnevTarDto> helysegnevTarToDtos(List<HelysegnevTar> helysegnevTars);
    List<HelysegnevTar> dtosToHelysegnevTar(List<HelysegnevTarDto> helysegnevTarDtoList);
}