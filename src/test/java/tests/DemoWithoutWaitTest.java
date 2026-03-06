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
        
        System.out.println("→ Click en 'Cargar Contenido Remoto'");
        driver.findElement(By.id("btnLoadContent")).click();
        pauseForDemo();
        
        System.out.println("→ Buscando 'btnProcessData' sin esperar...");
        System.out.println("⚠️  ERROR: El botón no existe aún (tarda 3 segundos)");
        
        // Esta línea FALLA con NoSuchElementException (el elemento no existe)
        driver.findElement(By.id("btnProcessData")).click();
        
        fail("Este código nunca se ejecuta (el test falla antes)");
    }
    
    @Test
    public void test2_DisabledButtonFails() {
        openTestPage();
        pauseForDemo();
        
        System.out.println("→ Escribiendo en campo 'username'");
        driver.findElement(By.id("username")).sendKeys("testuser");
        pauseForDemo();
        
        System.out.println("→ Intentando click en botón deshabilitado...");
        System.out.println("⚠️  ERROR: Botón visible pero disabled (se habilita en 2s)");
        
        // Esta línea FALLA con ElementNotInteractableException (botón deshabilitado)
        driver.findElement(By.id("btnSubmit")).click();
        
        fail("Este código nunca se ejecuta (el test falla antes)");
    }
    
    @Test
    public void test3_DynamicTextFails() {
        openTestPage();
        pauseForDemo();
        
        System.out.println("→ Click en 'Iniciar Proceso'");
        driver.findElement(By.id("btnUpdateStatus")).click();
        pauseForDemo();
        
        System.out.println("→ Leyendo texto sin esperar a que cambie...");
        String statusText = driver.findElement(By.id("statusText")).getText();
        System.out.println("   Texto actual: '" + statusText + "'");
        System.out.println("⚠️  ERROR: El texto no cambió todavía (cambia después de 2s)");
        
        // Esta assertion FALLA (el texto esperado "Procesando datos" aún no aparece)
        assertTrue(statusText.contains("Procesando datos"), 
            "El texto debería contener 'Procesando datos' pero es: '" + statusText + "'");
    }

    @Test
    public void test4_OverlayBlocksFails() {
        openTestPage();
        pauseForDemo();
        
        System.out.println("→ Click en 'Mostrar Alerta Temporal'");
        driver.findElement(By.id("btnShowAlert")).click();
        pauseForDemo();
        
        System.out.println("→ Intentando click en botón cubierto por overlay...");
        System.out.println("⚠️  ERROR: Overlay bloqueando el botón (desaparece en 3s)");
        
        // Esta línea FALLA con ElementClickInterceptedException (overlay bloquea el click)
        driver.findElement(By.id("btnAfterAlert")).click();
        
        fail("Este código nunca se ejecuta (el test falla antes)");
    }
}
