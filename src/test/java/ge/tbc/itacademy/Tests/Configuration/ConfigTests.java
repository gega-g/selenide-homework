package ge.tbc.itacademy.Tests.Configuration;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import ge.tbcitacademy.data.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import static com.codeborne.selenide.Selenide.open;

public class ConfigTests {
    protected WebDriver driver;
    @BeforeTest
    @Parameters(Constants.BROWSER)
    public void setUp(@Optional(Constants.CHROME) String browser) {
        switch (browser.toLowerCase()) {
            case Constants.CHROME:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case Constants.FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case Constants.EDGE:
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException(Constants.UNSUPPORTEDBROWSER + browser);
        }
        Configuration.timeout = 10000;
        Configuration.reportsFolder = Constants.RADIOSCREENSPATH;
        Configuration.savePageSource = true;
        WebDriverRunner.setWebDriver(driver);
        open();
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }
    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}