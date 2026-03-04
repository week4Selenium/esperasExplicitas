package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Clase base para todos los Page Objects
 * 
 * PATRÓN DE DISEÑO: Page Object Model (POM)
 * 
 * PROPÓSITO:
 * Implementa el patrón Page Object Model que separa:
 * - La lógica de localización de elementos (en esta clase)
 * - La lógica de negocio de los tests (en las clases de test)
 * 
 * VENTAJAS DEL POM:
 * 1. Código más mantenible: Si cambia un localizador, solo se modifica en un lugar
 * 2. Reusabilidad: Los métodos pueden usarse en múltiples tests
 * 3. Legibilidad: Los tests se leen como casos de uso de negocio
 * 4. Separación de responsabilidades: Los tests no saben cómo se localizan los elementos
 * 
 * ESPERAS EXPLÍCITAS:
 * Esta clase encapsula el uso de WebDriverWait y ExpectedConditions
 * proporcionando métodos reutilizables para escenarios comunes
 */
public class BasePage {
    
    // WebDriver para interactuar con el navegador
    protected WebDriver driver;
    
    // WebDriverWait para implementar esperas explícitas
    // Se inicializa con un timeout específico
    protected WebDriverWait wait;
    
    // Constante para el tiempo de espera por defecto (en segundos)
    // Este es el tiempo MÁXIMO que esperaremos por una condición
    private static final int DEFAULT_WAIT_TIMEOUT = 10;
    
    /**
     * Constructor que inicializa el driver y el wait
     * 
     * @param driver - instancia del WebDriver que controla el navegador
     * 
     * POR QUÉ USAR CONSTRUCTOR:
     * - Asegura que toda instancia de Page tenga acceso al driver
     * - Inicializa WebDriverWait una sola vez para toda la página
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        
        // Crear instancia de WebDriverWait con timeout de 10 segundos
        // Duration.ofSeconds(10) - tiempo máximo de espera
        // 
        // IMPORTANTE: Este timeout NO es una espera fija
        // WebDriverWait verifica la condición cada 500ms (por defecto)
        // Si la condición se cumple en 2 segundos, continúa inmediatamente
        // Solo espera los 10 segundos completos si la condición nunca se cumple
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIMEOUT));
    }
    
    /**
     * Constructor sobrecargado que permite especificar un timeout personalizado
     * 
     * @param driver - instancia del WebDriver
     * @param timeoutInSeconds - tiempo de espera personalizado en segundos
     * 
     * CASO DE USO:
     * Algunas operaciones pueden tardar más (ej: uploads de archivos, reportes grandes)
     * Permite flexibilidad sin cambiar el timeout por defecto
     */
    public BasePage(WebDriver driver, int timeoutInSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
    }
    
    // ==========================================
    // MÉTODOS CON ESPERAS EXPLÍCITAS
    // ==========================================
    
    /**
     * Espera hasta que un elemento sea VISIBLE en la página
     * 
     * @param locator - By localizador del elemento (ej: By.id("btnSubmit"))
     * @return WebElement - el elemento una vez que es visible
     * 
     * CUÁNDO USAR:
     * - Elementos que aparecen después de una carga asíncrona
     * - Elementos inicialmente ocultos (display: none)
     * - Modales, popups, mensajes que aparecen dinámicamente
     * 
     * QUÉ HACE ExpectedConditions.visibilityOfElementLocated():
     * - Verifica que el elemento existe en el DOM
     * - Verifica que el elemento tiene altura y ancho mayor que 0
     * - Verifica que el elemento no está oculto (display: none, visibility: hidden)
     */
    protected WebElement waitForElementToBeVisible(By locator) {
        System.out.println("  → Esperando que sea visible: " + locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * Espera hasta que un elemento sea CLICKEABLE
     * 
     * @param locator - By localizador del elemento
     * @return WebElement - el elemento una vez que es clickeable
     * 
     * CUÁNDO USAR:
     * - Botones que se habilitan después de validaciones
     * - Enlaces que aparecen después de animaciones
     * - Elementos que estaban cubiertos por overlays
     * 
     * QUÉ HACE ExpectedConditions.elementToBeClickable():
     * - Verifica que el elemento es visible (como visibilityOfElementLocated)
     * - Verifica que el elemento está habilitado (enabled = true)
     * - Verifica que no está cubierto por otro elemento
     * 
     * DIFERENCIA CON visibilityOfElementLocated:
     * Un elemento puede ser visible pero no clickeable (ej: un botón disabled)
     */
    protected WebElement waitForElementToBeClickable(By locator) {
        System.out.println("  → Esperando que sea clickeable: " + locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    /**
     * Espera hasta que un elemento tenga un texto específico
     * 
     * @param locator - By localizador del elemento
     * @param expectedText - texto que esperamos que contenga el elemento
     * @return boolean - true si el texto está presente, false si timeout
     * 
     * CUÁNDO USAR:
     * - Verificar mensajes de éxito/error
     * - Esperar cambios de estado (ej: "Procesando..." → "Completado")
     * - Validar actualizaciones dinámicas de texto
     * 
     * CASO REAL:
     * Un dashboard que muestra "Cargando datos..." y luego "50 registros encontrados"
     * Sin esta espera, el test podría fallar al verificar antes del cambio
     */
    protected boolean waitForTextToBePresentInElement(By locator, String expectedText) {
        System.out.println("  → Esperando texto '" + expectedText + "' en: " + locator);
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, expectedText));
    }
    
    /**
     * Espera hasta que un elemento YA NO SEA VISIBLE (invisibilidad)
     * 
     * @param locator - By localizador del elemento
     * @return boolean - true si el elemento es invisible, false si timeout
     * 
     * CUÁNDO USAR:
     * - Esperar a que desaparezca un loader/spinner
     * - Esperar a que se cierre un modal
     * - Esperar a que desaparezca un overlay bloqueante
     * - Esperar a que desaparezca un mensaje temporal
     * 
     * CASO REAL MUY COMÚN:
     * Páginas con overlays de carga que bloquean la interacción
     * Sin esta espera, obtendrás ElementClickInterceptedException
     */
    protected boolean waitForElementToBeInvisible(By locator) {
        System.out.println("  → Esperando que desaparezca: " + locator);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
    /**
     * Espera hasta que un elemento tenga un atributo específico con un valor específico
     * 
     * @param locator - By localizador del elemento
     * @param attribute - nombre del atributo (ej: "class", "disabled", "value")
     * @param value - valor esperado del atributo
     * @return boolean - true si el atributo tiene el valor esperado
     * 
     * CUÁNDO USAR:
     * - Verificar que un botón cambie de disabled a enabled
     * - Verificar que una clase CSS cambie (ej: "loading" → "loaded")
     * - Verificar valores de inputs después de autocompletar
     */
    protected boolean waitForAttributeContains(By locator, String attribute, String value) {
        System.out.println("  → Esperando atributo '" + attribute + "' contenga '" + value + "' en: " + locator);
        return wait.until(ExpectedConditions.attributeContains(locator, attribute, value));
    }
    
    /**
     * Espera hasta que un elemento esté PRESENTE en el DOM
     * 
     * @param locator - By localizador del elemento
     * @return WebElement - el elemento una vez que está presente
     * 
     * DIFERENCIA CON visibilityOfElementLocated:
     * - presenceOfElementLocated: solo verifica que existe en el DOM (puede estar oculto)
     * - visibilityOfElementLocated: verifica que existe Y es visible
     * 
     * CUÁNDO USAR presenceOfElementLocated:
     * - Cuando necesitas verificar que el elemento existe, aunque esté oculto
     * - Para elementos que manipularás con JavaScript
     * - Como paso previo antes de verificar visibilidad
     */
    protected WebElement waitForElementToBePresent(By locator) {
        System.out.println("  → Esperando que esté presente en el DOM: " + locator);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    // ==========================================
    // MÉTODOS DE INTERACCIÓN CON ESPERAS
    // ==========================================
    
    /**
     * Click seguro con espera explícita
     * Espera a que el elemento sea clickeable antes de hacer click
     * 
     * @param locator - By localizador del elemento
     * 
     * VENTAJA:
     * Evita ElementNotInteractableException y ElementClickInterceptedException
     */
    protected void clickElement(By locator) {
        System.out.println("→ Click en: " + locator);
        waitForElementToBeClickable(locator).click();
    }
    
    /**
     * Escribir texto con espera explícita
     * Espera a que el elemento sea visible antes de escribir
     * 
     * @param locator - By localizador del elemento
     * @param text - texto a escribir
     */
    protected void typeText(By locator, String text) {
        System.out.println("→ Escribiendo '" + text + "' en: " + locator);
        WebElement element = waitForElementToBeVisible(locator);
        element.clear(); // Limpiar campo antes de escribir
        element.sendKeys(text);
    }
    
    /**
     * Obtener texto de un elemento con espera explícita
     * Espera a que el elemento sea visible antes de obtener su texto
     * 
     * @param locator - By localizador del elemento
     * @return String - texto del elemento
     */
    protected String getText(By locator) {
        System.out.println("→ Obteniendo texto de: " + locator);
        return waitForElementToBeVisible(locator).getText();
    }
    
    /**
     * Verificar si un elemento es visible
     * 
     * @param locator - By localizador del elemento
     * @return boolean - true si el elemento es visible
     */
    protected boolean isElementVisible(By locator) {
        try {
            return waitForElementToBeVisible(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
