/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.repository;


import hu.anzek.backend.invoce.datalayer.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 *
 * @author User
 */
@Repository
public interface PartnerRepository extends JpaRepository<Partner,Long> {    
   
//    @Query(value =  "SELECT " +
//                    "    p.id AS partner_id," +
//                    "    p.megnevezes AS partner_megnevezes," +
//                    "    p.adoszam AS partner_adoszam," +
//                    "    p.kozossegi_asz AS partner_kozossegi_asz," +
//                    "    p.vevo_szallito AS partner_vevo_szallito," +
//                    "    p.fizmod AS partner_fizmod, " +
//                    "    c.id AS cimadat_id," +
//                    "    h.irszam AS helyseg_irszam," +
//                    "    h.helyseg AS helyseg_helyseg, " +
//                    "    c.utca AS cimadat_utca," +
//                    "    c.kozterulet AS cimadat_kozterulet," +
//                    "    c.hazszam AS cimadat_hazszam, " +
//                    "    p.egyeb_info AS partner_egyeb_info " +
//                    "FROM" +
//                    "    partnerek p" +
//                    "        JOIN" +
//                    "    cimadat c ON p.partner_cim_id = c.id" +
//                    "        JOIN" +
//                    "    helysegnev_tar h ON c.helyseg_irszam = h.irszam", nativeQuery = true)
//    public List<PartnerCimHelysegDto> findAllPartnerCimLista2();
//    
//    @Query("SELECT "
//            "    p.id AS partner_id," +
//            "    p.megnevezes AS partner_megnevezes," +
//            "    p.adoszam AS partner_adoszam," +
//            "    p.kozossegi_asz AS partner_kozossegi_asz," +
//            "    p.vevo_szallito AS partner_vevo_szallito," +
//            "    p.fizmod AS partner_fizmod, " +
//            "    c.id AS cimadat_id," +
//            "    h.irszam AS helyseg_irszam," +
//            "    h.helyseg AS helyseg_helyseg, " +
//            "    c.utca AS cimadat_utca," +
//            "    c.kozterulet AS cimadat_kozterulet," +
//            "    c.hazszam AS cimadat_hazszam, " +
//                    "    p.egyeb_info AS partner_egyeb_info " +
//            + "FROM "
//            + "     Partner p "
//            + "         JOIN "
//            + "     p.partner_cim c "
//            + "         JOIN "
//            + "     c.telepules h")
//    List<PartnerCimHelysegDto> findAllPartnerCimlista3();
}
