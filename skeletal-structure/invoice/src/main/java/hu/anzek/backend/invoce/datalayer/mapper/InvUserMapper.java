/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.mapper;


import hu.anzek.backend.invoce.datalayer.model.InvUser;
import hu.anzek.backend.invoce.datalayer.model.InvUserDto;
import java.util.List;
import org.mapstruct.Mapper;

/**
 *
 * @author User
 */
@Mapper(componentModel = "spring")
public interface InvUserMapper {
    
    // 1
    public InvUserDto invUserToDto(InvUser invUser);
    
    // 2
    public List<InvUserDto> invUsersToDtos(List<InvUser> invUsers);
    
    // 3
    // @Mapping(target = "id", ignore = true)
    public InvUser dtoToInvUser(InvUserDto invUserDto);
    
    // 4
    public List<InvUser> dtosToInvUsers(List<InvUserDto> invUserDtos);    
}
