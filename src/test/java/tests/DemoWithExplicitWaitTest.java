package tests;

import base.BaseTest;
import pages.DemoPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DemoWithExplicitWaitTest extends BaseTest {
    
    @Test
    public void test1_AsyncContentWorks() {
     
        DemoPage page = new DemoPage(driver);
        page.navigateTo(pageUrl);
        pauseForDemo();
        
        System.out.println("→ Click en 'Cargar Contenido Remoto'");
        page.clickLoadContent();
        pauseForDemo();
        
        System.out.println("→ Esperando que aparezca el loading indicator...");
        page.waitForLoadingToDisappear();
        
        System.out.println("→ Esperando que aparezca 'btnProcessData' (espera explícita)...");
        page.clickProcessData();
        pauseForDemo();
        
        System.out.println("✅ Resultado visible");
        assertTrue(page.isProcessResultVisible(), "El resultado debería estar visible");
    }
    
    @Test
    public void test2_DisabledButtonWorks() {
      
        DemoPage page = new DemoPage(driver);
        page.navigateTo(pageUrl);
        pauseForDemo();
        
        System.out.println("→ Escribiendo en campo 'username'");
        page.enterUsername("testuser");
        pauseForDemo();
        
        System.out.println("→ Esperando que el botón se habilite (espera explícita)...");
        page.clickSubmitButton();
        pauseForDemo();
        
        System.out.println("✅ Resultado visible");
        assertTrue(page.isSubmitResultVisible(), "El resultado debería estar visible");
    }
    
    @Test
    public void test3_DynamicTextWorks() {
        
        DemoPage page = new DemoPage(driver);
        page.navigateTo(pageUrl);
        pauseForDemo();
        
        System.out.println("→ Click en 'Iniciar Proceso'");
        page.clickUpdateStatus();
        pauseForDemo();
        
        System.out.println("→ Esperando estado 'Iniciando proceso' (espera explícita)...");
        page.waitForStatusText("Iniciando proceso");
        
        System.out.println("→ Esperando estado 'Procesando datos' (espera explícita)...");
        page.waitForStatusText("Procesando datos");
        
        System.out.println("→ Esperando estado 'Proceso completado' (espera explícita)...");
        page.waitForStatusText("Proceso completado exitosamente");
        
        System.out.println("✅ Texto final correcto");
        String finalStatus = page.getStatusText();
        assertEquals("Proceso completado exitosamente", finalStatus);
    }
    
    @Test
    public void test4_OverlayBlocksWorks() {
        
        DemoPage page = new DemoPage(driver);
        page.navigateTo(pageUrl);
        pauseForDemo();
        
        System.out.println("→ Click en 'Mostrar Alerta Temporal'");
        page.clickShowAlert();
        pauseForDemo();
        
        System.out.println("→ Alerta visible, esperando que desaparezca (espera explícita)...");
        page.waitForAlertToDisappear();
        
        System.out.println("→ Overlay desapareció, ahora haciendo click...");
        page.clickAfterAlertButton();
        pauseForDemo();
        
        System.out.println("✅ Resultado visible");
        assertTrue(page.isAfterAlertResultVisible(), "El resultado debería estar visible");
    }
}
