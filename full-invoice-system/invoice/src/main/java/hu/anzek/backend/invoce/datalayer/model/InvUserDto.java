/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.model;


import org.springframework.stereotype.Service;


/**
 *
 * @author User
 */
@Service
public class InvUserDto {
    private String userName;    
    private String pw;
    private String ls;

    public InvUserDto() {
    }

    public InvUserDto(String userName,
                      String pw,
                      String ls) {
        this.userName = userName;
        this.pw = pw;
        this.ls = ls;
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
    
}
