/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.repository;


import hu.anzek.backend.invoce.datalayer.model.InvUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 *
 * @author User
 */
@Repository
public interface InvUserRepo extends JpaRepository<InvUser, Long> {    
    /**
     * Van-e ilyen nevű felhasználónk?<br>
     * @param userName a felhasználó neve<br>
     * @return true/false<br>
     */
    public InvUser findByUserName(String userName);
    // Nativ MySqk parancs:
    // SELECT * FROM inv_user WHERE (user_name = :userName) AND (pw = :pw) AND (ls=:ls);
    // JPA automata implementációja:
    //      InvUser findByUserNameAndPwAndLs(String userName, String pw,String ls){
    //      String queryStr = "SELECT * FROM inv_user WHERE (user_name =" + userName + ") AND (pw =" + pw + ") AND (ls=" + ls + ");"
    //      return entityManager.createNativQuery(queryStr);
    // }    
    public List<InvUser> findByUserNameAndPwAndLs(@Param("ls") 
                                                  String s1, 
                                                  @Param("pw") 
                                                  String s2, 
                                                  @Param("userName") 
                                                  String s3);
}
