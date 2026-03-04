package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test que demuestra los PROBLEMAS causados por NO usar Esperas Explícitas
 * 
 * ⚠️ ADVERTENCIA: Este test ESTÁ DISEÑADO PARA FALLAR
 * 
 * PROPÓSITO DIDÁCTICO:
 * Este test muestra qué sucede cuando intentamos automatizar una aplicación web
 * con contenido dinámico SIN usar esperas explícitas (WebDriverWait + ExpectedConditions)
 * 
 * PROBLEMAS QUE DEMUESTRA:
 * 1. NoSuchElementException - Elemento no existe todavía
 * 2. ElementNotInteractableException - Elemento no está listo para interacción
 * 3. Tests Flaky - A veces pasa, a veces falla (inconsistente)
 * 4. Validaciones incorrectas - Verifica antes de que el estado cambie
 * 
 * POR QUÉ FALLA EN LA VIDA REAL:
 * - Llamadas a APIs que tardan tiempo
 * - Animaciones CSS
 * - JavaScript que manipula el DOM
 * - Lazy loading de contenido
 * - Validaciones asíncronas
 * 
 * COMPARACIÓN:
 * - Este test: SIN esperas explícitas → FALLA
 * - DemoWithExplicitWaitTest: CON esperas explícitas → PASA
 */
public class DemoWithoutWaitTest extends BaseTest {
    
    /**
     * Test que intenta interactuar con elementos de carga asíncrona
     * SIN usar esperas explícitas
     * 
     * ESCENARIO:
     * 1. Click en "Cargar Contenido Remoto"
     * 2. JavaScript simula delay de 3 segundos
     * 3. Test intenta hacer click en "Procesar Datos" INMEDIATAMENTE
     * 
     * RESULTADO ESPERADO: NoSuchElementException
     * 
     * POR QUÉ FALLA:
     * El test ejecuta las líneas de código en milisegundos
     * JavaScript necesita 3 segundos para crear el botón
     * El test busca el botón antes de que exista en el DOM
     * 
     * CASO REAL EQUIVALENTE:
     * - Dashboard que carga datos desde una API REST
     * - Tabla con paginación que tarda en renderizar
     * - Modal que se abre después de una animación
     */
    @Test
    public void testAsyncContentWithoutWait() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  TEST SIN ESPERAS: Carga Asíncrona                             ║");
        System.out.println("║  RESULTADO ESPERADO: FALLA con NoSuchElementException          ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        // Abrir la página de prueba
        openTestPage();
        
        try {
            // PASO 1: Click en "Cargar Contenido Remoto"
            // Esto inicia un timer de JavaScript de 3 segundos
            System.out.println("→ Click en 'Cargar Contenido Remoto'");
            driver.findElement(By.id("btnLoadContent")).click();
            
            // PASO 2: Intentar hacer click en "Procesar Datos" INMEDIATAMENTE
            // ⚠️ PROBLEMA: El botón no existe todavía (tarda 3 segundos en aparecer)
            System.out.println("→ Buscando botón 'Procesar Datos' sin esperar...");
            System.out.println("⚠️  El botón no existe aún (aparece después de 3 segundos)");
            
            // Esta línea LANZARÁ una excepción porque el elemento no existe
            driver.findElement(By.id("btnProcessData")).click();
            
            // Esta línea NUNCA se ejecuta debido a la excepción anterior
            System.out.println("✓ Click en 'Procesar Datos'");
            fail("Este test debería haber fallado con NoSuchElementException");
            
        } catch (NoSuchElementException e) {
            // EXCEPCIÓN CAPTURADA - Esto es lo que esperamos
            System.out.println("\n❌ TEST FALLÓ (como se esperaba)");
            System.out.println("   Excepción: NoSuchElementException");
            System.out.println("   Mensaje: " + e.getMessage());
            System.out.println("\n📌 EXPLICACIÓN:");
            System.out.println("   - El test ejecutó el código más rápido que JavaScript");
            System.out.println("   - El botón 'btnProcessData' no existía en el DOM todavía");
            System.out.println("   - Selenium no pudo encontrar el elemento");
            System.out.println("\n💡 SOLUCIÓN:");
            System.out.println("   - Usar WebDriverWait con ExpectedConditions.visibilityOfElementLocated()");
            System.out.println("   - Ver: DemoWithExplicitWaitTest.testAsyncContentWithWait()");
            
            // Re-lanzar la excepción para que JUnit marque el test como fallido
            throw e;
        }
    }
    
    /**
     * Test que intenta hacer click en un botón deshabilitado
     * SIN usar esperas explícitas
     * 
     * ESCENARIO:
     * 1. Escribir en campo de usuario (dispara validación de 2 segundos)
     * 2. Test intenta hacer click en botón INMEDIATAMENTE
     * 3. Botón está visible pero DESHABILITADO (disabled="true")
     * 
     * RESULTADO ESPERADO: ElementNotInteractableException
     * 
     * POR QUÉ FALLA:
     * El botón existe y es visible, pero NO está habilitado
     * JavaScript lo habilita después de 2 segundos
     * El test intenta hacer click antes de que se habilite
     * 
     * CASO REAL EQUIVALENTE:
     * - Formularios con validación asíncrona
     * - Botones de pago que se habilitan después de verificar datos
     * - Campos de autocompletado que necesitan cargar opciones
     */
    @Test
    public void testClickableElementWithoutWait() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  TEST SIN ESPERAS: Botón que se Habilita                       ║");
        System.out.println("║  RESULTADO ESPERADO: FALLA con ElementNotInteractableException ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        // Abrir la página de prueba
        openTestPage();
        
        try {
            // PASO 1: Escribir en el campo de usuario
            // Esto dispara una validación asíncrona de 2 segundos
            System.out.println("→ Escribiendo en campo de usuario");
            driver.findElement(By.id("username")).sendKeys("testuser");
            
            // PASO 2: Intentar hacer click en el botón INMEDIATAMENTE
            // ⚠️ PROBLEMA: El botón está VISIBLE pero DESHABILITADO
            System.out.println("→ Intentando hacer click en botón deshabilitado...");
            System.out.println("⚠️  El botón está disabled (se habilita después de 2 segundos)");
            
            // Esta línea LANZARÁ una excepción porque el botón no está habilitado
            driver.findElement(By.id("btnSubmit")).click();
            
            // Esta línea NUNCA se ejecuta debido a la excepción anterior
            System.out.println("✓ Click en botón 'Enviar Formulario'");
            fail("Este test debería haber fallado con ElementNotInteractableException");
            
        } catch (Exception e) {
            // EXCEPCIÓN CAPTURADA
            System.out.println("\n❌ TEST FALLÓ (como se esperaba)");
            System.out.println("   Excepción: " + e.getClass().getSimpleName());
            System.out.println("   Mensaje: " + e.getMessage());
            System.out.println("\n📌 EXPLICACIÓN:");
            System.out.println("   - El botón existe en el DOM y es visible");
            System.out.println("   - Pero está deshabilitado (atributo disabled='true')");
            System.out.println("   - Selenium no puede interactuar con elementos deshabilitados");
            System.out.println("\n💡 SOLUCIÓN:");
            System.out.println("   - Usar WebDriverWait con ExpectedConditions.elementToBeClickable()");
            System.out.println("   - Ver: DemoWithExplicitWaitTest.testClickableElementWithWait()");
            
            // Re-lanzar la excepción para que JUnit marque el test como fallido
            throw e;
        }
    }
    
    /**
     * Test que intenta validar texto que cambia dinámicamente
     * SIN usar esperas explícitas
     * 
     * ESCENARIO:
     * 1. Click en "Iniciar Proceso"
     * 2. El texto cambia: "Sistema listo" → "Iniciando..." → "Procesando..." → "Completado"
     * 3. Test verifica el texto INMEDIATAMENTE
     * 
     * RESULTADO ESPERADO: Test FLAKY (inconsistente)
     * 
     * POR QUÉ ES PROBLEMÁTICO:
     * El test verifica el texto antes de que cambie
     * A veces puede pasar por suerte (timing coincide)
     * A veces falla (el texto no cambió todavía)
     * Esto hace que el test sea NO CONFIABLE
     * 
     * CASO REAL EQUIVALENTE:
     * - Estados de pedidos (Pendiente → En Proceso → Enviado → Entregado)
     * - Progreso de uploads/downloads
     * - Notificaciones que cambian de mensaje
     */
    @Test
    public void testDynamicTextWithoutWait() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  TEST SIN ESPERAS: Texto Dinámico                              ║");
        System.out.println("║  RESULTADO ESPERADO: FALLA (validación incorrecta)            ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        // Abrir la página de prueba
        openTestPage();
        
        // PASO 1: Click en "Iniciar Proceso"
        // Esto inicia una secuencia de cambios de estado
        System.out.println("→ Click en 'Iniciar Proceso'");
        driver.findElement(By.id("btnUpdateStatus")).click();
        
        // PASO 2: Intentar leer el texto INMEDIATAMENTE
        // ⚠️ PROBLEMA: El texto cambia después de 2 segundos
        System.out.println("→ Leyendo texto de estado sin esperar...");
        String statusText = driver.findElement(By.id("statusText")).getText();
        System.out.println("   Texto obtenido: '" + statusText + "'");
        
        // PASO 3: Verificar si el texto es el esperado
        // Esta validación probablemente FALLE porque el texto no cambió todavía
        System.out.println("\n⚠️  Verificando si el texto es 'Procesando datos...'");
        System.out.println("   Texto actual: '" + statusText + "'");
        
        // PROBLEMA: El estado cambia en esta secuencia:
        // - 0s: "Iniciando proceso..."
        // - 2s: "Procesando datos..."
        // - 4s: "Finalizando..."
        // - 6s: "Proceso completado exitosamente"
        //
        // Si verificamos inmediatamente, obtendremos "Iniciando proceso..."
        // NO "Procesando datos..."
        
        if (statusText.contains("Procesando datos...")) {
            System.out.println("✓ Texto correcto (pero fue por suerte/timing)");
        } else {
            System.out.println("\n❌ TEST FALLÓ (como se esperaba)");
            System.out.println("   Texto esperado: 'Procesando datos...'");
            System.out.println("   Texto obtenido: '" + statusText + "'");
            System.out.println("\n📌 EXPLICACIÓN:");
            System.out.println("   - El test verificó el texto ANTES de que cambiara");
            System.out.println("   - JavaScript necesita tiempo para actualizar el estado");
            System.out.println("   - El test es más rápido que las actualizaciones del DOM");
            System.out.println("\n💡 SOLUCIÓN:");
            System.out.println("   - Usar WebDriverWait con ExpectedConditions.textToBePresentInElement()");
            System.out.println("   - Ver: DemoWithExplicitWaitTest.testDynamicTextWithWait()");
            
            // Marcar el test como fallido
            fail("El texto no cambió a tiempo. Necesita esperas explícitas.");
        }
    }
    
    /**
     * Test que intenta interactuar mientras hay un overlay visible
     * SIN usar esperas explícitas
     * 
     * ESCENARIO:
     * 1. Click en "Mostrar Alerta Temporal"
     * 2. Aparece un overlay que bloquea la página por 3 segundos
     * 3. Test intenta hacer click en botón que está DETRÁS del overlay
     * 
     * RESULTADO ESPERADO: ElementClickInterceptedException
     * 
     * POR QUÉ FALLA:
     * El overlay/modal está cubriendo el botón
     * Selenium detecta que otro elemento bloqueará el click
     * El click no puede ejecutarse
     * 
     * CASO REAL EQUIVALENTE:
     * - Modales de carga que bloquean la UI
     * - Spinners full-screen
     * - Overlays de "Procesando pago..."
     * - Notificaciones grandes que cubren contenido
     */
    @Test
    public void testOverlayWithoutWait() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  TEST SIN ESPERAS: Overlay Bloqueante                          ║");
        System.out.println("║  RESULTADO ESPERADO: FALLA con ElementClickInterceptedException║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        // Abrir la página de prueba
        openTestPage();
        
        try {
            // PASO 1: Click en "Mostrar Alerta Temporal"
            // Esto muestra un overlay que desaparece después de 3 segundos
            System.out.println("→ Click en 'Mostrar Alerta Temporal'");
            driver.findElement(By.id("btnShowAlert")).click();
            
            // PASO 2: Intentar hacer click en el botón que está DETRÁS del overlay
            // ⚠️ PROBLEMA: El overlay está cubriendo el botón
            System.out.println("→ Intentando hacer click en botón cubierto por overlay...");
            System.out.println("⚠️  El overlay está bloqueando el botón (desaparece en 3 segundos)");
            
            // Esperar un momento para que el botón aparezca (pero el overlay sigue ahí)
            Thread.sleep(100);
            
            // Esta línea LANZARÁ una excepción porque el overlay bloquea el click
            driver.findElement(By.id("btnAfterAlert")).click();
            
            // Esta línea NUNCA se ejecuta debido a la excepción anterior
            System.out.println("✓ Click en botón 'Acción Después de Alerta'");
            fail("Este test debería haber fallado con ElementClickInterceptedException");
            
        } catch (Exception e) {
            // EXCEPCIÓN CAPTURADA
            System.out.println("\n❌ TEST FALLÓ (como se esperaba)");
            System.out.println("   Excepción: " + e.getClass().getSimpleName());
            System.out.println("   Mensaje: " + e.getMessage());
            System.out.println("\n📌 EXPLICACIÓN:");
            System.out.println("   - El botón existe y está visible en el DOM");
            System.out.println("   - Pero otro elemento (el overlay) está cubriendo el botón");
            System.out.println("   - Selenium detecta que el click sería interceptado");
            System.out.println("\n💡 SOLUCIÓN:");
            System.out.println("   - Usar WebDriverWait con ExpectedConditions.invisibilityOfElementLocated()");
            System.out.println("   - Esperar a que el overlay desaparezca ANTES de hacer click");
            System.out.println("   - Ver: DemoWithExplicitWaitTest.testOverlayWithWait()");
            
            // Re-lanzar la excepción para que JUnit marque el test como fallido
            throw e;
        }
    }
}
