/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.service;


import hu.anzek.backend.invoce.datalayer.dto.InvUserDto;
import hu.anzek.backend.invoce.datalayer.mapper.InvUserMapper;
import hu.anzek.backend.invoce.datalayer.model.InvUser;
import hu.anzek.backend.invoce.datalayer.repository.InvUserRepo;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class InvUserService {    
    
    private final InvUserRepo invUserRepo;
    
    @Autowired
    public InvUserService(InvUserRepo invUserRepo) {
        this.invUserRepo = invUserRepo;
    }
    
    /**    
     * Kiolvas az adatbázisból egy InvUser-t az Id-ra!<br>
     * Mivel Optional az alapértelmezés, ezért ki kell csomagolni - ha van mit.
     * @param id a user azonosító<br>
     * @return visszaad egy usert, vagy null értéket<br>
     */
    public InvUser findUser(long id){         
        Optional<InvUser> optIv = this.invUserRepo.findById(id);        
        if(optIv.isEmpty()){            
            return null;
        }else{            
            return optIv.get();
        }        
    }
    
    /**
     * Új Rendszer-Felhasználó felvitele az SQL-be<br>
     * @param user az InvUser entitas<br>
     * @return ha sikeres volt a visszaolvasott (Id-val kiegészült) teljes entitas<br>
     */
    @Transactional
    public InvUser newUser(InvUser user){
        if(this.invUserRepo.findByUserName(user.getUserName()) == null){
            return this.invUserRepo.save(user);
        }else{
            return null;
        }
    }
        
    /**
     * Meglévő Rendszer-Felhasználó módosítása az SQL-ben (Id -re keres)<br>
     * @param user az InvUser entitas<br>
     * @return ha sikeres volt a visszaolvasott (Id-val kiegészült) teljes entitas<br>
     */
    @Transactional
    public InvUser modifyUser(InvUser user){
        if(this.invUserRepo.findById(user.getId()) != null){            
            return this.invUserRepo.save(user);
        }else{
            return null;
        }
    }    

    /**
     * Visszaadja az összes felhasználót<br>
     * @return az összes felhasználó, vagy null-lista<br>
     */
    public List<InvUser> findAll(){
        return this.invUserRepo.findAll();
    }
    
    /**
     * Az azonosítóra (ha létezik) törli aa feéhasználót!<br>
     * @param id a felhasználó azonosítója<br>
     * @return visszadja a kitörölt antitást<br>
     */
    public InvUser deleteUser(long id) {
        InvUser inv = this.findUser(id);
        if( inv != null){
            this.invUserRepo.deleteById(id);
        }
        return inv;
    }
    /////////////////////////////////////////////////////////////
    // SpringSecurity megoldással:
    /////////////////////////////////////////////////////////////
    public InvUserDto getUserByIdSpringSecurity(Long id) {
        InvUser invUser = invUserRepo.findById(id).orElseThrow(() -> new RuntimeException("A felhasználo nem talalhato!"));
        return InvUserMapper.INSTANCE.invUserToDto(invUser);
    }

    public InvUserDto saveUserSpringSecurity(InvUserDto invUserDto) {
        InvUser invUser = InvUserMapper.INSTANCE.dtoToInvUser(invUserDto);
        invUser = invUserRepo.save(invUser);
        return InvUserMapper.INSTANCE.invUserToDto(invUser);
    }    
}
