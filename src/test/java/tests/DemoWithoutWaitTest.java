package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.*;

public class DemoWithoutWaitTest extends BaseTest {
    
    @Test
    public void test1_AsyncContentFails() {
        openTestPage();
        pauseForDemo();
        
        driver.findElement(By.id("btnLoadContent")).click();
        pauseForDemo();
        
        
        // Esta lÃ­nea FALLA con NoSuchElementException (el elemento no existe)
        driver.findElement(By.id("btnProcessData")).click();
        
        fail("Este cÃ³digo nunca se ejecuta (el test falla antes)");
    }
    
    @Test
    public void test2_DisabledButtonFails() {
        openTestPage();
        pauseForDemo();
        
        driver.findElement(By.id("username")).sendKeys("testuser");
        pauseForDemo();
        
        
        // Esta lÃ­nea FALLA con ElementNotInteractableException (botÃ³n deshabilitado)
        driver.findElement(By.id("btnSubmit")).click();
        
        fail("Este cÃ³digo nunca se ejecuta (el test falla antes)");
    }
    
    @Test
    public void test3_DynamicTextFails() {
        openTestPage();
        pauseForDemo();
        
        driver.findElement(By.id("btnUpdateStatus")).click();
        pauseForDemo();
        
        String statusText = driver.findElement(By.id("statusText")).getText();
        
        // Esta assertion FALLA (el texto esperado "Procesando datos" aÃºn no aparece)
        assertTrue(statusText.contains("Procesando datos"), 
            "El texto deberÃ­a contener 'Procesando datos' pero es: '" + statusText + "'");
    }

    @Test
    public void test4_OverlayBlocksFails() {
        openTestPage();
        pauseForDemo();
        
        driver.findElement(By.id("btnShowAlert")).click();
        pauseForDemo();
        
        
        // Esta lÃ­nea FALLA con ElementClickInterceptedException (overlay bloquea el click)
        driver.findElement(By.id("btnAfterAlert")).click();
        
        fail("Este cÃ³digo nunca se ejecuta (el test falla antes)");
    }
}
