/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.service;


import hu.anzek.backend.invoce.service.enumeralt.Mozgasok;
import hu.anzek.backend.invoce.service.interfaces.EnumeraltConverter;
import org.springframework.stereotype.Component;


/**
 *
 * @author User
 */
@Component
public class MozgasokMapperConverterImpl implements EnumeraltConverter <Mozgasok, Integer> {

    @Override
    public Integer convertToAdatbazisTablaMezoFromEntitasAttributum(Mozgasok attribute) {
        return attribute.getIntValue();
    }

    @Override
    public Mozgasok convertToEntitasAttributumFromAdatbazisTablaMezo(Integer dbData) {
        if (dbData != null) {
            for (Mozgasok mozgas : Mozgasok.values()) {
                if (mozgas.getIntValue() == dbData) {
                    return mozgas;
                }
            }
        }
        return null;
    }
}
