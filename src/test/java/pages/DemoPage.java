package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object para la página de demostración de Esperas Explícitas
 * 
 * PATRÓN: Page Object Model (POM)
 * 
 * PROPÓSITO:
 * Esta clase representa la página "demo-page.html" y encapsula:
 * - Todos los localizadores de elementos (By)
 * - Todas las acciones que se pueden realizar en la página
 * - Todas las validaciones relacionadas con la página
 * 
 * PRINCIPIO DE RESPONSABILIDAD ÚNICA:
 * Esta clase solo se encarga de:
 * 1. Definir DÓNDE están los elementos (localizadores)
 * 2. Definir QUÉ acciones se pueden hacer (métodos de interacción)
 * 
 * Los tests (en la capa de tests) se encargan de:
 * - Definir la secuencia de acciones (el flujo del test)
 * - Realizar las aserciones (verificaciones)
 * 
 * VENTAJA PRINCIPAL:
 * Si cambia el HTML (ej: un ID de botón), solo modificamos esta clase
 * NO necesitamos modificar ningún test
 */
public class DemoPage extends BasePage {
    
    // ==========================================
    // LOCALIZADORES - ESCENARIO 1: Elemento que Aparece Después de Carga Asíncrona
    // ==========================================
    
    /**
     * Botón que inicia la carga de contenido asíncrono
     * Este botón dispara una función JavaScript que simula un delay de 3 segundos
     */
    private final By btnLoadContent = By.id("btnLoadContent");
    
    /**
     * Indicador de carga (spinner animado)
     * Este elemento aparece mientras se "carga" el contenido
     * Importante: Debe desaparecer antes de que aparezca el contenido
     */
    private final By loadingIndicator = By.id("loadingIndicator");
    
    /**
     * Botón que aparece DESPUÉS de 3 segundos
     * Este es el caso CRÍTICO que demuestra la necesidad de esperas explícitas
     * 
     * SIN esperas explícitas: NoSuchElementException
     * CON esperas explícitas: El test espera hasta que aparezca
     */
    private final By btnProcessData = By.id("btnProcessData");
    
    /**
     * Mensaje de resultado después de procesar datos
     * Aparece después de hacer click en "Procesar Datos"
     */
    private final By processResult = By.id("processResult");
    
    // ==========================================
    // LOCALIZADORES - ESCENARIO 2: Elemento que se Vuelve Clickeable
    // ==========================================
    
    /**
     * Campo de texto para nombre de usuario
     * Al escribir aquí, se dispara una validación asíncrona
     */
    private final By inputUsername = By.id("username");
    
    /**
     * Botón de envío de formulario
     * Inicialmente está DESHABILITADO (disabled)
     * Se HABILITA después de 2 segundos de escribir en el campo
     * 
     * Este es el escenario perfecto para: elementToBeClickable()
     * 
     * SIN esperas: ElementNotInteractableException
     * CON esperas: El test espera hasta que esté enabled
     */
    private final By btnSubmit = By.id("btnSubmit");
    
    /**
     * Mensaje de resultado del formulario
     * Aparece después de enviar el formulario exitosamente
     */
    private final By submitResult = By.id("submitResult");
    
    // ==========================================
    // LOCALIZADORES - ESCENARIO 3: Texto que Cambia Dinámicamente
    // ==========================================
    
    /**
     * Botón que inicia un proceso con múltiples estados
     * El estado cambia varias veces durante la ejecución
     */
    private final By btnUpdateStatus = By.id("btnUpdateStatus");
    
    /**
     * Elemento que muestra el texto del estado actual
     * El texto cambia dinámicamente:
     * 1. "Sistema listo" (inicial)
     * 2. "Iniciando proceso..." (0s)
     * 3. "Procesando datos..." (2s)
     * 4. "Finalizando..." (4s)
     * 5. "Proceso completado exitosamente" (6s)
     * 
     * Este es el escenario perfecto para: textToBePresentInElement()
     * 
     * SIN esperas: Se verifica el texto antes de que cambie
     * CON esperas: El test espera hasta que aparezca el texto esperado
     */
    private final By statusText = By.id("statusText");
    
    // ==========================================
    // LOCALIZADORES - ESCENARIO 4: Elemento que Desaparece
    // ==========================================
    
    /**
     * Botón que muestra una alerta temporal
     * La alerta bloquea la interacción durante 3 segundos
     */
    private final By btnShowAlert = By.id("btnShowAlert");
    
    /**
     * Alerta temporal que se muestra y luego desaparece
     * Simula un modal, overlay o notificación temporal
     * 
     * Este es el escenario perfecto para: invisibilityOfElementLocated()
     * 
     * SIN esperas: ElementClickInterceptedException (el overlay bloquea el click)
     * CON esperas: El test espera hasta que desaparezca la alerta
     */
    private final By temporaryAlert = By.id("temporaryAlert");
    
    /**
     * Botón que aparece DESPUÉS de que desaparece la alerta
     * No se puede hacer click mientras la alerta esté visible
     */
    private final By btnAfterAlert = By.id("btnAfterAlert");
    
    /**
     * Mensaje de resultado de la acción posterior a la alerta
     */
    private final By afterAlertResult = By.id("afterAlertResult");
    
    // ==========================================
    // CONSTRUCTOR
    // ==========================================
    
    /**
     * Constructor que recibe el WebDriver
     * Llama al constructor de BasePage para inicializar driver y wait
     * 
     * @param driver - instancia del WebDriver desde el test
     */
    public DemoPage(WebDriver driver) {
        super(driver);
    }
    
    // ==========================================
    // MÉTODOS DE INTERACCIÓN - ESCENARIO 1
    // ==========================================
    
    /**
     * Hacer click en el botón "Cargar Contenido Remoto"
     * Este método usa espera explícita para asegurar que el botón esté clickeable
     */
    public void clickLoadContent() {
        System.out.println("\n>>> ESCENARIO 1: Carga Asíncrona <<<");
        clickElement(btnLoadContent);
    }
    
    /**
     * Esperar hasta que el indicador de carga sea visible
     * Útil para verificar que la carga efectivamente inició
     * 
     * @return true si el indicador es visible
     */
    public boolean isLoadingIndicatorVisible() {
        return isElementVisible(loadingIndicator);
    }
    
    /**
     * Esperar hasta que el indicador de carga desaparezca
     * IMPORTANTE: Esto debe completarse antes de buscar el botón "Procesar Datos"
     * 
     * @return true si el indicador desapareció
     */
    public boolean waitForLoadingToDisappear() {
        System.out.println("  → Esperando que desaparezca el indicador de carga...");
        return waitForElementToBeInvisible(loadingIndicator);
    }
    
    /**
     * Esperar y hacer click en el botón "Procesar Datos"
     * 
     * ESTE ES EL MÉTODO CRÍTICO QUE DEMUESTRA ESPERAS EXPLÍCITAS
     * 
     * El botón aparece después de 3 segundos
     * SIN esperas explícitas: NoSuchElementException inmediatamente
     * CON esperas explícitas: Espera hasta 10 segundos a que aparezca
     */
    public void clickProcessData() {
        System.out.println("  → Esperando que aparezca el botón 'Procesar Datos'...");
        clickElement(btnProcessData);
    }
    
    /**
     * Verificar que el resultado del procesamiento apareció
     * 
     * @return true si el mensaje de resultado es visible
     */
    public boolean isProcessResultVisible() {
        return isElementVisible(processResult);
    }
    
    // ==========================================
    // MÉTODOS DE INTERACCIÓN - ESCENARIO 2
    // ==========================================
    
    /**
     * Escribir texto en el campo de usuario
     * Esto dispara una validación asíncrona que tarda 2 segundos
     * 
     * @param username - texto a escribir
     */
    public void enterUsername(String username) {
        System.out.println("\n>>> ESCENARIO 2: Botón que se Habilita <<<");
        typeText(inputUsername, username);
        System.out.println("  → Validación asíncrona iniciada (2 segundos)...");
    }
    
    /**
     * Esperar y hacer click en el botón de envío
     * 
     * ESTE MÉTODO DEMUESTRA: elementToBeClickable()
     * 
     * El botón está visible pero DESHABILITADO (disabled)
     * Se habilita después de 2 segundos
     * 
     * SIN esperas: ElementNotInteractableException
     * CON elementToBeClickable(): Espera hasta que esté enabled
     */
    public void clickSubmitButton() {
        System.out.println("  → Esperando que el botón se habilite...");
        clickElement(btnSubmit);
    }
    
    /**
     * Obtener el mensaje de resultado del formulario
     * 
     * @return String - texto del mensaje
     */
    public String getSubmitResultText() {
        return getText(submitResult);
    }
    
    /**
     * Verificar que el resultado del envío apareció
     * 
     * @return true si el mensaje es visible
     */
    public boolean isSubmitResultVisible() {
        return isElementVisible(submitResult);
    }
    
    // ==========================================
    // MÉTODOS DE INTERACCIÓN - ESCENARIO 3
    // ==========================================
    
    /**
     * Hacer click en el botón "Iniciar Proceso"
     * Esto inicia una secuencia de cambios de estado
     */
    public void clickUpdateStatus() {
        System.out.println("\n>>> ESCENARIO 3: Texto que Cambia Dinámicamente <<<");
        clickElement(btnUpdateStatus);
    }
    
    /**
     * Esperar hasta que el texto de estado contenga un texto específico
     * 
     * ESTE MÉTODO DEMUESTRA: textToBePresentInElement()
     * 
     * El estado cambia varias veces:
     * "Sistema listo" → "Iniciando..." → "Procesando..." → "Finalizando..." → "Completado"
     * 
     * SIN esperas: Se verifica el texto antes de que cambie (test flaky)
     * CON textToBePresentInElement(): Espera hasta que aparezca el texto correcto
     * 
     * @param expectedText - texto que esperamos ver
     * @return true si el texto apareció
     */
    public boolean waitForStatusText(String expectedText) {
        System.out.println("  → Esperando estado: '" + expectedText + "'...");
        return waitForTextToBePresentInElement(statusText, expectedText);
    }
    
    /**
     * Obtener el texto actual del estado
     * 
     * @return String - texto del estado
     */
    public String getStatusText() {
        return getText(statusText);
    }
    
    // ==========================================
    // MÉTODOS DE INTERACCIÓN - ESCENARIO 4
    // ==========================================
    
    /**
     * Hacer click en el botón "Mostrar Alerta Temporal"
     * Esto muestra una alerta que desaparece después de 3 segundos
     */
    public void clickShowAlert() {
        System.out.println("\n>>> ESCENARIO 4: Overlay que Desaparece <<<");
        clickElement(btnShowAlert);
    }
    
    /**
     * Verificar que la alerta temporal es visible
     * 
     * @return true si la alerta es visible
     */
    public boolean isTemporaryAlertVisible() {
        return isElementVisible(temporaryAlert);
    }
    
    /**
     * Esperar hasta que la alerta temporal desaparezca
     * 
     * ESTE MÉTODO DEMUESTRA: invisibilityOfElementLocated()
     * 
     * La alerta bloquea la interacción con elementos detrás de ella
     * 
     * SIN esperas: ElementClickInterceptedException (la alerta bloquea el click)
     * CON invisibilityOfElementLocated(): Espera hasta que desaparezca
     * 
     * @return true si la alerta desapareció
     */
    public boolean waitForAlertToDisappear() {
        System.out.println("  → Esperando que desaparezca la alerta temporal...");
        return waitForElementToBeInvisible(temporaryAlert);
    }
    
    /**
     * Hacer click en el botón que aparece después de la alerta
     * Este click solo es posible después de que desaparezca la alerta
     */
    public void clickAfterAlertButton() {
        System.out.println("  → Esperando que aparezca el botón 'Acción Después de Alerta'...");
        clickElement(btnAfterAlert);
    }
    
    /**
     * Verificar que el resultado de la acción posterior apareció
     * 
     * @return true si el mensaje es visible
     */
    public boolean isAfterAlertResultVisible() {
        return isElementVisible(afterAlertResult);
    }
    
    // ==========================================
    // MÉTODOS DE UTILIDAD
    // ==========================================
    
    /**
     * Método para navegar a la página
     * Útil para iniciar el test
     * 
     * @param url - URL de la página HTML local
     */
    public void navigateTo(String url) {
        driver.get(url);
        System.out.println("\n==============================================");
        System.out.println("  Página cargada: Demo - Esperas Explícitas");
        System.out.println("==============================================");
    }
    
    /**
     * Obtener el título de la página
     * Útil para verificar que estamos en la página correcta
     * 
     * @return String - título de la página
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
}
