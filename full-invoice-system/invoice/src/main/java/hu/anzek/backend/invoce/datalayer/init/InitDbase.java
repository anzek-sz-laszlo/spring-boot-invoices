/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.init;


import hu.anzek.backend.invoce.datalayer.model.InvUser;
import hu.anzek.backend.invoce.datalayer.repository.InvUserRepo;
import hu.anzek.backend.invoce.service.InvUserService;
import hu.anzek.backend.invoce.service.Jogosultsagok;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class InitDbase {
    
    @Autowired
    private InvUserService invUserService;
    @Autowired
    private InvUserRepo userRepo;
    
    public InitDbase(){
    }
    
    /**
     * Ha üres az adattár, feltöltjük teszt adatokkal<br>
     */
    public void initDbase(){
        // lekérjük az összes felhasználót:
        if(this.userRepo.findAll().isEmpty()){
            this.invUserService.newUser(new InvUser("user", "pwuser", Jogosultsagok.GLOBAL_USER.name()) );
            this.invUserService.newUser(new InvUser("invoice", "pwinvoice", Jogosultsagok.INVOICING_USER.name()) );
            this.invUserService.newUser(new InvUser("master", "pwmaster", Jogosultsagok.MASTER_DATA_ACCESS_USER.name()) );
            this.invUserService.newUser(new InvUser("observer", "pwobserver", Jogosultsagok.OBSERVER_USER.name()) );
            this.invUserService.newUser(new InvUser("system176admin", "pwsystem176admin", Jogosultsagok.GLOBAL_SYSTEMADMIN.name()) );
        }
    }
}
