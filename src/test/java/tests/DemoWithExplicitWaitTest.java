package tests;

import base.BaseTest;
import pages.DemoPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests que demuestran el uso CORRECTO de Esperas Explícitas (Explicit Waits)
 * 
 * ✅ COMPORTAMIENTO ESPERADO: Todos estos tests PASAN
 * 
 * PROPÓSITO DIDÁCTICO:
 * Demostrar cómo usar WebDriverWait + ExpectedConditions para manejar
 * correctamente el contenido dinámico en aplicaciones web modernas
 * 
 * TÉCNICAS DEMOSTRADAS:
 * 1. ExpectedConditions.visibilityOfElementLocated() - Espera a que aparezca
 * 2. ExpectedConditions.elementToBeClickable() - Espera a que sea clickeable
 * 3. ExpectedConditions.textToBePresentInElement() - Espera cambio de texto
 * 4. ExpectedConditions.invisibilityOfElementLocated() - Espera que desaparezca
 * 
 * COMPARACIÓN CLARA:
 * ❌ DemoWithoutWaitTest: 4 tests SIN esperas → FALLAN
 * ✅ DemoWithExplicitWaitTest: 4 tests CON esperas → PASAN
 * 
 * Ejecuta ambas clases y compara los resultados en el reporte HTML.
 */
public class DemoWithExplicitWaitTest extends BaseTest {
    
    /**
     * Escenario 1: Contenido que carga de forma asíncrona CON esperas explícitas
     * 
     * CONTRASTE CON: test1_AsyncContentFails() en DemoWithoutWaitTest
     * 
     * QUÉ HACE:
     * 1. Click en "Cargar Contenido" → JavaScript tarda 3 segundos
     * 2. ESPERA explícitamente hasta que aparezca el botón "Procesar Datos"
     * 3. Hace click solo cuando el botón existe y es visible
     * 
     * POR QUÉ FUNCIONA:
     * WebDriverWait con visibilityOfElementLocated() espera hasta 10s
     * Verifica cada 500ms si el elemento apareció
     * Continúa inmediatamente cuando el botón está disponible (no espera los 10s completos)
     * 
     * RESULTADO: ✅ Test PASA
     */
    @Test
    public void test1_AsyncContentWorks() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  ✅ Test 1: Carga Asíncrona CON esperas                   ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
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
    
    /**
     * Escenario 2: Botón que se habilita después de validación CON esperas explícitas
     * 
     * CONTRASTE CON: test2_DisabledButtonFails() en DemoWithoutWaitTest
     * 
     * QUÉ HACE:
     * 1. Escribe en el campo → dispara validación de 2 segundos
     * 2. ESPERA explícitamente hasta que el botón se habilite
     * 3. Hace click solo cuando el botón está enabled=true
     * 
     * POR QUÉ FUNCIONA:
     * elementToBeClickable() verifica que el botón:
     * - Existe en el DOM
     * - Es visible
     * - Está habilitado (no disabled)
     * - No está cubierto por otro elemento
     * 
     * RESULTADO: ✅ Test PASA
     */
    @Test
    public void test2_DisabledButtonWorks() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  ✅ Test 2: Botón Deshabilitado CON esperas               ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
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
    
    /**
     * Escenario 3: Texto que cambia dinámicamente CON esperas explícitas
     * 
     * CONTRASTE CON: test3_DynamicTextFails() en DemoWithoutWaitTest
     * 
     * QUÉ HACE:
     * 1. Click en "Iniciar Proceso" → el texto cambia cada 2 segundos
     * 2. ESPERA explícitamente a que aparezca cada estado esperado
     * 3. Verifica cada cambio de texto en la secuencia correcta
     * 
     * POR QUÉ FUNCIONA:
     * textToBePresentInElement() espera hasta que el texto específico aparezca
     * No importa cuánto tarde, el test espera pacientemente
     * Continúa solo cuando el texto correcto está presente
     * 
     * RESULTADO: ✅ Test PASA
     */
    @Test
    public void test3_DynamicTextWorks() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  ✅ Test 3: Texto Dinámico CON esperas                    ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
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
    
    /**
     * Escenario 4: Overlay que bloquea la interacción CON esperas explícitas
     * 
     * CONTRASTE CON: test4_OverlayBlocksFails() en DemoWithoutWaitTest
     * 
     * QUÉ HACE:
     * 1. Click en "Mostrar Alerta" → aparece overlay por 3 segundos
     * 2. ESPERA explícitamente hasta que el overlay desaparezca
     * 3. Hace click en el botón solo cuando ya no está bloqueado
     * 
     * POR QUÉ FUNCIONA:
     * invisibilityOfElementLocated() espera hasta que el overlay desaparezca
     * Verifica cada 500ms si el elemento ya no es visible
     * Continúa solo cuando el camino está despejado
     * 
     * RESULTADO: ✅ Test PASA
     */
    @Test
    public void test4_OverlayBlocksWorks() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  ✅ Test 4: Overlay Bloqueante CON esperas                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
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
