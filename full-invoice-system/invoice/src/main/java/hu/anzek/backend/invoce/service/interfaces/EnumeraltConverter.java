/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hu.anzek.backend.invoce.service.interfaces;


import org.springframework.stereotype.Component;


/**
 * . Az adatbázisban "tinyint" (rövid int, 0..255 közötti egész érték), amely enum, boolen (0,1), stb tárolására
 * használt típusban tárolja a Hibernate az enumerációt - alapértelmezésben! 
 * . Az "Ordinal Mapping statégia" befolyásolja az ORM eszköz (Hibernate) műköését... (olvass utána). 
 * Viszont a MySql -ben alpértelmezett a sorszámozás, de ha ez nem felel meg akkor, 
 * így lehet "manuálisan" előkészíteni (pl) a Workbench-ben a "FizetesiModok.enum" -ot:
 * CREATE TYPE status_enum AS ENUM ('KESZPENZ_CASH', 'ATUTALAS_TRANSFER', 'BANKKARTYA_HITEL_BANKDEBIT_CREDIT', 'EGYEB_OTHER');
 * - majd az entitás mezőre rátenni a Java kódban:
 * :
 * public class PartnerCimHelysegDto{
 * : // további mezők
 * @Enumerated(EnumType.STRING)
 * private FizetesiModok partner_fizmod;
 * : // a kód többi része
 * }
 * A saját entitásba kiolvasást a Hibernate megoldja, de egy natív Select nem tud típuskényszeríteni, így a RESULTSET-ben, 
 * - illetve annak kiolvasáskor - ez nem alakul vissza automatikusan az adott enumerálttá, 
 * ezért nem is fogja tudni hiba nélkül (egy az egyben) (pl) dto-vá alakítani...
 * 
 * @param <T> típus (az osztály típusa)
 * @param <U> az adatbázisban tárolt típus (Integer)
 * @author User
 */
@Component
public interface EnumeraltConverter <T extends Object, U extends Object> {
    
    U convertToAdatbazisTablaMezoFromEntitasAttributum(T attribute);    
    T convertToEntitasAttributumFromAdatbazisTablaMezo(U dbData);
}
