package tests;

import base.BaseTest;
import pages.DemoPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test que demuestra el uso CORRECTO de Esperas Explícitas (Explicit Waits)
 * 
 * ✅ Este test está diseñado para PASAR correctamente
 * 
 * PROPÓSITO DIDÁCTICO:
 * Este test muestra cómo usar WebDriverWait + ExpectedConditions para manejar
 * correctamente el contenido dinámico en aplicaciones web modernas
 * 
 * TÉCNICAS DEMOSTRADAS:
 * 1. WebDriverWait - Define el tiempo MÁXIMO de espera
 * 2. ExpectedConditions.visibilityOfElementLocated() - Espera a que aparezca un elemento
 * 3. ExpectedConditions.elementToBeClickable() - Espera a que un elemento sea clickeable
 * 4. ExpectedConditions.textToBePresentInElement() - Espera a que cambie el texto
 * 5. ExpectedConditions.invisibilityOfElementLocated() - Espera a que desaparezca un elemento
 * 
 * VENTAJAS DE ESPERAS EXPLÍCITAS:
 * ✓ Precisas: Solo esperan el tiempo necesario
 * ✓ Estables: No dependen de timing aleatorio
 * ✓ Profesionales: Son la mejor práctica en la industria
 * ✓ Legibles: El código expresa la intención claramente
 * ✓ Mantenibles: Fáciles de entender y modificar
 * 
 * COMPARACIÓN:
 * - DemoWithoutWaitTest: SIN esperas → FALLA
 * - Este test: CON esperas explícitas → PASA ✓
 */
public class DemoWithExplicitWaitTest extends BaseTest {
    
    /**
     * Test que maneja correctamente elementos que aparecen después de carga asíncrona
     * CON esperas explícitas
     * 
     * ESCENARIO:
     * 1. Click en "Cargar Contenido Remoto"
     * 2. JavaScript simula delay de 3 segundos (como una API real)
     * 3. Test ESPERA hasta que aparezca el botón "Procesar Datos"
     * 4. Hace click cuando el botón está disponible
     * 
     * RESULTADO ESPERADO: ✅ Test PASA correctamente
     * 
     * POR QUÉ FUNCIONA:
     * - WebDriverWait espera hasta 10 segundos (timeout máximo)
     * - ExpectedConditions.visibilityOfElementLocated() verifica cada 500ms
     * - Cuando el botón aparece (después de 3s), el test continúa inmediatamente
     * - NO espera los 10 segundos completos, solo lo necesario
     * 
     * CASO REAL EQUIVALENTE:
     * - Dashboard que carga datos desde API REST
     * - Tabla con datos de servidor que tarda en renderizar
     * - Modal que se abre después de procesar información
     * 
     * TÉCNICA UTILIZADA:
     * ExpectedConditions.visibilityOfElementLocated()
     * - Verifica que el elemento existe en el DOM
     * - Verifica que el elemento es visible (no display:none)
     * - Verifica que tiene tamaño (height y width > 0)
     */
    @Test
    public void testAsyncContentWithWait() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  TEST CON ESPERAS EXPLÍCITAS: Carga Asíncrona                  ║");
        System.out.println("║  RESULTADO ESPERADO: ✅ PASA correctamente                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        // Inicializar el Page Object con el driver
        DemoPage page = new DemoPage(driver);
        
        // Navegar a la página de prueba
        page.navigateTo(pageUrl);
        pauseForDemo(); // ⏱️ Pausa para ver la página cargada
        
        // PASO 1: Click en "Cargar Contenido Remoto"
        // Este click inicia un proceso asíncrono de 3 segundos
        page.clickLoadContent();
        pauseForDemo(); // ⏱️ Pausa para ver el loading aparecer
        
        // PASO 2: Esperar hasta que desaparezca el indicador de carga
        // Esto es opcional pero demuestra el uso de invisibilityOfElementLocated()
        boolean loadingDisappeared = page.waitForLoadingToDisappear();
        System.out.println("  ✓ Indicador de carga desapareció: " + loadingDisappeared);
        
        // PASO 3: Esperar y hacer click en "Procesar Datos"
        // ✅ ESPERA EXPLÍCITA en acción:
        //    - El Page Object usa waitForElementToBeClickable()
        //    - Espera hasta que el botón aparezca (máx. 10 segundos)
        //    - Cuando aparece (después de 3s), hace click inmediatamente
        page.clickProcessData();
        pauseForDemo(); // ⏱️ Pausa para ver el resultado
        
        // PASO 4: Verificar que apareció el resultado
        boolean resultVisible = page.isProcessResultVisible();
        System.out.println("  ✓ Resultado visible: " + resultVisible);
        
        // VALIDACIÓN: El resultado debe estar visible
        assertTrue(resultVisible, "El resultado del procesamiento debería estar visible");
        
        System.out.println("\n✅ TEST COMPLETADO EXITOSAMENTE");
        System.out.println("   - El test esperó el tiempo necesario (no más, no menos)");
        System.out.println("   - Las esperas explícitas manejaron la sincronización correctamente");
        System.out.println("   - El test es ESTABLE y CONFIABLE");
        pauseForDemo(1500); // ⏱️ Pausa final para leer el resultado
    }
    
    /**
     * Test que maneja correctamente botones que se habilitan después de validación
     * CON esperas explícitas
     * 
     * ESCENARIO:
     * 1. Escribir en campo de usuario (dispara validación de 2 segundos)
     * 2. Test ESPERA hasta que el botón esté habilitado
     * 3. Hace click cuando el botón está clickeable
     * 
     * RESULTADO ESPERADO: ✅ Test PASA correctamente
     * 
     * POR QUÉ FUNCIONA:
     * - ExpectedConditions.elementToBeClickable() verifica:
     *   1. Que el elemento es visible
     *   2. Que el elemento está habilitado (enabled = true)
     *   3. Que no está cubierto por otro elemento
     * - Solo hace click cuando TODAS las condiciones se cumplen
     * 
     * CASO REAL EQUIVALENTE:
     * - Formularios con validación asíncrona del servidor
     * - Botones de pago que se habilitan después de verificar tarjeta
     * - Campos de autocompletado que cargan opciones de API
     * - Términos y condiciones que deben aceptarse primero
     * 
     * TÉCNICA UTILIZADA:
     * ExpectedConditions.elementToBeClickable()
     * - Más estricta que visibilityOfElementLocated()
     * - Verifica que el elemento está listo para interacción
     * - Previene ElementNotInteractableException
     */
    @Test
    public void testClickableElementWithWait() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  TEST CON ESPERAS EXPLÍCITAS: Botón que se Habilita           ║");
        System.out.println("║  RESULTADO ESPERADO: ✅ PASA correctamente                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        // Inicializar el Page Object
        DemoPage page = new DemoPage(driver);
        
        // Navegar a la página de prueba
        page.navigateTo(pageUrl);
        pauseForDemo(); // ⏱️ Pausa para ver la página cargada
        
        // PASO 1: Escribir en el campo de usuario
        // Esto dispara una validación asíncrona de 2 segundos
        String username = "testuser";
        page.enterUsername(username);
        pauseForDemo(); // ⏱️ Pausa para ver el texto ingresado
        
        // PASO 2: Esperar y hacer click en el botón de envío
        // ✅ ESPERA EXPLÍCITA en acción:
        //    - El Page Object usa waitForElementToBeClickable()
        //    - Espera hasta que el botón esté habilitado (máx. 10 segundos)
        //    - Verifica que el botón no está disabled
        //    - Hace click solo cuando está completamente clickeable
        page.clickSubmitButton();
        pauseForDemo(); // ⏱️ Pausa para ver el resultado
        
        // PASO 3: Verificar que apareció el resultado
        boolean resultVisible = page.isSubmitResultVisible();
        System.out.println("  ✓ Resultado visible: " + resultVisible);
        
        // PASO 4: Verificar el texto del resultado
        String resultText = page.getSubmitResultText();
        System.out.println("  ✓ Texto del resultado: " + resultText);
        
        // VALIDACIONES:
        assertTrue(resultVisible, "El resultado debería estar visible");
        assertTrue(resultText.contains(username), 
                   "El resultado debería contener el nombre de usuario: " + username);
        
        System.out.println("\n✅ TEST COMPLETADO EXITOSAMENTE");
        System.out.println("   - elementToBeClickable() esperó hasta que el botón se habilitó");
        System.out.println("   - No hubo ElementNotInteractableException");
        System.out.println("   - El test es ROBUSTO y PROFESIONAL");
    }
    
    /**
     * Test que maneja correctamente texto que cambia dinámicamente
     * CON esperas explícitas
     * 
     * ESCENARIO:
     * 1. Click en "Iniciar Proceso"
     * 2. El texto cambia en secuencia: "Sistema listo" → "Iniciando..." → 
     *    "Procesando..." → "Finalizando..." → "Completado"
     * 3. Test ESPERA a que aparezca cada estado esperado
     * 
     * RESULTADO ESPERADO: ✅ Test PASA correctamente
     * 
     * POR QUÉ FUNCIONA:
     * - textToBePresentInElement() espera hasta que el texto específico aparezca
     * - Verifica cada 500ms si el texto cambió
     * - No importa cuánto tarde en cambiar, el test espera pacientemente
     * - Cuando aparece el texto correcto, continúa inmediatamente
     * 
     * CASO REAL EQUIVALENTE:
     * - Estados de pedidos: Pendiente → Confirmado → Enviado → Entregado
     * - Progreso de uploads: Validando → Subiendo → Procesando → Completo
     * - Estados de pipeline CI/CD: Building → Testing → Deploying → Success
     * - Notificaciones que cambian: "Guardando..." → "Guardado"
     * 
     * TÉCNICA UTILIZADA:
     * ExpectedConditions.textToBePresentInElement()
     * - Espera hasta que un elemento contenga un texto específico
     * - Ideal para validar cambios de estado
     * - Previene tests flaky por timing
     */
    @Test
    public void testDynamicTextWithWait() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  TEST CON ESPERAS EXPLÍCITAS: Texto Dinámico                   ║");
        System.out.println("║  RESULTADO ESPERADO: ✅ PASA correctamente                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        // Inicializar el Page Object
        DemoPage page = new DemoPage(driver);
        
        // Navegar a la página de prueba
        page.navigateTo(pageUrl);
        pauseForDemo(); // ⏱️ Pausa para ver la página cargada
        
        // PASO 1: Click en "Iniciar Proceso"
        // Esto inicia una secuencia de cambios de estado:
        // 0s: "Iniciando proceso..."
        // 2s: "Procesando datos..."
        // 4s: "Finalizando..."
        // 6s: "Proceso completado exitosamente"
        page.clickUpdateStatus();
        pauseForDemo(); // ⏱️ Pausa para ver el cambio de estado inicial
        
        // PASO 2: Esperar y verificar el primer cambio de estado
        // ✅ ESPERA EXPLÍCITA en acción:
        //    - waitForStatusText() usa textToBePresentInElement()
        //    - Espera hasta que aparezca "Iniciando proceso..."
        //    - Si ya está presente, continúa inmediatamente
        boolean hasInitiatingText = page.waitForStatusText("Iniciando proceso");
        System.out.println("  ✓ Estado alcanzado: 'Iniciando proceso'");
        assertTrue(hasInitiatingText, "Debería mostrar 'Iniciando proceso...'");
        
        // PASO 3: Esperar el siguiente estado
        // Esto demuestra la ESPERA INTELIGENTE:
        // - No importa si tarda 1 o 3 segundos
        // - El test espera hasta que el texto cambie
        // - No hay hardcoded Thread.sleep()
        boolean hasProcessingText = page.waitForStatusText("Procesando datos");
        System.out.println("  ✓ Estado alcanzado: 'Procesando datos'");
        assertTrue(hasProcessingText, "Debería mostrar 'Procesando datos...'");
        
        // PASO 4: Esperar el estado de finalización
        boolean hasFinalizingText = page.waitForStatusText("Finalizando");
        System.out.println("  ✓ Estado alcanzado: 'Finalizando'");
        assertTrue(hasFinalizingText, "Debería mostrar 'Finalizando...'");
        
        // PASO 5: Esperar el estado final
        boolean hasCompletedText = page.waitForStatusText("Proceso completado exitosamente");
        System.out.println("  ✓ Estado alcanzado: 'Proceso completado exitosamente'");
        assertTrue(hasCompletedText, "Debería mostrar 'Proceso completado exitosamente'");
        
        // PASO 6: Verificar el texto final
        String finalStatus = page.getStatusText();
        assertEquals("Proceso completado exitosamente", finalStatus, 
                    "El estado final debería ser 'Proceso completado exitosamente'");
        
        System.out.println("\n✅ TEST COMPLETADO EXITOSAMENTE");
        System.out.println("   - textToBePresentInElement() esperó cada cambio de estado");
        System.out.println("   - El test siguió la secuencia completa sin fallos");
        System.out.println("   - No hay tests flaky, 100% CONFIABLE");
    }
    
    /**
     * Test que maneja correctamente overlays/modales que desaparecen
     * CON esperas explícitas
     * 
     * ESCENARIO:
     * 1. Click en "Mostrar Alerta Temporal"
     * 2. Aparece un overlay que bloquea la página por 3 segundos
     * 3. Test ESPERA hasta que desaparezca el overlay
     * 4. Hace click en el botón que estaba detrás del overlay
     * 
     * RESULTADO ESPERADO: ✅ Test PASA correctamente
     * 
     * POR QUÉ FUNCIONA:
     * - invisibilityOfElementLocated() espera hasta que el elemento desaparezca
     * - Solo continúa cuando el overlay ya no está visible
     * - Esto asegura que el botón sea clickeable sin obstrucciones
     * 
     * CASO REAL EQUIVALENTE:
     * - Spinners de carga full-screen
     * - Modales de "Procesando pago..."
     * - Overlays de "Guardando cambios..."
     * - Notificaciones Toast que cubren botones temporalmente
     * - Animaciones de entrada/salida de modales
     * 
     * TÉCNICA UTILIZADA:
     * ExpectedConditions.invisibilityOfElementLocated()
     * - Espera hasta que un elemento YA NO sea visible
     * - Útil para elementos que bloquean la interacción
     * - Previene ElementClickInterceptedException
     */
    @Test
    public void testOverlayWithWait() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  TEST CON ESPERAS EXPLÍCITAS: Overlay Bloqueante              ║");
        System.out.println("║  RESULTADO ESPERADO: ✅ PASA correctamente                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        // Inicializar el Page Object
        DemoPage page = new DemoPage(driver);
        
        // Navegar a la página de prueba
        page.navigateTo(pageUrl);
        pauseForDemo(); // ⏱️ Pausa para ver la página cargada
        
        // PASO 1: Click en "Mostrar Alerta Temporal"
        // Esto muestra un overlay que desaparece después de 3 segundos
        page.clickShowAlert();
        pauseForDemo(); // ⏱️ Pausa para ver el overlay aparecer
        
        // PASO 2: Verificar que la alerta es visible
        boolean alertVisible = page.isTemporaryAlertVisible();
        System.out.println("  ✓ Alerta temporal visible: " + alertVisible);
        assertTrue(alertVisible, "La alerta debería estar visible");
        
        // PASO 3: Esperar hasta que desaparezca la alerta
        // ✅ ESPERA EXPLÍCITA en acción:
        //    - waitForAlertToDisappear() usa invisibilityOfElementLocated()
        //    - Espera hasta que el overlay desaparezca (máx. 10 segundos)
        //    - Verifica cada 500ms si ya desapareció
        //    - Solo continúa cuando la alerta ya no es visible
        boolean alertDisappeared = page.waitForAlertToDisappear();
        System.out.println("  ✓ Alerta desapareció: " + alertDisappeared);
        assertTrue(alertDisappeared, "La alerta debería haber desaparecido");
        
        // PASO 4: Hacer click en el botón que estaba detrás del overlay
        // Ahora es seguro hacer click porque el overlay ya no está
        page.clickAfterAlertButton();
        pauseForDemo(); // ⏱️ Pausa para ver el resultado
        
        // PASO 5: Verificar que apareció el resultado
        boolean resultVisible = page.isAfterAlertResultVisible();
        System.out.println("  ✓ Resultado visible: " + resultVisible);
        
        // VALIDACIÓN: El resultado debe estar visible
        assertTrue(resultVisible, "El resultado debería estar visible");
        
        System.out.println("\n✅ TEST COMPLETADO EXITOSAMENTE");
        System.out.println("   - invisibilityOfElementLocated() esperó a que desaparezca el overlay");
        System.out.println("   - No hubo ElementClickInterceptedException");
        System.out.println("   - El test manejó correctamente elementos bloqueantes");
        pauseForDemo(1500); // ⏱️ Pausa final para leer el resultado
    }
    
    /**
     * Test integrado que demuestra todos los tipos de esperas en un flujo completo
     * 
     * PROPÓSITO:
     * Mostrar cómo se combinan diferentes tipos de esperas explícitas
     * en un escenario real de automatización
     * 
     * TÉCNICAS COMBINADAS:
     * 1. elementToBeClickable() - Para botones activos
     * 2. visibilityOfElementLocated() - Para elementos que aparecen
     * 3. textToBePresentInElement() - Para cambios de estado
     * 4. invisibilityOfElementLocated() - Para overlays que desaparecen
     * 
     * RESULTADO ESPERADO: ✅ Test PASA correctamente
     */
    @Test
    public void testCompleteFlowWithExplicitWaits() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  TEST INTEGRADO: Todos los Tipos de Esperas Explícitas        ║");
        System.out.println("║  RESULTADO ESPERADO: ✅ PASA correctamente                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        // Inicializar el Page Object
        DemoPage page = new DemoPage(driver);
        
        // Navegar a la página
        page.navigateTo(pageUrl);
        pauseForDemo(); // ⏱️ Pausa para ver la página cargada
        
        String pageTitle = page.getPageTitle();
        System.out.println("✓ Página cargada: " + pageTitle);
        assertEquals("Demo - Esperas Explícitas en Selenium", pageTitle);
        
        // ESCENARIO 1: Carga asíncrona
        System.out.println("\n--- Ejecutando Escenario 1: Carga Asíncrona ---");
        pauseForDemo(800); // ⏱️ Breve pausa entre escenarios
        page.clickLoadContent();
        page.waitForLoadingToDisappear();
        page.clickProcessData();
        assertTrue(page.isProcessResultVisible());
        pauseForDemo(); // ⏱️ Pausa para ver el resultado del escenario
        
        // Recargar la página para el siguiente escenario
        page.navigateTo(pageUrl);
        pauseForDemo(500); // ⏱️ Breve pausa después de recargar
        
        // ESCENARIO 2: Botón que se habilita
        System.out.println("\n--- Ejecutando Escenario 2: Botón que se Habilita ---");
        pauseForDemo(800); // ⏱️ Breve pausa entre escenarios
        page.enterUsername("integration_test");
        page.clickSubmitButton();
        assertTrue(page.isSubmitResultVisible());
        pauseForDemo(); // ⏱️ Pausa para ver el resultado del escenario
        
        // Recargar la página para el siguiente escenario
        page.navigateTo(pageUrl);
        pauseForDemo(500); // ⏱️ Breve pausa después de recargar
        
        // ESCENARIO 3: Texto dinámico
        System.out.println("\n--- Ejecutando Escenario 3: Texto Dinámico ---");
        pauseForDemo(800); // ⏱️ Breve pausa entre escenarios
        page.clickUpdateStatus();
        page.waitForStatusText("Iniciando proceso");
        page.waitForStatusText("Procesando datos");
        page.waitForStatusText("Proceso completado exitosamente");
        pauseForDemo(); // ⏱️ Pausa para ver el resultado del escenario
        
        // Recargar la página para el siguiente escenario
        page.navigateTo(pageUrl);
        pauseForDemo(500); // ⏱️ Breve pausa después de recargar
        
        // ESCENARIO 4: Overlay bloqueante
        System.out.println("\n--- Ejecutando Escenario 4: Overlay Bloqueante ---");
        pauseForDemo(800); // ⏱️ Breve pausa entre escenarios
        page.clickShowAlert();
        page.waitForAlertToDisappear();
        page.clickAfterAlertButton();
        assertTrue(page.isAfterAlertResultVisible());
        pauseForDemo(); // ⏱️ Pausa para ver el resultado del escenario
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  ✅ TODOS LOS ESCENARIOS COMPLETADOS EXITOSAMENTE              ║");
        System.out.println("║                                                                ║");
        System.out.println("║  Las Esperas Explícitas demostraron:                          ║");
        System.out.println("║  ✓ Estabilidad (0% flakiness)                                 ║");
        System.out.println("║  ✓ Eficiencia (solo espera lo necesario)                      ║");
        System.out.println("║  ✓ Legibilidad (código expresivo y claro)                     ║");
        System.out.println("║  ✓ Profesionalismo (best practices de la industria)           ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
