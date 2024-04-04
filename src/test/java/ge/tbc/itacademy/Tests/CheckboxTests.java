package ge.tbc.itacademy.Tests;

import com.codeborne.selenide.*;
import ge.tbc.itacademy.ReportListener.CustomReportListenerForCheckboxRadioButton;
import ge.tbc.itacademy.TestListener.CustomTestListenerForCheckboxRadioButton;
import ge.tbcitacademy.data.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Listeners({CustomReportListenerForCheckboxRadioButton.class,
        CustomTestListenerForCheckboxRadioButton.class})
public class CheckboxTests {
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
        Configuration.reportsFolder = Constants.CHECKBOXSCREENPATH;
        Configuration.savePageSource = true;
        WebDriverRunner.setWebDriver(driver);
        open();
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }
    @AfterTest
    public void tearDown(){
        driver.close();
    }

    public void unChecker(SelenideElement element){
        if (element.is(checked)){
            element.setSelected(false);
        }
    }

    public void checker(SelenideElement element){
        if (!element.is(checked)){
            element.setSelected(true);
        }
    }

    @Test(description = Constants.RADIOCHECKBOXDESC)
    public void checkBoxTests(){
        open(Constants.CHECKBOXURL);
        ElementsCollection checkboxes = $("#checkboxes").findAll("input[type='checkbox']");
        SelenideElement checkbox1 = checkboxes.get(0);
        SelenideElement checkbox2 = checkboxes.get(1);

        unChecker(checkbox2);
        Assert.assertTrue(checkbox2.isSelected());

        checker(checkbox1);
        Assert.assertFalse(checkbox1.isSelected());
    }
}