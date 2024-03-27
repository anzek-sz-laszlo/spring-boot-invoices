/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.service;


import hu.anzek.backend.invoce.datalayer.model.InvUser;
import hu.anzek.backend.invoce.datalayer.repository.InvUserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author User
 */
@Service
public class InvUserService {
    
    @Autowired
    InvUserRepo invUserRepo;

    public InvUserService() {
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
     * Meglévő Rendszer-Felhasználó módosítása az SQL-be<br>
     * @param user az InvUser entitas<br>
     * @return ha sikeres volt a visszaolvasott (Id-val kiegészült) teljes entitas<br>
     */
    @Transactional
    public InvUser modifyUser(InvUser user){
        if(this.invUserRepo.findByUserName(user.getUserName()) != null){
            return this.invUserRepo.save(user);
        }else{
            return null;
        }
    }    
}
