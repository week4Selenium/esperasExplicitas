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
        
        page.clickLoadContent();
        pauseForDemo();
        
        page.waitForLoadingToDisappear();
        
        page.clickProcessData();
        pauseForDemo();
        
        assertTrue(page.isProcessResultVisible(), "El resultado deberÃ­a estar visible");
    }
    
    @Test
    public void test2_DisabledButtonWorks() {
      
        DemoPage page = new DemoPage(driver);
        page.navigateTo(pageUrl);
        pauseForDemo();
        
        page.enterUsername("testuser");
        pauseForDemo();
        
        page.clickSubmitButton();
        pauseForDemo();
        
        assertTrue(page.isSubmitResultVisible(), "El resultado deberÃ­a estar visible");
    }
    
    @Test
    public void test3_DynamicTextWorks() {
        
        DemoPage page = new DemoPage(driver);
        page.navigateTo(pageUrl);
        pauseForDemo();
        
        page.clickUpdateStatus();
        pauseForDemo();
        
        page.waitForStatusText("Iniciando proceso");
        
        page.waitForStatusText("Procesando datos");
        
        page.waitForStatusText("Proceso completado exitosamente");
        
        String finalStatus = page.getStatusText();
        assertEquals("Proceso completado exitosamente", finalStatus);
    }
    
    @Test
    public void test4_OverlayBlocksWorks() {
        
        DemoPage page = new DemoPage(driver);
        page.navigateTo(pageUrl);
        pauseForDemo();
        
        page.clickShowAlert();
        pauseForDemo();
        
        page.waitForAlertToDisappear();
        
        page.clickAfterAlertButton();
        pauseForDemo();
        
        assertTrue(page.isAfterAlertResultVisible(), "El resultado deberÃ­a estar visible");
    }
}
