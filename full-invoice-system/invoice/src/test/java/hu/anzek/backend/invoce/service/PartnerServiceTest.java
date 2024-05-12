/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package hu.anzek.backend.invoce.service;


import hu.anzek.backend.invoce.datalayer.mapper.PartnerCimMapper;
import hu.anzek.backend.invoce.datalayer.model.Cimadat;
import hu.anzek.backend.invoce.datalayer.model.HelysegnevTar;
import hu.anzek.backend.invoce.datalayer.model.Partner;
import hu.anzek.backend.invoce.datalayer.repository.CimadatRepository;
import hu.anzek.backend.invoce.datalayer.repository.PartnerRepository;
import hu.anzek.backend.invoce.service.enumeralt.FizetesiModok;
import hu.anzek.backend.invoce.service.enumeralt.PartnerStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
/**
 *
 * @author User
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PartnerServiceTest {
    // a tesztosztályunk függőséegei:
    @Mock
    private PartnerRepository partnerRepository;
    
    @Mock
    private CimadatRepository cimadatRepository;
    
    @Mock
    private PartnerCimMapper cimMapper; 
        
    @InjectMocks
    private PartnerService partnerService;
            
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        // Ez, ( vagyis ha itt mockolunk) azt jelenti, hogy minden egyes teszteset előtt 
        // pl a "cimadatRepository" objektum mockolt példányát létre hozzuk, 
        // ezáltal biztosítjuk a tesztek izoláltságát és konzisztenciáját.
        // - de bizonyos esetekben nem célravezető (általam nem magyarázható) problémákat okozhat! 
        // Ahogy teszi ezt jelen esetben is...
        //
        // this.cimadatRepository = mock(CimadatRepository.class);
        // this.partnerRepository = mock(PartnerRepository.class);
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of create method, of class PartnerService.
     */
    @Test
    public void testCreate() {
        System.out.println("Create(partner)");
        Cimadat cim = new Cimadat(1L, new HelysegnevTar("3335", "Test-Bükkszék"), "Egri", "út", "77");
        Partner entity = new Partner();
        entity.setId(777L);
        entity.setMegnevezes("Test-Anzek Informatika Kft");
        entity.setAdoszam("Test-25481558-2-09");
        entity.setKozossegi_asz("Test-HU25481558");
        entity.setVevo_szallito(PartnerStatus.VEVEO_CUSTOMER);
        entity.setFizmod(FizetesiModok.EGYEB_OTHER);
        entity.setPartner_cim(cim);

        // Ez itt a MOCKOLÁS:
        // így értsük 
        // - valójában épp úgy értsük, ahgy olvassuk:
        // amikor( cimadatRepository.save( itt_valamilyen(Cimadat.osztály_hivatkozás_történik) )).akkor_TérjVissza( a "cim" tartalommal);
        when(cimadatRepository.save(any(Cimadat.class))).thenReturn(cim);
        // ez itt dettó!
        when(partnerRepository.save(any(Partner.class))).thenReturn(entity);
        System.out.println(EkezetEltavolito.uniform("A TESZT KVÁZI_ÚJ PARTNER ADATAI: \n" + entity.toString()));                       
        // ment és kiír:
        Partner result = this.partnerService.partnerGrafMentes(true,entity);
        System.out.println(EkezetEltavolito.uniform("A \"LERÖGZÍTET\" ÉS VISSZAOLVASOTT PARTNER ADATAI: \n" + entity.toString()));   
        cim = this.cimMapper.setCimadatFromPartner(entity);
        System.out.println(EkezetEltavolito.uniform("A PARTNERBŐL KIEMELT CÍMADATOK: \n" + cim.toString()));
        // ellenőrzések:
        // Vajon azt kaptuk-e vissza, amit elküdtünk?
        assertEquals(entity, result);
        // Az alább a "mock objektumok"- hoz tartozó "save" metódusok futattásának a számát ellenőrizzük. 
        // A "times(1)" jelentése: 1x várjuk el a "save" metódus meghívását a tesztesetben. 
        // vajon a PartnerService osztályban a cimService -el végzett cím-mentési művelet pontosan egyszer történik-e meg?        
        // Ha többször hívódna meg a save metódus a cimadatRepository mock objektumon, akkor a teszteset megbukna:
        verify(cimadatRepository, times(1)).save(any(Cimadat.class));
        verify(partnerRepository, times(1)).save(any(Partner.class));    
    }
}
