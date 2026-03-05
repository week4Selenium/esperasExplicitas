package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

/**
 * Clase base para todos los tests de automatización
 * 
 * PROPÓSITO:
 * Esta clase contiene la configuración común que necesitan todos los tests:
 * - Inicialización del WebDriver con control explícito del ChromeDriverService
 * - Configuración del navegador
 * - Limpieza GARANTIZADA después de cada test (sin procesos zombie)
 * 
 * SOLUCIÓN PROFESIONAL IMPLEMENTADA:
 * - ChromeDriverService explícito para control total del ciclo de vida
 * - Cierre correcto tanto del driver como del service
 * - Sin procesos Chrome/ChromeDriver quedando abiertos
 */
public class BaseTest {
    
    // Variable protegida para que las clases hijas puedan acceder al driver
    protected WebDriver driver;
    
    // ChromeDriverService para control explícito del proceso ChromeDriver
    // CRÍTICO: Esto garantiza que el proceso se cierre correctamente
    private ChromeDriverService service;
    
    // URL del HTML local que vamos a probar
    protected String pageUrl;
    
    // ⏱️ DELAY PARA DEMOSTRACIONES
    // Tiempo de pausa (en milisegundos) después de cada acción
    // 1500ms = 1.5 segundos por acción (ajusta según necesidad)
    protected static final int DEMO_DELAY = 1500;
    
    /**
     * Método que se ejecuta ANTES de cada test (@BeforeEach)
     * 
     * CONFIGURACIÓN PROFESIONAL:
     * 1. Crear ChromeDriverService explícitamente (control del proceso)
     * 2. Configurar ChromeOptions (opciones del navegador)
     * 3. Crear ChromeDriver pasando service + options
     * 4. Configurar el driver y URL de prueba
     */
    @BeforeEach
    public void setUp() {
        try {
            // PASO 1: Configurar WebDriverManager (descarga chromedriver si no existe)
            WebDriverManager.chromedriver().setup();
            
            // PASO 2: Crear ChromeDriverService EXPLÍCITAMENTE
            // Esto es CRÍTICO para poder cerrar el servicio completamente después
            service = new ChromeDriverService.Builder()
                .usingAnyFreePort()  // Usa cualquier puerto disponible (evita conflictos)
                .build();
            
            // PASO 3: Configurar ChromeOptions
            ChromeOptions options = new ChromeOptions();
            
            // Opciones básicas
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-gpu");
            
            // OPCIONES CRÍTICAS para evitar procesos zombie:
            options.addArguments("--disable-dev-shm-usage");  // Evita problemas de memoria compartida
            options.addArguments("--no-sandbox");  // Desactiva sandbox (útil en CI/CD)
            options.addArguments("--remote-debugging-port=0");  // Sin puerto de debugging
            
            // Ocultar barra de automatización
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            options.setExperimentalOption("useAutomationExtension", false);
            
            // PASO 4: Crear ChromeDriver pasando SERVICE y OPTIONS
            // Esto nos da control total sobre el ciclo de vida
            driver = new ChromeDriver(service, options);
            
            // PASO 5: Configurar el driver
            driver.manage().window().maximize();
            
            // PASO 6: Preparar URL de la página de prueba
            File htmlFile = new File("src/test/resources/demo-page.html");
            pageUrl = "file:///" + htmlFile.getAbsolutePath().replace("\\", "/");
            
            System.out.println("==============================================");
            System.out.println("✓ WebDriver inicializado correctamente");
            System.out.println("✓ Navegador: Chrome");
            System.out.println("✓ URL de la página: " + pageUrl);
            System.out.println("==============================================");
            
        } catch (Exception e) {
            System.err.println("❌ Error en setUp: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Método que se ejecuta DESPUÉS de cada test (@AfterEach)
     * 
     * SOLUCIÓN PROFESIONAL - CIERRE COMPLETO:
     * 1. Cerrar WebDriver (driver.quit)
     * 2. Detener ChromeDriverService (service.stop) - CRÍTICO
     * 3. Limpiar referencias en bloque finally
     * 
     * ESTO GARANTIZA:
     * - NO quedan procesos chrome.exe abiertos
     * - NO quedan procesos chromedriver.exe abiertos
     * - Los tests se pueden ejecutar múltiples veces consecutivas sin problemas
     */
    @AfterEach
    public void tearDown() {
        try {
            // PASO 1: Cerrar el WebDriver
            if (driver != null) {
                try {
                    driver.quit();  // Cierra todas las ventanas y finaliza la sesión
                } catch (Exception e) {
                    System.err.println("⚠️ Error cerrando driver: " + e.getMessage());
                }
            }
            
            // PASO 2: Detener el ChromeDriverService (CRÍTICO)
            // Esto mata el proceso chromedriver.exe completamente
            if (service != null && service.isRunning()) {
                service.stop();
            }
            
            System.out.println("==============================================");
            System.out.println("✓ WebDriver y ChromeDriverService cerrados");
            System.out.println("==============================================");
            
        } catch (Exception e) {
            System.err.println("⚠️ Error en tearDown: " + e.getMessage());
        } finally {
            // PASO 3: Limpiar referencias (GARANTIZADO por finally)
            driver = null;
            service = null;
        }
    }
    
    /**
     * Método auxiliar para obtener el WebDriver
     */
    protected WebDriver getDriver() {
        return driver;
    }
    
    /**
     * Método auxiliar para abrir la página de prueba
     */
    protected void openTestPage() {
        driver.get(pageUrl);
        System.out.println("→ Página de prueba cargada: " + driver.getTitle());
    }
    
    /**
     * Pausa para demostración
     * 
     * PROPÓSITO:
     * Durante demostraciones en vivo o presentaciones a clientes,
     * hace que la automatización sea más lenta y VISIBLE.
     * 
     * @param milliseconds - tiempo de pausa en milisegundos
     */
    protected void pauseForDemo(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("⚠️ Pausa de demostración interrumpida");
        }
    }
    
    /**
     * Pausa para demostración usando el delay por defecto
     */
    protected void pauseForDemo() {
        pauseForDemo(DEMO_DELAY);
    }
}
