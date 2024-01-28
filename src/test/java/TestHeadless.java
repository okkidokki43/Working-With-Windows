import driverfactory.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tools.WaitTools;

public class TestHeadless {
    private String notheadUrl = System.getProperty("nothead.url");
    private WebDriver driver;
    private WaitTools waitTools;

    @BeforeEach
    public void init() {
        driver = new DriverFactory("--headless").create();
        waitTools = new WaitTools(driver);
        driver.get(notheadUrl);
    }
    @AfterEach
    public void stopDriver() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }
    @Test
    public void headlessChrome() {
        driver.findElement(By.cssSelector("input[aria-autocomplete]"))
                .sendKeys("ОТУС" + Keys.ENTER);
        WebElement oneResalt = driver.findElement(By.xpath
                ("//span[text()='Онлайн‑курсы для профессионалов," +
                        " дистанционное обучение современным ...']"));
        waitTools.waitForCondition(ExpectedConditions.stalenessOf(oneResalt));
        String factResalt = oneResalt.getText();
        Assertions.assertEquals("Онлайн‑курсы для профессионалов," +
                " дистанционное обучение современным ...", factResalt);
    }
}