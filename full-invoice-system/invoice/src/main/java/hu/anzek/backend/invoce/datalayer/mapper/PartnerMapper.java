/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.mapper;

import hu.anzek.backend.invoce.datalayer.dto.PartnerDto;
import hu.anzek.backend.invoce.datalayer.model.Partner;
import java.util.List;
import org.mapstruct.Mapper;


/**
 *
 * @author User
 */
@Mapper(componentModel = "spring")
public interface PartnerMapper {
    PartnerDto partnerToDto(Partner partner);
    Partner dtoToPartner(PartnerDto partnerDto);   
    List<PartnerDto> partnersToDtos(List<Partner> partner);
    List<Partner> dtosToPartners(List<PartnerDto> partnerDtos);   
}
