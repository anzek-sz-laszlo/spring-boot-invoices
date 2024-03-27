/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.repository;


import hu.anzek.backend.invoce.datalayer.model.InvUser;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 *
 * @author User
 */
public interface InvUserRepo extends JpaRepository<InvUser, Long> {    
    /**
     * Van-e ilyen nevű felhasználónk?<br>
     * @param userName a felhasználó neve<br>
     * @return true/false<br>
     */
    public InvUser findByUserName(String userName);
}
