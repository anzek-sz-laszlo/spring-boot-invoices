/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import org.springframework.stereotype.Repository;


/**
 *
 * @author User
 */
@Repository
@Entity
public class InvUser implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(unique = true)
    private String userName; 
    private String pw;
    private String ls;

    public InvUser(){
    }

    public InvUser(String userName, String pw, String ls) {            
        this.userName = userName;
        this.pw = pw;
        this.ls = ls;
    }

    @Override
    public String toString(){
        return "UserEntity{ Id = " + this.id + " | NameValue = " + this.userName + " | PasswordValue = " + this.pw + " | authorized = " + ls + "}";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getLs() {
        return ls;
    }

    public void setLs(String ls) {
        this.ls = ls;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
