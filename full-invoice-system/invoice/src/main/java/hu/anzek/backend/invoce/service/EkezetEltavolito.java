/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.service;


import lombok.NoArgsConstructor;


/**
 *
 * @author User
 */
@NoArgsConstructor
public class EkezetEltavolito {
    public static String uniform(String input) {
        return input.replaceAll("[á]", "a")
                    .replaceAll("[é]", "e")
                    .replaceAll("[í]", "i")
                    .replaceAll("[óöő]", "o")
                    .replaceAll("[úüű]", "u")
                    .replaceAll("[ő]", "o")
                    .replaceAll("[ű]", "u")
                    .replaceAll("[Á]", "A")
                    .replaceAll("[É]", "E")
                    .replaceAll("[Í]", "I")
                    .replaceAll("[ÓÖŐ]", "O")
                    .replaceAll("[ÚÜŰ]", "U")
                    .replaceAll("[Ő]", "O")
                    .replaceAll("[Ű]", "U");
    }
}
