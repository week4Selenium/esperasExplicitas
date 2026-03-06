package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.File;


public class BaseTest {
    protected WebDriver driver;
    private ChromeDriverService service;
    protected String pageUrl;
    protected static final int DEMO_DELAY = 1500;
    
    @BeforeEach
    public void setUp() {
        try {
            WebDriverManager.chromedriver().setup();
            service = new ChromeDriverService.Builder()
                .usingAnyFreePort()  
                .build();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-dev-shm-usage");  
            options.addArguments("--no-sandbox"); 
            options.addArguments("--remote-debugging-port=0"); 
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            options.setExperimentalOption("useAutomationExtension", false);
            
            driver = new ChromeDriver(service, options);
            driver.manage().window().maximize();
            
            File htmlFile = new File("src/test/resources/demo-page.html");
            pageUrl = "file:///" + htmlFile.getAbsolutePath().replace("\\", "/");
            
        } catch (Exception e) {
            System.err.println("âŒ Error en setUp: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
   
    @AfterEach
    public void tearDown() {
        try {
            if (driver != null) {
                try {
                    driver.quit(); 
                } catch (Exception e) {
                    System.err.println("âš ï¸ Error cerrando driver: " + e.getMessage());
                }
            }
            
            if (service != null && service.isRunning()) {
                service.stop();
            }
            
        } catch (Exception e) {
            System.err.println("âš ï¸ Error en tearDown: " + e.getMessage());
        } finally {
            driver = null;
            service = null;
        }
    }
    
    protected void openTestPage() {
        driver.get(pageUrl);
    }
    
    protected void pauseForDemo(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    protected void pauseForDemo() {
        pauseForDemo(DEMO_DELAY);
    }
}
