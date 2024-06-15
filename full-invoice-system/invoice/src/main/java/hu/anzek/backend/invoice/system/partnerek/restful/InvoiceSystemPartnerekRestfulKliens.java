/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package hu.anzek.backend.invoice.system.partnerek.restful;

import hu.anzek.backend.invoce.datalayer.dto.CimadatDto;
import hu.anzek.backend.invoce.datalayer.dto.PartnerCimHelysegDto;
import hu.anzek.backend.invoce.service.enumeralt.FizetesiModok;
import hu.anzek.backend.invoce.service.enumeralt.PartnerStatus;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author User
 */
public class InvoiceSystemPartnerekRestfulKliens {

    private static final String BASE_PARTNER_URL = "http://localhost:8080/api/partners";
    private static final String BASE_CIMADAT_URL = "http://localhost:8080/api/cimadat";

    @Value("${api.username}")
    private String username;

    @Value("${api.password}")
    private String password;    
  
    /**     
     * . Spring Framework alapvető osztályainak egyike.<br>
     * Feladata, hogy egyszerűsítse a HTTP kérések küldését és fogadását Java alkalmazásokban. <br>
     * Ő egy kliensoldali HTTP könyvtár. <br>
     * Lehetővé teszi az alkalmazások számára, hogy:<br> 
     * - HTTP kéréseket küldjenek és<br> 
     * - RESPONSE válaszokat fogadjanak <br>
     * különböző HTTP metódusokkal, mint például GET, POST, PUT, DELETE<br>
     */
    private final RestTemplate restTemplate = new RestTemplate();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        InvoiceSystemPartnerekRestfulKliens kliens = new InvoiceSystemPartnerekRestfulKliens();        
        if(kliens.loadConfig()){     
            kliens.configureRestTemplate();
            kliens.run();
        }
    }

    private boolean loadConfig() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sajnalom, de nem talalhato a 'config.properties' fajl!");
                return false;
            }
            properties.load(input);
            this.username = properties.getProperty("api.username");
            this.password = properties.getProperty("api.password");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("\n\nSajnalom, de hiba allt elo!");
            return false;
        }
        return true;
    }

    private void configureRestTemplate() {
        this.restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(this.username, this.password));
    }
    
    private void run() {
        while (true) {
            System.out.println("Belepve = " + this.username + " pw = " + this.password +"\n");
            System.out.println("Valaszthatsz az alabbi lehetosegekbol:\n");
            System.out.println("1. Listazzuk ki az osszes partnert ( getAll() )");
            System.out.println("2. Keressunk partnert egy Id-re ( getById( partner.id )");
            System.out.println("3. Hozzunk letre egy ujat ( create(partner) )");
            System.out.println("4. Modositsuk az Id-vel rendelkezo partnert ( update( partner.id ) )");
            System.out.println("5. Toroljuk a partnert (delete( partner.id )");
            System.out.println("0. Kilepes");
            
            int valasztasunk = this.scanner.nextInt();
            this.scanner.nextLine(); 

            switch (valasztasunk) {
                case 1:
                    this.getAllPartners();
                    break;
                case 2:
                    this.getPartnerById();
                    break;
                case 3:
                    this.createPartner();
                    break;
                case 4:
                    this.updatePartner();
                    break;
                case 5:
                    this.deletePartner();
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("Ervenytelen valasztas, probald meg ujra!\n");
            }
        }
    }
   
    private void getAllPartners() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(this.username, this.password); 

        HttpEntity<String> entity = new HttpEntity<>(headers);
                
        // Ez a ResponseEntity<T> osztály is a Spring alapvető osztályai közé tartozik: 
        // a HTTP válaszok reprezentálására, kezelésére szolgál. 
        // Lehetővé teszi a HTTP válaszok:
        // - állapotkódjainak (megadhatjuk-, lekérhetjük a válasz állapotkódot:  200 OK, 404 Not Found, stb.), 
        // - fejlécének és 
        // - törzsének kezelését. 
        // Segítségével rugalmasan kezelhető a HTTP válaszok a RESTful szolgáltatásokban.
        ResponseEntity<PartnerCimHelysegDto[]> response = this.restTemplate.exchange(BASE_PARTNER_URL, HttpMethod.GET, entity, PartnerCimHelysegDto[].class);
        System.out.println("Statuszkod=" + response.getStatusCode() + " - A teljes partner-lista : \n[ " + response.toString() +" ]\n");
        if (response.getStatusCode() == HttpStatus.OK) {
            PartnerCimHelysegDto[] partners = response.getBody();
            if(partners.length > 0 ){
                int i = 0;
                for (PartnerCimHelysegDto partner : partners) {
                    System.out.println(i++ + ". " + partner.toString());
                }
            }else{
                System.out.println("Nincs listazhato adat!");
            }
        } else {
            System.out.println("Nem sikerült lekerni a partnereket, allapotkod: " + response.getStatusCode());
        }        
        System.out.println("---- vege.");
    }

    private void getPartnerById() {
        System.out.print("Kerem a partner ID: ");
        Long id = this.scanner.nextLong();
        // a newline kiolvasása:
        this.scanner.nextLine(); 
        // egy GET kérésből visszaposztoljuk JSON body-ban: 
        ResponseEntity<PartnerCimHelysegDto> response = this.restTemplate.getForEntity(BASE_PARTNER_URL + "/" + id, PartnerCimHelysegDto.class);
        System.out.println("Statuszkod=" + response.getStatusCode() + " - A keresett partner adatai: " + response.toString());
    }

    @SuppressWarnings("null")
    private void createPartner() {
        System.out.print("Kerem a partner nevet : ");
        String memegnevezese = this.scanner.nextLine();
        System.out.print("Adoszámat : ");
        String aSzam = this.scanner.nextLine();
        System.out.print("EU-s adoszamat : ");
        String kozossegiAsz = this.scanner.nextLine();
        System.out.print("Egyeb informaciokt : ");
        String egyeb = this.scanner.nextLine();
        
        System.out.println("Add meg a partner-státuszt ezekbol valasztva :");
        System.out.println("1. VEVO");
        System.out.println("2. SZALLITO");
        System.out.println("3. MINDKETTO");
        int vevoSzallitoChoice = this.scanner.nextInt();
        this.scanner.nextLine();
        PartnerStatus vevoSzallito = PartnerStatus.values()[vevoSzallitoChoice - 1];

        System.out.println("Add meg a fizetesi modot ezekbol valasztva :");
        System.out.println("1. CASH");
        System.out.println("2. TRANSFER");
        System.out.println("3. OTHER");
        int fizmodChoice = this.scanner.nextInt();
        this.scanner.nextLine(); 
        FizetesiModok fizmod = FizetesiModok.values()[fizmodChoice - 1];

        System.out.print("Kerlek adj meg egy letezo cim-kodot - vagy 0-t: ");
        String cimadatId = this.scanner.nextLine();
        if(cimadatId.equals("0")) cimadatId = null;

        String utca = "";
        String kozterulet = "";
        String hazszam = "";
        String irszam = "";
        String helyseg = "";
        Long cimId = null;
        
        if(cimadatId == null){        
            System.out.print("Kerem a kozterület nevet: ");
            utca = this.scanner.nextLine();
            System.out.print("A kozterulet tipusat : ");
            kozterulet = this.scanner.nextLine();
            System.out.print("A hazszamot : ");
            hazszam = this.scanner.nextLine();
            System.out.print("Az iranyitoszamot : ");
            irszam = this.scanner.nextLine();
            System.out.print("Kerem a települes nevet : ");
            helyseg = this.scanner.nextLine();
        }else{
            // lekérdezzük a "megadott" cim Id - értékét és kiírjuk amit ide választott
            // illetve értékként is visszadjuk a komplex dto -ba!:
            ResponseEntity<CimadatDto> response = this.restTemplate.getForEntity(BASE_CIMADAT_URL + "/" + cimadatId, CimadatDto.class);         
            CimadatDto cimadatDto = response.getBody();
            System.out.println( cimadatDto.toString());
            cimId = cimadatDto.getId();
            utca = cimadatDto.getUtca();
            kozterulet = cimadatDto.getKozterulet();
            hazszam = cimadatDto.getHazszam();
            irszam = cimadatDto.getTelepules().getIrszam();
            helyseg = cimadatDto.getTelepules().getHelyseg();
        }
        // betöltjük a Dto -ba (ahogy a tymeleaf sablon is teszi):
        PartnerCimHelysegDto newPartner = new PartnerCimHelysegDto();
        newPartner.setPartner_megnevezes(memegnevezese);
        newPartner.setPartner_adoszam(aSzam);
        newPartner.setPartner_kozossegi_asz(kozossegiAsz);
        newPartner.setPartner_vevo_szallito(vevoSzallito.getIntValue());
        newPartner.setPartner_fizmod(fizmod.getFizmod());        
        newPartner.setPartner_egyeb_info(egyeb);
        newPartner.setCimadat_id(cimId);
        newPartner.setCimadat_utca(utca);
        newPartner.setCimadat_kozterulet(kozterulet);
        newPartner.setCimadat_hazszam(hazszam);
        newPartner.setHelyseg_irszam(irszam);
        newPartner.setHelyseg_helyseg(helyseg);
       
        // visszaposztoljuk a RESTful kontrollernek egy JSON body ban:
        ResponseEntity<PartnerCimHelysegDto> response = this.restTemplate.postForEntity(BASE_PARTNER_URL, newPartner, PartnerCimHelysegDto.class);
        PartnerCimHelysegDto createdPartner = response.getBody();
        createdPartner.toConsol("Statuszkod=" + response.getStatusCode() + " - Partner letrehozasa : ");
    }

    @SuppressWarnings("null")
    private void updatePartner() {
        System.out.print("Kerem a modositando partner ID-jet: ");
        Long id = this.scanner.nextLong();
        this.scanner.nextLine(); 
        
        // Lekérjük az aktualis partnert az ID alapján
        String url = BASE_PARTNER_URL + "/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(this.username, this.password);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<PartnerCimHelysegDto> response = this.restTemplate.exchange(url, HttpMethod.GET, entity, PartnerCimHelysegDto.class);
        PartnerCimHelysegDto partnerViewDto = response.getBody();
        
        // ha van ilyen vevő - amit módosítani szeretnénk:
        if (partnerViewDto != null) {           
            // Bekérjük a módosítandó adatokat
            System.out.print("Figyelem!");
            System.out.print("Ha nem modosul az adat, eleg <Entert> -t nyomni!\n");
   
            System.out.print("Kerem az uj nevet (aktualis: " + partnerViewDto.getPartner_megnevezes() + "): ");
            String megnevezese = this.scanner.nextLine();
            if(megnevezese.isEmpty()) megnevezese = partnerViewDto.getPartner_megnevezes();
            
            System.out.print("Kérem az új adószámot (aktualis: " + partnerViewDto.getPartner_adoszam() + "): ");
            String aSzam = this.scanner.nextLine();
            if(aSzam.isEmpty()) aSzam = partnerViewDto.getPartner_adoszam();
            
            System.out.print("A modositott EU-s adoszamat (aktualis: " + partnerViewDto.getPartner_kozossegi_asz() + "): ");
            String kozossegiAsz = this.scanner.nextLine();
            if(kozossegiAsz.isEmpty()) kozossegiAsz = partnerViewDto.getPartner_adoszam();
            
            System.out.print("Az uj egyeb informaciokat (aktualis: " + partnerViewDto.getPartner_egyeb_info() + "): ");
            String egyeb = this.scanner.nextLine();
            if(egyeb.isEmpty()) egyeb = partnerViewDto.getPartner_egyeb_info();
            
            System.out.println("Modositsd a partner-statuszt ezekbol valasztva - vagy 0-t (aktualis: " + PartnerStatus.values()[partnerViewDto.getPartner_vevo_szallito()] + "): ");
            System.out.println("1. VEVO");
            System.out.println("2. SZALLITO");
            System.out.println("3. MINDKETTO");
            int vevoSzallitoChoice = this.scanner.nextInt();
            this.scanner.nextLine();
            PartnerStatus vevoSzallito = null;
            if(vevoSzallitoChoice == 0) vevoSzallito = PartnerStatus.values()[partnerViewDto.getPartner_vevo_szallito()];
            else vevoSzallito = PartnerStatus.values()[vevoSzallitoChoice - 1];

            System.out.println("Modositsd a fizetesi modot ezekből valasztva - vagy 0-t (aktualis: " + FizetesiModok.values()[partnerViewDto.getPartner_fizmod()] + "): ");
            System.out.println("1. CASH");
            System.out.println("2. TRANSFER");
            System.out.println("3. OTHER");
            int fizmodChoice = this.scanner.nextInt();
            this.scanner.nextLine(); 
            
            FizetesiModok fizmod = null;
            if(fizmodChoice == 0) fizmod = FizetesiModok.values()[partnerViewDto.getPartner_fizmod()];
            else fizmod = FizetesiModok.values()[fizmodChoice - 1];
            
            System.out.print("Kerlek adj meg egy letezo cimkodot vagy 0-t egy uj cimadat felvetelehez (aktualis: " + partnerViewDto.getCimadat_id() + "): ");
            String cimadatId = this.scanner.nextLine();
            if(cimadatId.equals("0")) cimadatId = null;
            
            String utca = "";
            String kozterulet = "";
            String hazszam = "";
            String irszam = "";
            String helyseg = "";
            Long cimId = null;
            
            if(cimadatId == null){
                System.out.print("Kerem add meg a kozterulet (új) nevet (aktualis: " + partnerViewDto.getCimadat_utca() + "): ");
                utca = this.scanner.nextLine();
                System.out.print("Add meg a kozterulet modositott tipusat  (aktualis: " + partnerViewDto.getCimadat_kozterulet() + "): ");
                kozterulet = this.scanner.nextLine();
                System.out.print("Add meg az uj hazszamot  (aktualis: " + partnerViewDto.getCimadat_hazszam() + "): ");
                hazszam = this.scanner.nextLine();
                System.out.print("Kerem az (uj) iranyitoszamot - ugyeljen annak helyessegere! (aktualis: " + partnerViewDto.getHelyseg_irszam() + "): ");
                irszam = this.scanner.nextLine();
                System.out.print("Kerem a modositott települes nevet  (aktualis: " + partnerViewDto.getHelyseg_helyseg() + "): ");
                helyseg = this.scanner.nextLine();
            }else{
                // lekérdezzük a "megadott" cim Id - Cimadat(komplex)  értékét és kiírjuk amit választott, 
                // illetve értékként is visszapakoljuk a komplex-partner dto -nkba!:
                // lekérdezés:
                ResponseEntity<CimadatDto> responseEntity = this.restTemplate.getForEntity(BASE_CIMADAT_URL + "/" + cimadatId, CimadatDto.class);         
                CimadatDto cimadatDto = responseEntity.getBody();
                // kiíratás:
                cimadatDto.toConsol("A valasztott uj cimadatok:");
                // visszapaoklás (fontos: a 301. sor körül String -ként kértük be a konzolról az id -t, de a Cimadat.id -je az Long, ezért nem azt írjuk vissza...)!
                cimId = cimadatDto.getId();
                utca = cimadatDto.getUtca();
                kozterulet = cimadatDto.getKozterulet();
                hazszam = cimadatDto.getHazszam();
                irszam = cimadatDto.getTelepules().getIrszam();
                helyseg = cimadatDto.getTelepules().getHelyseg();
            }
            
            // betöltjük a Dto -ba (ahogy a thymeleaf sablon is teszi):
            PartnerCimHelysegDto updatedPartner = new PartnerCimHelysegDto();
            updatedPartner.setPartner_id(id);
            updatedPartner.setPartner_megnevezes(megnevezese);
            updatedPartner.setPartner_adoszam(aSzam);
            updatedPartner.setPartner_kozossegi_asz(kozossegiAsz);
            updatedPartner.setPartner_vevo_szallito(vevoSzallito.getIntValue());
            updatedPartner.setPartner_fizmod(fizmod.getFizmod());        
            updatedPartner.setPartner_egyeb_info(egyeb);
            updatedPartner.setCimadat_id(cimId);
            updatedPartner.setCimadat_id(cimId);
            updatedPartner.setCimadat_utca(utca);
            updatedPartner.setCimadat_kozterulet(kozterulet);
            updatedPartner.setCimadat_hazszam(hazszam);
            updatedPartner.setHelyseg_irszam(irszam);
            updatedPartner.setHelyseg_helyseg(helyseg);
           
            // Elkészítjük a frissített partner objektumot:
            // A metódus elején már deklarált változókat "feljavítjuk", itt a "header" -t állítjuk be:
            headers.setContentType(MediaType.APPLICATION_JSON);
            // Létrehozzuk a kérést,
            // és itt adjuk meg a kérésnek a "body"-t ill. az előbb paraméterezett "headert" is:
            HttpEntity<PartnerCimHelysegDto> updateEntity = new HttpEntity<>(updatedPartner, headers);
            // ez egy, a REST elvi alapokban fontos konvenció a PUT és a DELETE metódusohívásoknál (meg kell adni a .../{id} -t is): 
            url = BASE_PARTNER_URL + "/" + id;
            // és elküldjük a PUT igét (parancsot):
            response = this.restTemplate.exchange(url, HttpMethod.PUT, updateEntity, PartnerCimHelysegDto.class);        
            // this.restTemplate.put(BASE_URL, updateEntity);
            // Visszakérdezzük az adatokat, 
            // de ehhez "újra alkotjuk" a kérést, (mert ez egy GET ennek nem lesz body-ja!):
            updateEntity = new HttpEntity<>(headers);
            response = this.restTemplate.exchange(url, HttpMethod.GET, updateEntity, PartnerCimHelysegDto.class);
            // megjött a válasz:
            response.getBody().toConsol("Statuszkod=" + response.getStatusCode() + " - Partner modositas: " );
        } else {
            System.out.println("Partner nem talalhato.");
        }
    }

    private void deletePartner() {
        System.out.print("Kerem a torlendo partner Id -et : ");
        Long id = this.scanner.nextLong();
        this.scanner.nextLine(); 
        
        String url = BASE_PARTNER_URL + "/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(this.username, this.password);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = this.restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            System.out.println("Partner torolve.");
        } else {
            System.out.println("Hiba tortent a partner torlese soran.");
        }        
    }
}
