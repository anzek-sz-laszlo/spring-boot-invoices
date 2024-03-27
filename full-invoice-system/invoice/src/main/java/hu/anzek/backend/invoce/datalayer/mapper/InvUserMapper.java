/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.mapper;


import hu.anzek.backend.invoce.datalayer.model.InvUser;
import hu.anzek.backend.invoce.datalayer.model.InvUserDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author User
 */
@Mapper(componentModel = "spring")
public interface InvUserMapper {
    InvUserDto invUserDto(InvUser invUser);
    List<InvUserDto> invUserDtos(List<InvUser> invUsers);
    
    @Mapping(target = "id", ignore = true)
    InvUser invUser(InvUserDto invUserDto);
    List<InvUser> invUsers(List<InvUserDto> invUserDtos);    
}
