/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package hu.anzek.backend.invoce.service;


import hu.anzek.backend.invoce.datalayer.model.InvCikk;
import hu.anzek.backend.invoce.datalayer.model.Stock;
import hu.anzek.backend.invoce.service.enumeralt.Mozgasok;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;


/**
 * .
 * A mockolás lényege:<br> 
 * A Mockito tesztekben egy adott osztályt vagy interfészt helyettesítünk egy olyan objektummal,<br> 
 * amely viselkedését mi szabályozzuk<br> 
 * Így könnyen tesztelhetővé válnak azok a funkciók, amelyek függnek más osztályoktól vagy szolgáltatásoktól.<br> 
 * A mock objektumok viselkedését a tesztekben definiáljuk és azok csak a tesztelés során élnek<br>
 * @author User
 */
@ExtendWith(MockitoExtension.class)
public class SzummazasMockitoServiceTest {
    
    // Ez egy injektor!
    // Tehát a "@InjectMocks" annotációval jelöljük azt az osztályt, amelyet tesztelni szeretnénk!
    // (most épp a StockService osztályt...)
    // Ez az annotáció a tesztosztályon belül megkeresi azokat a további osztályokat vagy interfészeket, 
    //    amelyeket injektálni kell az osztályba (ami általában a konstruktoron keresztül történik):
    @InjectMocks
    public StockService service;
 
    // Ezzel, a "@Mock" annotációval pedig azokat az osztályokat vagy interfészeket jelöljük, 
    //    amelyeket mockolni (kamuzni) szeretnénk:
    // ! de ez nem injektál - ez kamuzik!
    @Mock
    private InvCikkService cikkService;
            
    public SzummazasMockitoServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testOsszMennyisegFrissitese() {
        // 1,
        // Készítsünk teszt adatokat!
        // Jelen esetben így szoktuk mondani: "mockoljuk a InvCikkService"-t (nem a StockService-t, hanem az InvCikkService-t): 
        // Tehát a "StockService" teszthez úgy definiáljuk a "cikkservice" tagváltozót, hogy:
        //     a cikkservice.getById(id) híváskor ezt itt, azaz az alábbi "cikk" értékét adja vissza: 
        InvCikk cikk = new InvCikk(1L, "megnevezes", "mennyegys", 27.0, 127.56, 5);
        // 2, 
        // most csináljunk input adatokat az "OsszMennyisegFrissitese()" metódusnak: 
        // 2/a, a cikk.keszleten_van(+5) - tehát előzőleg ez a cikk, 5 bevételezéssel lett letárolva:
        Stock original = new Stock(1L, cikk, LocalDateTime.of(2024,4,1,9,10,1,0), Mozgasok.BEVET, 5, 0);
        // 2/b, most hozzáadunk (bevételezünk) újabb 15 -öt:
        Stock raktarMozgas = new Stock(2L, cikk, LocalDateTime.of(2024,5,2,10,22,0,0), Mozgasok.BEVET, 15, 0);
        // 3,
        // Itt "meg-mockoljuk a cikkService.getById()" metódust, 
        // - - mivel ez egy összetettebb, ún. "integrációs egység" (itt pl. adatbázis-orm-enttás kapcsolat) - ezért mockoljuk.
        // Az alábbi kifejezés azt mondja meg a jupiter JUnit tesztnek: 
        // - ha a tesztfutása során a "cikkService.getById()" metódust "1L" paraméterrel meghívnák, 
        // - - akkor térjen vissza a fent létrehozott "cikk" objektummal 
        // De valójában nem csinál semmit sem, olyannyira nem, hogy még csak nem is futtatja ezt a metódust!
        when(this.cikkService.getById(1L)).thenReturn(cikk);
        // 4, 
        // Most már meghívhatjuk a tesztelni kívánt metódust, vagyis amit tesztelünk:
        this.service.osszMennyisegFrissitese(original, raktarMozgas);
        // 5,
        // Az eddig ismertetett módon ellenőrizzük, hogy a cikk készleten van mennyisége megfelelően frissült-e?:
        assertEquals(15, cikk.getKeszleten_van());
    }
}

