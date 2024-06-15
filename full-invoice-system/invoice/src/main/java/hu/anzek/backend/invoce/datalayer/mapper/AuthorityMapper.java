/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.mapper;


import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


/**
 *
 * @author User
 */
@Mapper
public interface AuthorityMapper {
    AuthorityMapper INSTANCE = Mappers.getMapper(AuthorityMapper.class);

    @Named("mapGrantAuthoritiesToStrings")
    default List<String> mapAuthoritiesToStrings(List<SimpleGrantedAuthority> authorities) {
        return authorities.stream()
                          .map(SimpleGrantedAuthority::getAuthority)
                          .collect(Collectors.toList());
    }

    @Named("mapStringsToAuthorities")
    default List<SimpleGrantedAuthority> mapStringsToAuthorities(List<String> authorities) {
        return authorities.stream()
                          .map(SimpleGrantedAuthority::new)
                          .collect(Collectors.toList());
    }
}
