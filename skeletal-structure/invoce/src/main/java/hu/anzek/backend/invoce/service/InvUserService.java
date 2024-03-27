/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.service;


import hu.anzek.backend.invoce.datalayer.model.InvUser;
import hu.anzek.backend.invoce.datalayer.repository.InvUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author User
 */
@Service
public class InvUserService {
    
    @Autowired
    InvUserRepo invUserRepo;
    
    @Transactional
    public InvUser newUser(InvUser invUser){
        if( this.invUserRepo.findByUserName(invUser.getUserName()) == null){
            return this.invUserRepo.save(invUser);
        }
        return null;
    }
}
