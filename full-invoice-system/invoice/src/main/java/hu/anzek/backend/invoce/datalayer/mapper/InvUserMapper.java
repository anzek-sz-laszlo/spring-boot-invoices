/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.mapper;


import hu.anzek.backend.invoce.datalayer.dto.InvUserDto;
import hu.anzek.backend.invoce.datalayer.model.InvUser;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 *
 * @author User
 */
@Mapper(componentModel = "spring", uses = {AuthorityMapper.class})
public interface InvUserMapper {
    InvUserMapper INSTANCE = Mappers.getMapper(InvUserMapper.class);
        
    @Mapping(target = "authorities", source = "authorities", qualifiedByName = "mapGrantAuthoritiesToStringList")
    public InvUserDto invUserToDto(InvUser invUser); 
    public List<InvUserDto> invUsersToDtos(List<InvUser> invUsers);
    
    //@Mapping(target = "authorities", source = "authorities", qualifiedByName = "mapStringListToGrantAuthorities") 
    public InvUser dtoToInvUser(InvUserDto invUserDto);
    public List<InvUser> dtosToInvUsers(List<InvUserDto> invUserDtos);    

    @Named("mapStringListToGrantAuthorities")
    static List<SimpleGrantedAuthority> mapStringsToAuthorities(List<String> authorities) {
        return authorities.stream()
                            // Ez (a konstruktorhívás) pont ugyan azt jelenti, mint az alábbi sor: .map(SimpleGrantedAuthority::new)
                          .map(e -> new SimpleGrantedAuthority(e)) 
                          .collect(Collectors.toList());
    }
    
    // itt elég fura módon nem fogadta el bemenetként a "List<SimpleGrantedAuthority>" -t
    // és az IDE javasolta, hogy "próbáljam meg" a bemenő paraméterként a felsőkorlátos kollekció meghatározással
    // ehhez viszont nem árt utánna nézni, hogy a szóban forgó osztály minek a leszármaztatotja:
    // Emlékeztetőül:
    // alsó korlát megadás (talán emlékezünk rá): Collection<? super T> a T, vagy minden szint, amelyből T származtatva van
    // felső korlát: Collection<? extends T> azaz a T, vagy minden,a mi T -ből van származtatva!
    @Named("mapGrantAuthoritiesToStringList")
    static List<String> mapAuthoritiesToStrings(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                          .map(GrantedAuthority::getAuthority)
                          .collect(Collectors.toList());
    }    
}
