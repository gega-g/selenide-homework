package ge.tbc.itacademy.Tests;

import com.codeborne.selenide.*;
import ge.tbcitacademy.ReportListener.CustomReportListenerForCheckboxRadioButton;
import ge.tbcitacademy.Retry.IRetry;
import ge.tbcitacademy.Retry.RetryAnalyzer;
import ge.tbcitacademy.TestListener.CustomTestListenerForCheckboxRadioButton;
import ge.tbcitacademy.data.Constants;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.*;

import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

@Listeners({CustomReportListenerForCheckboxRadioButton.class,
        CustomTestListenerForCheckboxRadioButton.class})
public class RadioButtonTests extends ConfigTests{

    public void checkYes(SelenideElement yes){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(Constants.JSEXECUTOR, yes);
        yes.click();
    }

    @Test(description = Constants.RADIOCHECKBOXDESC,priority = 1, groups = {"radioButtonTests"})
    public void radioButtonTests(){
        open(Constants.RADIOBUTTONURL);
        SelenideElement yes = $x("//label[@for='yesRadio']");
        checkYes(yes);
        Assert.assertTrue($(".mt-3").has(exactText(Constants.SELECTEDNO)));
    }

    @IRetry(count = 5)
    @Test(description = Constants.FAIL5TIMES , priority = 2, retryAnalyzer = RetryAnalyzer.class)
    public void falseInfo(){
        Assert.assertEquals(Constants.MESSI, Constants.GOAT);
    }
}
