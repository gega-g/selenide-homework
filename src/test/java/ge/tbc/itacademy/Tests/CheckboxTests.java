package ge.tbc.itacademy.Tests;

import com.codeborne.selenide.*;
import ge.tbcitacademy.ReportListener.CustomReportListenerForCheckboxRadioButton;
import ge.tbcitacademy.TestListener.CustomTestListenerForCheckboxRadioButton;
import ge.tbcitacademy.data.Constants;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Listeners({CustomReportListenerForCheckboxRadioButton.class,
        CustomTestListenerForCheckboxRadioButton.class})
public class CheckboxTests extends ConfigTests {
    WebDriver driver;
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

    @Test(description = Constants.RADIOCHECKBOXDESC, groups = {"checkBoxTests"})
    public void checkBoxTests(){
        open(Constants.CHECKBOXURL);
        ElementsCollection checkboxes = $("#checkboxes").findAll("input[type='checkbox']");
        SelenideElement checkbox1 = checkboxes.get(0);
        SelenideElement checkbox2 = checkboxes.get(1);

        unChecker(checkbox2);
        Assert.assertTrue(checkbox2.isSelected());

        checker(checkbox1);
        Assert.assertFalse(checkbox1.isSelected());
        System.out.println("smth");
    }
}