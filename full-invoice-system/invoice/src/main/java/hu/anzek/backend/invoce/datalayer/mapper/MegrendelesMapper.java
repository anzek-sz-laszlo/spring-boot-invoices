/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.mapper;


import hu.anzek.backend.invoce.datalayer.dto.MegrendelesDto;
import hu.anzek.backend.invoce.datalayer.dto.MegrendelesTetelDto;
import hu.anzek.backend.invoce.datalayer.model.Megrendeles;
import hu.anzek.backend.invoce.datalayer.model.MegrendelesTetel;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;


/**
 *
 * @author User
 */
@Service
@Mapper
public class MegrendelesMapper {
    
    public MegrendelesDto toDto(Megrendeles order){
        if(order == null){
            return null;
        }
        List<MegrendelesTetelDto> tetelekDto = new ArrayList<>();
        for(MegrendelesTetel tetel : order.getTetelek()){
            MegrendelesTetelDto tetelDto = new MegrendelesTetelDto();
            tetelDto.setId(tetel.getId());
            tetelDto.setInvCikkId(tetel.getInv_cikk().getId());
            tetelDto.setMennyiseg(tetel.getMennyiseg());
            tetelekDto.add(tetelDto);
        }
        MegrendelesDto dto = new MegrendelesDto();
        dto.setId(order.getId());
        dto.setSzallitoId(order.getSzallito().getId());
        dto.setVevoId(order.getVevo().getId());
        dto.setLezarva(order.isLezarva());
        dto.setMikorra(order.getMikorra());
        dto.setMegjegyzes(order.getMegjegyzes());
        dto.setRogzitve(order.getRogzitve());
        dto.setTetelek(tetelekDto);
        dto.setSzamlaszam(order.getSzamlaszam());
        return dto;    
    }
    
   public List<MegrendelesDto> toDtos(List<Megrendeles> order) {
        if ( order == null ) {
            return null;
        }

        List<MegrendelesDto> list = new ArrayList<>( order.size() );
        for ( Megrendeles order1 : order ) {
            list.add( this.toDto( order1 ) );
        }
        return list;
    }    
}
