package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests que demuestran los PROBLEMAS REALES causados por NO usar Esperas Explícitas
 * 
 * ⚠️ ADVERTENCIA: Estos tests FALLAN NATURALMENTE
 * 
 * PROPÓSITO DIDÁCTICO:
 * Estos tests muestran qué sucede en aplicaciones web reales cuando NO usamos
 * esperas explícitas (WebDriverWait + ExpectedConditions)
 * 
 * ERRORES QUE VERÁS:
 * 1. NoSuchElementException - Elemento no existe todavía en el DOM
 * 2. ElementNotInteractableException - Elemento existe pero no está listo
 * 3. Fallo de Assertion - El texto aún no cambió
 * 4. ElementClickInterceptedException - Overlay bloqueando el elemento
 * 
 * COMPARACIÓN CLARA:
 * ❌ DemoWithoutWaitTest (esta clase): 4 tests SIN esperas → FALLAN
 * ✅ DemoWithExplicitWaitTest: 4 tests CON esperas → PASAN
 * 
 * Ejecuta ambas clases para ver la diferencia en el reporte HTML.
 */
public class DemoWithoutWaitTest extends BaseTest {
    
    /**
     * Escenario 1: Contenido que carga de forma asíncrona (simula llamada a API)
     * 
     * QUÉ HACE:
     * 1. Click en "Cargar Contenido" → JavaScript tarda 3 segundos en crear el botón
     * 2. Intenta hacer click inmediatamente en el botón "Procesar Datos"
     * 
     * POR QUÉ FALLA:
     * El botón no existe todavía en el DOM cuando el test lo busca
     * 
     * ERROR ESPERADO: NoSuchElementException
     * 
     * SOLUCIÓN: Ver testAsyncContentWithWait() en DemoWithExplicitWaitTest
     */
    @Test
    public void test1_AsyncContentFails() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║  ❌ Test 1: Carga Asíncrona SIN esperas                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        
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
    
    /**
     * Escenario 2: Botón que se habilita después de validación
     * 
     * QUÉ HACE:
     * 1. Escribe en el campo → dispara validación asíncrona de 2 segundos
     * 2. Intenta hacer click inmediatamente en el botón (que está deshabilitado)
     * 
     * POR QUÉ FALLA:
     * El botón existe y es visible, pero tiene atributo disabled=true
     * 
     * ERROR ESPERADO: ElementNotInteractableException
     * 
     * SOLUCIÓN: Ver testClickableElementWithWait() en DemoWithExplicitWaitTest
     */
    @Test
    public void test2_DisabledButtonFails() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║  ❌ Test 2: Botón Deshabilitado SIN esperas             ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        
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
    
    /**
     * Escenario 3: Texto que cambia dinámicamente en tiempo real
     * 
     * QUÉ HACE:
     * 1. Click en "Iniciar Proceso" → el texto cambia cada 2 segundos
     * 2. Lee el texto inmediatamente y lo valida
     * 
     * POR QUÉ FALLA:
     * El texto esperado aún no cambió cuando el test lo lee
     * Secuencia: "Sistema listo" → (2s) → "Iniciando" → (2s) → "Procesando" → (2s) → "Completado"
     * 
     * ERROR ESPERADO: AssertionFailedError (texto incorrecto)
     * 
     * SOLUCIÓN: Ver testDynamicTextWithWait() en DemoWithExplicitWaitTest
     */
    @Test
    public void test3_DynamicTextFails() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║  ❌ Test 3: Texto Dinámico SIN esperas                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        
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
    
    /**
     * Escenario 4: Overlay que bloquea la interacción con elementos
     * 
     * QUÉ HACE:
     * 1. Click en "Mostrar Alerta" → aparece overlay que desaparece en 3 segundos
     * 2. Intenta hacer click en botón que está DETRÁS del overlay
     * 
     * POR QUÉ FALLA:
     * El overlay está cubriendo el botón, Selenium detecta que el click sería bloqueado
     * 
     * ERROR ESPERADO: ElementClickInterceptedException
     * 
     * SOLUCIÓN: Ver testOverlayWithWait() en DemoWithExplicitWaitTest
     */
    @Test
    public void test4_OverlayBlocksFails() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║  ❌ Test 4: Overlay Bloqueante SIN esperas              ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        
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
