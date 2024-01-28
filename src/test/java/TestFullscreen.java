import driverfactory.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tools.WaitTools;

public class TestFullscreen {
    private String authlogsUrl = System.getProperty("authlogs.url");
    private String login = System.getProperty("login");
    private String password = System.getProperty("password");
    private final Logger logger = LogManager.getLogger(TestFullscreen.class);
    private WebDriver driver;
    private WaitTools waitTools;

    @BeforeEach
    public void init () {
        driver = new DriverFactory("--start-maximized").create();
        waitTools = new WaitTools(driver);
        driver.get(authlogsUrl);
        logger.info("Start driver");
    }
    @AfterEach
    public void stopDriver() {
        if(driver != null) {
            driver.close();
            driver.quit();
        }
        logger.info("Stop driver");
    }

    @Test
    public void Testauthlogs() {
        String enterLocator = "//button[text()='Войти']";
        waitTools.waitForCondition(ExpectedConditions.presenceOfElementLocated
                (By.xpath(enterLocator)));
        waitTools.waitForCondition(ExpectedConditions.elementToBeClickable
                (By.xpath(enterLocator)));

        WebElement enterButton = driver.findElement(By.xpath(enterLocator));
        enterButton.click();

        driver.findElement(By.xpath("//div[./input[@name='email']]")).click();
        WebElement mailClick = driver.findElement(By.xpath("//input[@name='email']"));
        waitTools.waitForCondition(ExpectedConditions.stalenessOf
                (mailClick));
        mailClick.sendKeys(login);

        driver.findElement(By.xpath("//div[./input[@type='password']]")).click();
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys
                (password);

        driver.findElement(By.cssSelector("#__PORTAL__ button")).click();

        Assertions.assertTrue(waitTools.waitForCondition(ExpectedConditions.invisibilityOf
                (enterButton)));

        logger.info(driver.manage().getCookies().toString());
    }
}