/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Beans/Bean.java to edit this template
 */
package hu.anzek.backend.invoce.service;


import hu.anzek.backend.invoce.datalayer.dto.MegrendelesDto;
import hu.anzek.backend.invoce.datalayer.mapper.MegrendelesMapper;
import hu.anzek.backend.invoce.datalayer.model.Invoice;
import hu.anzek.backend.invoce.datalayer.model.InvoiceItem;
import hu.anzek.backend.invoce.datalayer.model.Megrendeles;
import hu.anzek.backend.invoce.datalayer.model.MegrendelesTetel;
import hu.anzek.backend.invoce.datalayer.repository.InvoiceRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author User
 */
@Service
public class InvoiceService {
    
    private final MegrendelesService megrendelesService;
    private final InvoiceRepository invoiceRepository;
    private final MegrendelesMapper orderMapper; 
    //private final StockService stockService;
    
    @Autowired
    public InvoiceService(MegrendelesService megrendelesService,
                          InvoiceRepository invoiceRepository,
                              MegrendelesMapper orderMapper) {
        this.megrendelesService = megrendelesService;
        this.invoiceRepository = invoiceRepository;
        this.orderMapper = orderMapper;
        //this.stockService = stockService;
    }

    /** 
     * . Egy fetch = LAZY típusú lekérdezés entitás-gráf függőségének beolvasási "kényszerítése"
     * Amikor simán csak hivatkozunk (bárhogyan is) az adott adatmezőre, metörténik a kényszerítés!
     * És itt még egy kontextusban kezeljük a két lekérdezést!
     * @param id
     * @return 
     */
    @Transactional
    public Invoice getInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        
        // Ezzel kényszerítjük a perzisztencia kontextust, hogy a proxi-tételeket is hozza be, vagyis
        // betöltjük az "items" listamező tartalmát:
        invoice.toConsol("szamla:");
        invoice.getItems().stream().forEach(e -> {
                e.setAfa_kulcs(this.round2(e.getAfa_kulcs()));
                e.setEgyseg_ar(this.round2(e.getEgyseg_ar()));  
        });
        return invoice;
    }

    /**
     * . Ez itt létrehoz egy komplett számlát a paraméterben érkező megrendelés példányból
     * @param megrendeles a megrendelés példány
     * @return az elkészült számla példány
     */
    @Transactional
    @SuppressWarnings("null")
    private Invoice createInvoice(Megrendeles megrendeles) {     
        // legyártjuk a számlát:
        Invoice invoice = this.invoice(megrendeles);
        if(invoice == null){
            return null;
        }
        invoice = this.invoiceRepository.save(invoice);
        // "S" -el kezd, és előnullázva 10 karakerben az id majd "/" jel és az évszám:
        invoice.setSzamlaszam(String.format("S%010d", invoice.getId()) + "/" + invoice.getKeszult().getYear() );
        // de ez nem túl hatékony módszer mert ehhez most sajna, újra mentenünk kell...
        invoice = this.invoiceRepository.save(invoice);
        // azután itt jön még a megrendelés lezárása is:
        megrendeles.setSzamlaszam(invoice.getSzamlaszam());
        megrendeles.setReadyForInvoicing();
        megrendeles = this.megrendelesService.updateMegrendeles(megrendeles);
        return invoice;
    }    

    /**
     * . Visszaad egy listát az épp számláott megrendelésekről!
     * Előkézítés:
     * - megvizsgáltuk, megvannak-e a tételekhez szükséges készletmennyiségek?
     * És azt találtuk igen, megpróbáltuk zárolni a rekordot (más felhasználók elől)
     * - és ha ez is sikerült, 
     * - az "isReadyForInvoicing()" -true -ra kell állítanunk!
     * Végezetül, ha mindez megvolt: leszámlázzuk
     * @return az épp számlázható megrendelések listája
     */
    public List<MegrendelesDto> automataKeszrejelento() {
        List<Megrendeles> lista = new ArrayList<>();
        List<Megrendeles> szamlazva = new ArrayList<>();
        lista = this.megrendelesService.getAllBillableMegrendeless();
        if(lista != null){
            for(Megrendeles megrendeles : lista){
                boolean szamlazhato = true;
                // címke egy loop utasításhoz:
                kiugrasiSzint:
                // a rekord lock-olása:
                // A feldolgozáshoz zároljuk a megrendelési rekordot. 
                // Minimálisan, az ellenőrzés idejére, 
                // - viszont, ha minden rendben van "nála", akkor csak a számlakászítő futása után oldjuk fel!                
                if(this.megrendelesService.updateZarolas(megrendeles, true)){
                    // most már nyugodtan dolgozhatunk vele,és legelsőként is kiolvassuk a tételeit:
                    megrendeles.getTetelek().size();
                    List<MegrendelesTetel> items = megrendeles.getTetelek();
                    // ha előzőekben már lezárt (manuálisan, vagy gépi úton maradt így), akkor azt elfogadva,
                    // csak azt nézzük meg, van-e most legalább egy olyan tétele, ami készletről kifuttatható 
                    // maga a rendelés lezárt-e (számlázható-e?)
                    if(megrendeles.isLezarva()){
                        szamlazhato = false;
                        for(MegrendelesTetel item : items){
                            // egyenként megnézzük van-e valamelyiknek kellő fedezete:
                            if(item.getMennyiseg() <= item.getInv_cikk().getKeszleten_van()){
                                // van fedezete:
                                szamlazhato = true;
                                // elhagyjuk a ciklust:
                                break kiugrasiSzint;
                            }
                        }        
                    }else{
                        for(MegrendelesTetel item : items){
                            // egyenként megnézzük van-e kellő fedezete:
                            if(item.getMennyiseg() > item.getInv_cikk().getKeszleten_van()){
                                // sajnos nincs fedezete:
                                szamlazhato = false;
                                // elhagyjuk a ciklust:
                                break kiugrasiSzint;
                            }
                        }
                    }
                }
                if(szamlazhato){ 
                    // a zárolást (a rekordfoglalást) meghagyva, le is zárjuk a rendelést:        
                    // adatbázisba majd a számlakészítő menti el, 
                    // (már ha sikerült neki a számláakészítés):
                    megrendeles.setLezarva(true); 
                    // leszámlázzuk:
                    Invoice invoice = this.createInvoice(megrendeles);
                    if(invoice != null){
                        invoice.toConsol("uj szamla keszult: ");
                        megrendeles.setSzamlaszam(invoice.getSzamlaszam());                    
                        szamlazva.add(megrendeles); 
                    }else{
                        System.out.println("A Rsz(" + megrendeles.getId() + ") alapjan nem sikerult szamlat kesziteni!");
                    }
                }else{
                    // feloldjuk a zárolást, elengedjük a rekordot és nem számlázzuk:
                    megrendeles.setZarolva(false);
                    this.megrendelesService.updateMegrendeles(megrendeles);
                } 
            }
            return this.orderMapper.toDtos(szamlazva);
        }else{
            return null;
        }
    }

    /**
     * Kézi készrejelentő.
     * Csak akkor jelenti készre, ha legalább egy megrendelési eleme kiszámlázható!
     * @param id a megrendelés száma, amelyet kényszeítenünk kell
     * @return visszadaja, hoyg sikerült, vagy sem?
     */
    public boolean manualisKeszrejelento(Long id) {
        Megrendeles megrendeles = this.megrendelesService.getMegrendeles(id);
        if(megrendeles == null){
            return false;
        }
        boolean szamlazhato = true;
        megrendeles.getTetelek().size();
        List<MegrendelesTetel> items = megrendeles.getTetelek();
        for(MegrendelesTetel item : items){
            // egyenként megnézzük van-e kellő fedezete:
            if(item.getMennyiseg() > item.getInv_cikk().getKeszleten_van()){
                // sajnos nincs fedezete:
                szamlazhato = false;
                // elhagyjuk a ciklust:
                break;
            }
        }
        if(szamlazhato){     
            // zároljuk:
            if(this.megrendelesService.updateZarolas(megrendeles,true)){
                // ha sikerült a zárolás leszámlázzuk:
                Invoice invoice = this.createInvoice(megrendeles);
                if(invoice != null){
                    invoice.toConsol("uj szamla keszult: ");
                    megrendeles.setSzamlaszam(invoice.getSzamlaszam());                    
                    return true;
                }
            }
        }    
        return false;
    }
    
    /**
     * Elkészítjük egy számla áfaösszesítőjét
     * @param invoiceId a számla azonosítója
     * @return 
     */
    public Map<Double, Double> getAfaOsszesito(Long invoiceId) {
        Map<Double, Double> afaOsszesito = new HashMap<>();
        return afaOsszesito;
    }    
    
    /**
     * duplapontos számértékek két tizedesre kerekítése
     * @param db a duplapontos kerekitetlen érték
     * @return a kerekített érték
     */
    private double round2(Double db){
        BigDecimal bdc = BigDecimal.valueOf(db).setScale(2, RoundingMode.HALF_UP);
        return bdc.doubleValue();
    }
    
    /**
     * Egy kis csalafintasággal a számla konstruktora maga a számlakészítő üzleti logika is
     * vitatható, hogy ez így megállja-e a helyét (itt van -e a helye?) de ami miatt itt van kő egyszerű magyarázat:
     * A konstruktor feladata, hogy létrehoz egy entitás példányt
     * csakhogy itt a létrehozás folyamatában van elrejtve a létjogosultsága is 
     * (vagyis a létrehozás vizsgálja, hogy létrehozzható-e egyáltalán?)
     * @param m a megrendelés példány
     */
    private Invoice invoice(Megrendeles m){
        Invoice invoice = new Invoice();
        m.getTetelek().size();
        List<MegrendelesTetel> mtList = m.getTetelek();
        List<InvoiceItem> items = new ArrayList<>();
        boolean lehetSzamla = false;
        Double sum_afak = 0.00;
        Double sum_alapok = 0.00;
        for(MegrendelesTetel tetel : mtList){  
            // csak ha van a mgrendelt mennyiségből elegendő:
            if(tetel.getMennyiseg() <= tetel.getInv_cikk().getKeszleten_van()) {
                InvoiceItem item = new InvoiceItem( invoice, 
                                                    tetel.getId(), 
                                                   tetel.getInv_cikk().getMegnevezes(), 
                                                    tetel.getInv_cikk().getMennyegys(), 
                                                    tetel.getInv_cikk().getAfa_kulcs(), 
                                                    tetel.getInv_cikk().getEgyseg_ar(), 
                                                    tetel.getMennyiseg(),                         
                                                    "Ebbol adtuk ki: " 
                                                    + String.valueOf(tetel.getInv_cikk().getKeszleten_van()) + "\n " 
                                                    + "kiallitva : " + LocalDateTime.now());
                sum_afak += (item.getAfa_kulcs() != 0.00 ? item.getAfa_kulcs() / 100 : 0.00) * (item.getEgyseg_ar() * item.getMennyiseg());
                sum_alapok += item.getEgyseg_ar() * item.getMennyiseg();
                // hozzáadjuk a számla kiszámlázott tételeihez:
                items.add(item);
                lehetSzamla = true;
            }
        }
        if(lehetSzamla){
            invoice.setMegrendeles( m );
            invoice.setKeszult(LocalDate.now());
            invoice.setMegjegyzes(m.getMegjegyzes());
            invoice.setSzallitas_napja(m.getMikorra());
            invoice.setSzallito_megnevezes(m.getSzallito().getMegnevezes());
            invoice.setSzallito_adoszam(m.getSzallito().getAdoszam());
            invoice.setSzallito_kozossegi_asz (m.getSzallito().getKozossegi_asz());
            invoice.setSzallito_irszam (m.getSzallito().getPartner_cim().getTelepules().getIrszam());
            invoice.setSzallito_telepules (m.getSzallito().getPartner_cim().getTelepules().getHelyseg());
            invoice.setSzallito_utca (m.getSzallito().getPartner_cim().getUtca());
            invoice.setSzallito_kozterulet (m.getSzallito().getPartner_cim().getKozterulet());
            invoice.setSzallito_hazszam (m.getSzallito().getPartner_cim().getHazszam());
            invoice.setVevo_megnevezes (m.getVevo().getMegnevezes());
            invoice.setVevo_adoszam (m.getVevo().getAdoszam());
            invoice.setVevo_kozossegi_asz (m.getVevo().getKozossegi_asz());
            invoice.setVevo_irszam (m.getVevo().getPartner_cim().getTelepules().getIrszam());
            invoice.setVevo_telepules (m.getVevo().getPartner_cim().getTelepules().getHelyseg());
            invoice.setVevo_utca (m.getVevo().getPartner_cim().getUtca());
            invoice.setVevo_kozterulet (m.getVevo().getPartner_cim().getKozterulet());
            invoice.setVevo_hazszam (m.getVevo().getPartner_cim().getHazszam()); 
            invoice.setItems(items);
            invoice.setKeszult(LocalDate.now());
            invoice.setElszamolasi_valuta("HUF");
        }        
        if(lehetSzamla) return invoice; else return null;
    }
}
