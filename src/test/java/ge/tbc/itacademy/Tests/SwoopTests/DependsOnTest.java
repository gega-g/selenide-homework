package ge.tbc.itacademy.Tests.SwoopTests;

import com.codeborne.selenide.*;
import ge.tbc.itacademy.Tests.Configuration.ConfigTests;
import ge.tbcitacademy.data.Constants;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;

public class DependsOnTest extends ConfigTests {
    @Test(priority = 0)
    public void searchTest() {
        open(Constants.SWOOPURL);
        SelenideElement inputField = $(".reheadersearch");
        inputField.sendKeys(Constants.BURGER);
        inputField.pressEnter();

        ElementsCollection titlesWithBurger = $$x("//div[@class='special-offer-title']//p[@title[contains(., 'ბურგერ')]]");
        ElementsCollection linksWithBurgerText = $$x("//div[@class='special-offer-text']//a[contains(., 'ბურგერ')]");
        boolean isBurgerFound = false;

        for (int i = 0; i < titlesWithBurger.size(); i++) {
            if (titlesWithBurger.get(i).getAttribute(Constants.TITLE).contains(Constants.BURGER)) {
                isBurgerFound = true;
                break;
            }
        }

        if (!isBurgerFound) {
            for (int i = 0; i < linksWithBurgerText.size(); i++) {
                if (linksWithBurgerText.get(i).getText().contains(Constants.BURGER)) {
                    isBurgerFound = true;
                    break;
                }
            }
        }

        Configuration.assertionMode = AssertionMode.SOFT;
        Assert.assertTrue(isBurgerFound);
    }

    @Test(priority = 1, dependsOnMethods = "searchTest", alwaysRun = true)
    public void validateIndividualOfferNameFromSearch(){
        $x("//div[@class='special-offer']").click();
        Assert.assertTrue($(".merchantTitle").has(Condition.text(Constants.BURGER)));
    }
}
