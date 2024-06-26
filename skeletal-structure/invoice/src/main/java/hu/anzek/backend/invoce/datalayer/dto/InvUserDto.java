/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.dto;


import org.springframework.stereotype.Component;


/**
 *
 * @author User
 */
@Component
public class InvUserDto {
    private long id;
    private String userName;    
    private String pw;
    private String ls;

    public InvUserDto() {
    }

    public InvUserDto(long id,
                      String userName,
                      String pw,
                      String ls) {
        this.id = id;
        this.userName = userName;
        this.pw = pw;
        this.ls = ls;
    }

    public String getUserName() {
        return userName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
