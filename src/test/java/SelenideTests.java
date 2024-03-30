import com.codeborne.selenide.*;
import ge.tbcitacademy.data.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class SelenideTests {
    @BeforeClass
    @Parameters(Constants.BROWSER)
    public void setUp(String browser) {
        switch (browser.toLowerCase()) {
            case Constants.CHROME:
                WebDriverManager.chromedriver().setup();
                break;
            case Constants.FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                break;
            default:
                throw new IllegalArgumentException(Constants.UNSUPPORTEDBROWSER + browser);
        }
    }

//    public List<List<String>> getTableData() {
//        List<List<String>> tableData = new ArrayList<>();
//        SelenideElement table = $(".PricingTable-Wrap");
//        ElementsCollection rows = table.findAll("tr");
//        for (SelenideElement row : rows) {
//            ElementsCollection columns = row.findAll("td");
//            List<String> rowElements = new ArrayList<>();
//            for (SelenideElement column : columns) {
//                rowElements.add(column.getText());
//            }
//            tableData.add(rowElements);
//        }
//        return tableData;
//    }
//    public List<String> getRow(String feature) {
//        List<List<String>> tableData = getTableData();
//        for (List<String> row : tableData) {
//            for (String element : row) {
//                if (element.contains(feature)) {
//                    return row;
//                }
//            }
//        }
//        return null;
//    }

    public void navigateToPricing(){
        open(Constants.TELERIKURL);
        WebDriverRunner.getWebDriver().manage().window().maximize();
        SelenideElement pricing = $(withText(Constants.PRICING));
        pricing.click();
    }


    @Test
    public void validateBundleOffers(){
        navigateToPricing();
        SelenideElement details = $x("//span[@class='action']");
        executeJavaScript("arguments[0].scrollIntoView({block: 'center'});", details);
        details.click();

        SelenideElement UI = $(".UI.is-active");
        UI.shouldNotHave(text(Constants.MOCKINGSOLUTION));

//        ვერ მივხვდი როგორ უნდა გამეკეთებინა დანარჩენები:/

        ElementsCollection offerNames = $$(".Text--b9.u-fs24.u-tac.u-mb0.js-product");
        executeJavaScript("window.scrollTo(0, document.body.scrollHeight)");
        for (SelenideElement name : offerNames) {
            name.shouldBe(visible);
        }
    }


    @Test
    public void validateIndividualOffers(){
        navigateToPricing();
        $(withText(Constants.INDIVIDUALPRODUCTS)).click();
        SelenideElement kendoUI = $x("//div[@data-opti-expid='Kendo UI']");
        SelenideElement kendoReact = $x("//div[@data-opti-expid='KendoReact']")
                .hover();

        Assert.assertTrue($x("//div[@class='Box-ninja u-pt1 u-l-dn']").isDisplayed());
        $x("//a[@href='/kendo-ui']/preceding-sibling::div[@data-tlrk-plugin='dropdown']")
                .shouldHave(text(Constants.PRIORITYSUPPORT), visible);
        $x("//a[@href='/kendo-react-ui/#pricing']/preceding-sibling::div[@data-tlrk-plugin='dropdown']")
                .shouldHave(text(Constants.PRIORITYSUPPORT), visible);

        Assert.assertEquals(kendoUI.$((".js-price")).text(), Constants.UIPRICE);
        Assert.assertEquals(kendoReact.$((".js-price")).text(), Constants.REACTPRICE);

    }

    @Test
    public void checkBoxTest() {
        open(Constants.CHECKBOXURL);
        WebDriverRunner.getWebDriver().manage().window().maximize();
        ElementsCollection checkboxes = $("#checkboxes").findAll("input[type='checkbox']");
        SelenideElement checkbox1 = checkboxes.get(0);
        SelenideElement checkbox2 = checkboxes.get(1);
        checkbox1.click();
        checkbox1.shouldBe(checked);
        checkbox1.shouldHave(attribute(Constants.TYPE, Constants.CHECKBOX));
        checkbox2.shouldHave(attribute(Constants.TYPE, Constants.CHECKBOX));
    }

    @Test
    public void dropDownTest() {
        open(Constants.DROPDOWNURL);
        WebDriverRunner.getWebDriver().manage().window().maximize();
        SelenideElement dropdown = $("#dropdown");
        SelenideElement option = $x("//option[text()='Please select an option']");
        option.shouldBe(selected);
        dropdown.selectOption(Constants.OPT2);
        dropdown.getSelectedOption().shouldHave(text(Constants.OPT2), value(Constants.TWO));
    }

    @Test
    public void collectionsTest() {
        open(Constants.COLLECTIONSURL);
        WebDriverRunner.getWebDriver().manage().window().maximize();

        SelenideElement name = $(".mr-sm-2.form-control")
                .shouldHave(attribute(Constants.PLACEHOLDER, Constants.FULLNAME))
                .setValue(Constants.FULLNAME);

        SelenideElement email = $x("//input[@placeholder='name@example.com']")
                .setValue(Constants.MAIL);

        SelenideElement currentAddress = $("[class='form-control']")
                .shouldHave(attribute(Constants.PLACEHOLDER, Constants.CURRENTADDRESS))
                .setValue(Constants.CURRENTADDRESS);

        SelenideElement permanentAddress = $("#permanentAddress")
                .setValue(Constants.PERMANENTADDRESS);

        List<SelenideElement> inputFields = Arrays.asList(name, email, currentAddress, permanentAddress);
        for (SelenideElement field : inputFields) {
            field.shouldBe(Condition.visible);
        }
    }
}