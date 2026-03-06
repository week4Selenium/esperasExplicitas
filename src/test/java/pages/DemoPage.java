package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DemoPage extends BasePage {
    
    private final By btnLoadContent = By.id("btnLoadContent");
    private final By loadingIndicator = By.id("loadingIndicator");
    private final By btnProcessData = By.id("btnProcessData");
    private final By processResult = By.id("processResult");
    
    private final By inputUsername = By.id("username");
    private final By btnSubmit = By.id("btnSubmit");
    private final By submitResult = By.id("submitResult");
    
    private final By btnUpdateStatus = By.id("btnUpdateStatus");
    private final By statusText = By.id("statusText");
    private final By btnShowAlert = By.id("btnShowAlert");
    private final By temporaryAlert = By.id("temporaryAlert");
    private final By btnAfterAlert = By.id("btnAfterAlert");
    private final By afterAlertResult = By.id("afterAlertResult");
    
    public DemoPage(WebDriver driver) {
        super(driver);
    }
    
  
    public void clickLoadContent() {
        System.out.println("\n>>> ESCENARIO 1: Carga Asíncrona <<<");
        clickElement(btnLoadContent);
    }
    
    public boolean waitForLoadingToDisappear() {
        System.out.println("  → Esperando que desaparezca el indicador de carga...");
        return waitForElementToBeInvisible(loadingIndicator);
    }
    
    public void clickProcessData() {
        System.out.println("  → Esperando que aparezca el botón 'Procesar Datos'...");
        clickElement(btnProcessData);
    }
    
    public boolean isProcessResultVisible() {
        return isElementVisible(processResult);
    }
    
    public void enterUsername(String username) {
        System.out.println("\n>>> ESCENARIO 2: Botón que se Habilita <<<");
        typeText(inputUsername, username);
        System.out.println("  → Validación asíncrona iniciada (2 segundos)...");
    }
    
    public void clickSubmitButton() {
        System.out.println("  → Esperando que el botón se habilite...");
        clickElement(btnSubmit);
    }
    
    public boolean isSubmitResultVisible() {
        return isElementVisible(submitResult);
    }
    
    public void clickUpdateStatus() {
        System.out.println("\n>>> ESCENARIO 3: Texto que Cambia Dinámicamente <<<");
        clickElement(btnUpdateStatus);
    }
    
    public boolean waitForStatusText(String expectedText) {
        System.out.println("  → Esperando estado: '" + expectedText + "'...");
        return waitForTextToBePresentInElement(statusText, expectedText);
    }
    
    public String getStatusText() {
        return getText(statusText);
    }

    public void clickShowAlert() {
        System.out.println("\n>>> ESCENARIO 4: Overlay que Desaparece <<<");
        clickElement(btnShowAlert);
    }
    
    public boolean waitForAlertToDisappear() {
        System.out.println("  → Esperando que desaparezca la alerta temporal...");
        return waitForElementToBeInvisible(temporaryAlert);
    }
    
    public void clickAfterAlertButton() {
        System.out.println("  → Esperando que aparezca el botón 'Acción Después de Alerta'...");
        clickElement(btnAfterAlert);
    }
    
    public boolean isAfterAlertResultVisible() {
        return isElementVisible(afterAlertResult);
    }
    
    public void navigateTo(String url) {
        driver.get(url);
        System.out.println("\n==============================================");
        System.out.println("  Página cargada: Demo - Esperas Explícitas");
        System.out.println("==============================================");
    }
}
