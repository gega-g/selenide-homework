package ge.tbc.itacademy.Tests.ParameterizationTests;

import ge.tbc.itacademy.Tests.Configuration.ConfigTests;
import ge.tbcitacademy.data.Constants;
import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.testng.AssertJUnit.assertEquals;

public class AlternativeParameterization extends ConfigTests {
//    @Parameters({"firstName", "lastName", "gender", "mobileNumber"})
    @Test(dataProvider = "userData")
    public void parameterTest(String firstName, String lastName, String gender, String mobileNumber) {
        open(Constants.DEMOQAURLAUTO);
        $(By.id(Constants.FIRSTNAME)).scrollTo().sendKeys(firstName);
        $(By.id(Constants.LASTNAME)).sendKeys(lastName);
        $(By.cssSelector("#genterWrapper input[value='" + gender + "']")).sibling(0).click();
        $(By.id(Constants.USERNUMBER)).sendKeys(mobileNumber);

        String enteredFirstName = $(By.id(Constants.FIRSTNAME)).getValue();
        assertEquals(enteredFirstName, firstName);
    }

    @DataProvider()
    public Object[][] userData() {
        return new Object[][]{
                {"firstName1", "lastName1", "Male", "11111111111"},
                {"firstName2", "lastName2", "Female", "22222222222"},
                {"firstName3", "lastName3", "Other", "33333333333"},
        };
    }
}