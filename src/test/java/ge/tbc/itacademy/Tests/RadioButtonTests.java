package ge.tbc.itacademy.Tests;

import com.codeborne.selenide.*;
import ge.tbc.itacademy.ReportListener.CustomReportListenerForCheckboxRadioButton;
import ge.tbc.itacademy.TestListener.CustomTestListenerForCheckboxRadioButton;
import ge.tbcitacademy.data.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

@Listeners({CustomReportListenerForCheckboxRadioButton.class,
        CustomTestListenerForCheckboxRadioButton.class})
public class RadioButtonTests {
    WebDriver driver;

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
    public void checkYes(SelenideElement yes){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(Constants.JSEXECUTOR, yes);
        yes.click();
    }
    @AfterTest
    public void tearDown(){
        driver.close();
    }

    @Test(description = Constants.RADIOCHECKBOXDESC)
    public void radioButtonTests(){
        open(Constants.RADIOBUTTONURL);
        SelenideElement yes = $x("//label[@for='yesRadio']");
        checkYes(yes);
        Assert.assertTrue($(".mt-3").has(exactText(Constants.SELECTEDNO)));
    }
}
