/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.service;


/**
 *
 * @author User
 */
public class UserNotFoundException extends Exception {    
    
    private static final long serialVersionUID = 1L;
    
    public UserNotFoundException(String message){    
        super(message);
    }    
}
