/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package hu.anzek.backend.invoce.service;


import hu.anzek.backend.invoce.datalayer.model.Megrendeles;
import hu.anzek.backend.invoce.datalayer.repository.MegrendelesRepo;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author User
 */
@Service
public class MegrendelesService {
    
    private final MegrendelesRepo megrendelesRepo;
    
    @Autowired
    public MegrendelesService(MegrendelesRepo megrendelesRepo) {
        this.megrendelesRepo = megrendelesRepo;        
    }
     
    /** 
     * . Egy fetch = LAZY típusú lekérdezés entitás-gráf függőségének beolvasási "kényszerítése"
     * Amikor simán csak hivatkozunk (bárhogyan is) az adott adatmezőre, metörténik a kényszerítés!
     * És itt még egy kontextusban kezeljük a két lekérdezést!
     * @param id
     * @return 
     */
    @Transactional
    public Megrendeles getMegrendeles(Long id) {
        Megrendeles megrendeles = this.megrendelesRepo.findById(id).orElse(null);
        // Ezzel kényszerítjük a perzisztencia kontextust, hogy a proxi-tételeket is hozza be, (ha null értéke lenne) vagyis
        // betöltjük az "items" listamező tartalmát:
        megrendeles.getReadOnlyTetelek().size(); 
        return megrendeles;
    }

    public List<Megrendeles> getAllMegrendeless(){
        List<Megrendeles> lista = this.megrendelesRepo.findAll();
        lista.stream().forEach(e -> e.getReadOnlyTetelek());
        return lista;
    }

    /**
     * új bevitel
     * @param megrendeles a megrendelés entitás-példány, amely itt még nem rendelkezhet ID -val!
     * @return a sikeres, kész, mentett és ID -vel rendelkező példány, vagy más esetben null érték!
     */
    @Transactional
    public Megrendeles createMegrendeles(Megrendeles megrendeles) {
        if((megrendeles == null) || (megrendeles.getId() != null)) {
            // nem lehet üres a példány, 
            // viszont éppenhogy null értékű kell legyen az ID -je!
            return null;
        }         
        return this.megrendelesRepo.save(megrendeles);
    }
    
    /**
     * móódosítás
     * @param megrendeles a megrendelés példány
     * @return a módosítás után visszakérdezett példánya, vagy hibák esetén null
     */
    @Transactional
    public Megrendeles updateMegrendeles(Megrendeles megrendeles) {
        if((megrendeles == null) || (megrendeles.getId() == null)) {
            return null;
        }
        Megrendeles mrend = this.megrendelesRepo.findById(megrendeles.getId()).orElse(null);
        if( mrend == null){
            return null;
        }        
        if( ! this.megrendelesRepo.findById(megrendeles.getId()).get().isZarolva()){
            return this.megrendelesRepo.save(megrendeles);
        }else{
            return null;
        }
    }

    /**
     * . Visszaad egy listát a megrendelési fő adatok szerint épp számlázható megrendelésekről!
     * Ezeket egyenként meg kell mahd vizsgálni, vajon megvannak-e a tételekhez szükséges készletmennyiségek?
     * - ha igen, zarolnunk kell a rekordot 
     * - - ha ez is sikerült, az "isReadyForInvoicing()" -true -ra kell állítanunk!
     * @return a számlázásra alkalmas megrendelések listája, a megrendelés repóból
     */
    public List<Megrendeles> getAllBillableMegrendeless() {
        return this.megrendelesRepo.findAllBillable(LocalDate.now()).orElse(null);
    }
    
    /**
     * automatikus megrendelés-rekord lockolás optimista eljárással
     * @return a zárolásra verziószámmal megjelölt rekordok listája
     */
    public List<Megrendeles> getAllBillableOptimist() {
        return this.megrendelesRepo.findAllBillableOptimist(LocalDate.now()).orElse(null);
    }
   
    /**
     * automatikus megrendelés-rekord lockolás pesszimista eljárással
     * @return 
     */
    public List<Megrendeles> getAllBillablePessimist() {
        return this.megrendelesRepo.findAllBillablePessimist(LocalDate.now()).orElse(null);
    }
   
    /**
     * .
     * Megrendelési példány zárolása feldolgozáshoz, 
     * vagy feloldása, de ez utóbbit nem használjuk, mert ha számlázott lesz, akkor már úgysem vehetjük újra elő! 
     * - bár lehetne létjogosultsága: pl ha valami miatt sikertelen lett volna a műveletsor
     * persze ez egy tökéletes megoldás..., 
     * - de ahol nincs több száz párhuzamos kérés ugyan arra az esetre, ott megfelkelően működhet.
     * @param megrendeles a kiválasztott példány
     * @param zaroljuk true/false
     * @return sikerült, vagy már más lefoglalta
     */
    @Transactional
    public boolean updateZarolas(Megrendeles megrendeles, boolean zaroljuk) {
        if((megrendeles == null) || (megrendeles.getId() == null)) {
            return false;
        }
        Megrendeles mrend = this.megrendelesRepo.findById(megrendeles.getId()).orElse(null);
        if( mrend == null){
            return false;
        }
        if( ! zaroljuk){
            // feloldás
            megrendeles.setZarolva(false);
            this.megrendelesRepo.save(megrendeles);
            return true;
        }else{
            // zárolás:
            // megnézzük szabad-e még a szóbanforgó rekordunk:
            if( ! mrend.isZarolva()){
                // ha igen, akkor zároljuk:
                megrendeles.setZarolva(true);
                this.megrendelesRepo.save(megrendeles);
                return true;
            }        
            // ha már nem, akkor false -al térünk vissza
            return false;
        }
    }

    public Megrendeles findById(Long id){
        return this.megrendelesRepo.findById(id).orElse(null);
    }

    public void delete(Megrendeles megrendeles) {
        this.megrendelesRepo.delete(megrendeles);
    }
}
