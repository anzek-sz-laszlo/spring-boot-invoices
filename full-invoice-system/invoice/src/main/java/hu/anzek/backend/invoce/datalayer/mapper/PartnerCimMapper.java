/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.mapper;


import hu.anzek.backend.invoce.datalayer.model.Cimadat;
import hu.anzek.backend.invoce.datalayer.model.HelysegnevTar;
import hu.anzek.backend.invoce.datalayer.model.Partner;
import org.springframework.stereotype.Service;


/**
 *
 * @author User
 */
@Service
public class PartnerCimMapper {
    
    public Cimadat setCimadatFromPartner(Partner partner){           
        Cimadat partnerCim = new Cimadat();
        partnerCim.setId(partner.getPartner_cim().getId());
        partnerCim.setTelepules(partner.getPartner_cim().getTelepules());
        partnerCim.setUtca(partner.getPartner_cim().getUtca());
        partnerCim.setKozterulet(partner.getPartner_cim().getKozterulet());
        partnerCim.setHazszam(partner.getPartner_cim().getHazszam());
        
        return partnerCim;
    }
    
    public HelysegnevTar setHelysegnevTarFromPartner(Partner partner){
        return partner.getPartner_cim().getTelepules();
    }
    
    public Partner setPartnerCimFromCimadat( Cimadat cim, Partner partner){       
        partner.setPartner_cim(cim);
        return partner;
    }
}
