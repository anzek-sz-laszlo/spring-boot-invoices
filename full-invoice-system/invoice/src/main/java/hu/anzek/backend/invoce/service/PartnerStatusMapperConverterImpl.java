/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.service;


import hu.anzek.backend.invoce.service.enumeralt.PartnerStatus;
import hu.anzek.backend.invoce.service.interfaces.EnumeraltConverter;
import org.springframework.stereotype.Component;


/**
 *
 * @author User
 */
@Component
public class PartnerStatusMapperConverterImpl implements EnumeraltConverter <PartnerStatus, Integer>{

    @Override
    public Integer convertToAdatbazisTablaMezoFromEntitasAttributum(PartnerStatus attribute) {
        return attribute.getIntValue();
    }

    @Override
    public PartnerStatus convertToEntitasAttributumFromAdatbazisTablaMezo(Integer dbData) {
        if (dbData != null) {
            for (PartnerStatus statusz : PartnerStatus.values()) {
                if (statusz.getIntValue() == dbData) {
                    return statusz;
                }
            }
        }
        return null;
    }
}
