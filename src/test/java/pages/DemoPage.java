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
        clickElement(btnLoadContent);
    }
    
    public boolean waitForLoadingToDisappear() {
        return waitForElementToBeInvisible(loadingIndicator);
    }
    
    public void clickProcessData() {
        clickElement(btnProcessData);
    }
    
    public boolean isProcessResultVisible() {
        return isElementVisible(processResult);
    }
    
    public void enterUsername(String username) {
        typeText(inputUsername, username);
    }
    
    public void clickSubmitButton() {
        clickElement(btnSubmit);
    }
    
    public boolean isSubmitResultVisible() {
        return isElementVisible(submitResult);
    }
    
    public void clickUpdateStatus() {
        clickElement(btnUpdateStatus);
    }
    
    public boolean waitForStatusText(String expectedText) {
        return waitForTextToBePresentInElement(statusText, expectedText);
    }
    
    public String getStatusText() {
        return getText(statusText);
    }

    public void clickShowAlert() {
        clickElement(btnShowAlert);
    }
    
    public boolean waitForAlertToDisappear() {
        return waitForElementToBeInvisible(temporaryAlert);
    }
    
    public void clickAfterAlertButton() {
        clickElement(btnAfterAlert);
    }
    
    public boolean isAfterAlertResultVisible() {
        return isElementVisible(afterAlertResult);
    }
    
    public void navigateTo(String url) {
        driver.get(url);
    }
}
