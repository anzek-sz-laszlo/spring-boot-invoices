/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.mapper;


import hu.anzek.backend.invoce.datalayer.dto.PartnerCimHelysegDto;
import hu.anzek.backend.invoce.datalayer.model.Partner;
import hu.anzek.backend.invoce.service.FizmodMapperConverterImpl;
import hu.anzek.backend.invoce.service.PartnerStatusMapperConverterImpl;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;



/**
 *
 * @author User
 */
@Service
@Mapper(componentModel = "spring", uses = {FizmodMapperConverterImpl.class, PartnerStatusMapperConverterImpl.class})
public interface PartnerViewMapper {
    PartnerViewMapper INSTANCE = Mappers.getMapper(PartnerViewMapper.class);    
    @Mapping(source = "id", target = "partner_id")
    @Mapping(source = "megnevezes", target = "partner_megnevezes")
    @Mapping(source = "adoszam", target = "partner_adoszam")
    @Mapping(source = "kozossegi_asz", target = "partner_kozossegi_asz")
    @Mapping(source = "vevo_szallito", target = "partner_vevo_szallito")
    @Mapping(source = "fizmod", target = "partner_fizmod")
    @Mapping(source = "partner_cim.id", target = "cimadat_id")
    @Mapping(source = "partner_cim.telepules.irszam", target = "helyseg_irszam")
    @Mapping(source = "partner_cim.telepules.helyseg", target = "helyseg_helyseg")
    @Mapping(source = "partner_cim.utca", target = "cimadat_utca")
    @Mapping(source = "partner_cim.kozterulet", target = "cimadat_kozterulet")
    @Mapping(source = "partner_cim.hazszam", target = "cimadat_hazszam")   
    @Mapping(source = "partner.egyeb_info", target = "partner_egyeb_info")  
    PartnerCimHelysegDto partnerToDto(Partner partner);    
    
    List<PartnerCimHelysegDto> partnersToDtos(List<Partner> partnerek);
    
    @Mapping(source = "partner_id", target = "id")
    @Mapping(source = "partner_megnevezes", target = "megnevezes")
    @Mapping(source = "partner_adoszam", target = "adoszam")
    @Mapping(source = "partner_kozossegi_asz", target = "kozossegi_asz")
    @Mapping(source = "partner_vevo_szallito", target = "vevo_szallito")
    @Mapping(source = "partner_fizmod", target = "fizmod")
    @Mapping(source = "cimadat_id", target = "partner_cim.id")
    @Mapping(source = "helyseg_irszam", target = "partner_cim.telepules.irszam")
    @Mapping(source = "helyseg_helyseg", target = "partner_cim.telepules.helyseg")
    @Mapping(source = "cimadat_utca", target = "partner_cim.utca")
    @Mapping(source = "cimadat_kozterulet", target = "partner_cim.kozterulet")
    @Mapping(source = "cimadat_hazszam", target = "partner_cim.hazszam")        
    @Mapping(source = "partner_egyeb_info", target = "egyeb_info")       
    Partner dtoToPartner(PartnerCimHelysegDto nezetPartnerDto);
    
    List<Partner> dtosToPartners(List<PartnerCimHelysegDto> nezetPartnerDtoList);    
}
