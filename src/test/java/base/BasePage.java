package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final int DEFAULT_WAIT_TIMEOUT = 10;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIMEOUT));
    }
    
    protected WebElement waitForElementToBeVisible(By locator) {
        System.out.println("  → Esperando que sea visible: " + locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    protected WebElement waitForElementToBeClickable(By locator) {
        System.out.println("  → Esperando que sea clickeable: " + locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    protected boolean waitForTextToBePresentInElement(By locator, String expectedText) {
        System.out.println("  → Esperando texto '" + expectedText + "' en: " + locator);
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, expectedText));
    }
    
    protected boolean waitForElementToBeInvisible(By locator) {
        System.out.println("  → Esperando que desaparezca: " + locator);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
    protected void clickElement(By locator) {
        System.out.println("→ Click en: " + locator);
        waitForElementToBeClickable(locator).click();
    }
    
    protected void typeText(By locator, String text) {
        System.out.println("→ Escribiendo '" + text + "' en: " + locator);
        WebElement element = waitForElementToBeVisible(locator);
        element.clear(); 
        element.sendKeys(text);
    }
    
    protected String getText(By locator) {
        System.out.println("→ Obteniendo texto de: " + locator);
        return waitForElementToBeVisible(locator).getText();
    }
    
    protected boolean isElementVisible(By locator) {
        try {
            return waitForElementToBeVisible(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
