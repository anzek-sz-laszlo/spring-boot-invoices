/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package hu.anzek.backend.invoce.service;


import hu.anzek.backend.invoce.datalayer.model.InvCikk;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * InvCikkService integrációs tesztje<br>
 * @author User
 */
@SpringBootTest
public class InvCikkServiceIT {
    
    private final InvCikkService cikkService;
    public InvCikkServiceIT(InvCikkService cikkService) {
        this.cikkService = cikkService;
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

    /**
     * Test of getAll method, of class InvCikkService.
     */
    @Test
    public void testGetAll() {
        System.out.println("getAll");
        InvCikkService instance = null;
        List<InvCikk> expResult = null;
        List<InvCikk> result = instance.getAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getById method, of class InvCikkService.
     */
    @Test
    public void testGetById() {
        System.out.println("getById");
        Long id = null;
        InvCikkService instance = null;
        InvCikk expResult = null;
        InvCikk result = instance.getById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of create method, of class InvCikkService.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        InvCikk entity = null;
        InvCikkService instance = null;
        InvCikk expResult = null;
        InvCikk result = instance.create(entity);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class InvCikkService.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        InvCikk entity = null;
        InvCikkService instance = null;
        InvCikk expResult = null;
        InvCikk result = instance.update(entity);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class InvCikkService.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        Long id = null;
        InvCikkService instance = null;
        boolean expResult = false;
        boolean result = instance.delete(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printFullList method, of class InvCikkService.
     */
    @Test
    public void testPrintFullList() {
        System.out.println("printFullList");
        InvCikkService instance = null;
        List<String> expResult = null;
        List<String> result = instance.printFullList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printEntityDetails method, of class InvCikkService.
     */
    @Test
    public void testPrintEntityDetails() {
        System.out.println("printEntityDetails");
        Long id = null;
        InvCikkService instance = null;
        List<String> expResult = null;
        List<String> result = instance.printEntityDetails(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Ezt írtuk mi:<br>
     */
    @Test
    public void testGetCikkById() {
        // Adatbázisból lekérünk egy cikket az azonosító alapján
        InvCikk cikk = cikkService.getById(1L);

        // Ellenőrizzük, hogy a cikk nem null és a megfelelő azonosítóval rendelkezik
        assertNotNull(cikk);
        assertEquals(1L, cikk.getId());
    }    
}
