/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.service;


import hu.anzek.backend.invoce.service.enumeralt.FizetesiModok;
import hu.anzek.backend.invoce.service.interfaces.EnumeraltConverter;
import org.springframework.stereotype.Component;


/**
 * 
 * @author User
 */
@Component
public class FizmodMapperConverterImpl implements EnumeraltConverter <FizetesiModok, Integer> {

    @Override
    public Integer convertToAdatbazisTablaMezoFromEntitasAttributum(FizetesiModok attribute) {
        return attribute.getFizmod();
    }

    @Override
    public FizetesiModok convertToEntitasAttributumFromAdatbazisTablaMezo(Integer dbData) {
        if (dbData != null) {
            for (FizetesiModok modok : FizetesiModok.values()) {
                if (modok.getFizmod() == dbData) {
                    return modok;
                }
            }
        }
        return null;
    }
}
