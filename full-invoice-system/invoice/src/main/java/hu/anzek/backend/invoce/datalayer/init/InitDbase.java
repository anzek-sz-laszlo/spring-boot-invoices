/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.init;


import hu.anzek.backend.invoce.datalayer.model.InvUser;
import hu.anzek.backend.invoce.datalayer.repository.InvUserRepo;
import hu.anzek.backend.invoce.service.InvUserService;
import hu.anzek.backend.invoce.service.enumeralt.Jogosultsagok;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class InitDbase {
        
    private final InvUserService invUserService;    
    private final InvUserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitDbase(InvUserService invUserService,
                     InvUserRepo userRepo,
                     PasswordEncoder passwordEncoder){
        this.invUserService = invUserService;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }
    
    public void createUser1() {
        String username = "anzek";
        String rawPassword = "176Backend";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        List<String> authorities = List.of(Jogosultsagok.GLOBAL_SYSTEMADMIN.name());
        InvUser user = new InvUser(username, encodedPassword, "AKTIV", authorities);
        this.userRepo.save(user);
    }    
    
    public void createUser2() {
        String username = "user";
        String rawPassword = "pwuser";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        List<String> authorities = List.of("GLOBAL_USER");
        InvUser user = new InvUser(username, encodedPassword, "AKTIV", authorities);
        this.userRepo.save(user);
    }   
    
    public void createUser3() {
        String username = "invoice";
        String rawPassword = "pwinvoice";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        List<String> authorities = List.of("INVOICING_USER");
        InvUser user = new InvUser(username, encodedPassword, "AKTIV", authorities);
        this.userRepo.save(user);
    }    
    
    public void createUser4() {
        String username = "master";
        String rawPassword = "pwmaster";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        List<String> authorities = List.of("MASTER_DATA_ACCESS_USER");
        InvUser user = new InvUser(username, encodedPassword, "AKTIV", authorities);
        this.userRepo.save(user);
    } 
    
    public void createUser5() {
        String username = "observer";
        String rawPassword = "pwobserver";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        List<String> authorities = List.of("OBSERVER_USER");
        InvUser user = new InvUser(username, encodedPassword, "AKTIV", authorities);
        this.userRepo.save(user);
    } 
        
    /**
     * Ha üres az adattár, feltöltjük teszt adatokkal<br>
     */
    public void initDbase(){
        if(this.userRepo.findAll().isEmpty()){
            // a 30. héttől már Springes PasswordEncoding használata kell:
            this.createUser1();
            this.createUser2();
            this.createUser3();
            this.createUser4();
            this.createUser5();
            // 29.-30. héttől ez márkiiktatva
            //        // lekérjük az összes felhasználót:
            //        
            //            this.invUserService.newUser(new InvUser("user", "pwuser", Jogosultsagok.GLOBAL_USER.name()) );
            //            this.invUserService.newUser(new InvUser("invoice", "pwinvoice", Jogosultsagok.INVOICING_USER.name()) );
            //            this.invUserService.newUser(new InvUser("master", "pwmaster", Jogosultsagok.MASTER_DATA_ACCESS_USER.name()) );
            //            this.invUserService.newUser(new InvUser("observer", "pwobserver", Jogosultsagok.OBSERVER_USER.name()) );
            //            this.invUserService.newUser(new InvUser("system176admin", "pwsystem176admin", Jogosultsagok.GLOBAL_SYSTEMADMIN.name()) );
        }
    }
}
