/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.controller.webControllers;


import hu.anzek.backend.invoce.datalayer.mapper.InvUserMapper;
import hu.anzek.backend.invoce.datalayer.model.InvUser;
import hu.anzek.backend.invoce.datalayer.dto.InvUserDto;
import hu.anzek.backend.invoce.service.InvUserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 *
 * @author User
 */
@Controller
@RequestMapping("/invusers")
public class InvUserController {
    
    @Autowired
    private InvUserService userService;    
    @Autowired
    private InvUserMapper userMapper;
    
    @GetMapping
    public String getUserList(Map<String,List<InvUserDto>> model1,
                              Map<String,InvUserDto> model2) {
        model1.put("invUserek", this.userMapper.invUsersToDtos(this.userService.findAll()));
        model2.put("ujFelhasznalo", new InvUserDto());   
        return "invUserCrud";
    }

    @PostMapping("/invuser_add")
    public String createUser( @ModelAttribute("userDto") 
                              InvUserDto userDto ) {
        System.out.println("Rögzített újbevitel: " + this.userService.newUser(this.userMapper.dtoToInvUser(userDto)) );
        return "redirect:/invusers";
    }
    
    @GetMapping("/invuser_modify/{id}")    
    public String editDolgozo(  Map<String, Object> model, 
                                @PathVariable 
                                long id ){
        InvUser iu = this.userService.findUser(id);
        if(iu != null){            
            model.put("modifyInvUser", this.userMapper.invUserToDto(this.userService.findUser(id)));
            return "invUserModify";
        }else{            
            model.put("error", "Figyelem hibás felhasználó azonosító (nem található), így a módosítás sikertelen!");
            model.put("status", "1001");
            return "error_message";
        }
    }
                              
    @PostMapping("/invuser_modify")
    public String preModifyUser( @ModelAttribute("userDto") 
                                 InvUserDto userDto,
                                 Map<String, Object> model ){ 
        System.out.println("Rögzített módosítás: " + this.userService.modifyUser(this.userMapper.dtoToInvUser(userDto)) );
        return "redirect:/invusers";
    }

    @GetMapping("/invuser_delete/{id}")    
    public String deleteUser( @PathVariable 
                              long id) {
        System.out.println("Rögzített törlés: " + this.userService.deleteUser(id) );
        return "redirect:/invusers";
    }  
}
