import com.codeborne.selenide.*;
import ge.tbcitacademy.data.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;


import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


public class SelenideTests2 {

    SoftAssert sfa;
    WebDriver driver;

    @BeforeClass
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
            default:
                throw new IllegalArgumentException(Constants.UNSUPPORTEDBROWSER + browser);
        }
        Configuration.assertionMode = AssertionMode.SOFT;
        sfa = new SoftAssert();
        WebDriverRunner.setWebDriver(driver);
        open();
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }
    @AfterClass
    public void getDown(){
        driver.close();
    }

    @Test
    public void validateDemosDesign() {
        open(Constants.TELERIKURL);
        SelenideElement web = $(byAttribute(Constants.HREF, Constants.WEB));
        web.click();
        ElementsCollection webPics = $$x("//h2[@id='web']/following:" +
                ":div[@class='HoverImg u-mb1'][following::h2[@id='desktop']]");
        for (SelenideElement pic : webPics) {
            actions().moveToElement(pic).perform();
            sfa.assertTrue(pic.has(attribute(Constants.CLASS, Constants.PURPLE)), Constants.HOVERERRORMESSAGE);
        }
        sfa.assertTrue(webPics.get(0).hover().innerHtml().contains(Constants.UICONDITION), Constants.UIERROR);

        SelenideElement desktop = $(byAttribute(Constants.HREF, Constants.DESKTOP));
        desktop.click();
        ElementsCollection desktopElements = $$x("//h2[@id='desktop']/following:" +
                ":div[contains(@data-placeholder-label, 'Column')][following::h2[@id='mobile']]");
        for (SelenideElement element : desktopElements) {
            if (element.innerHtml().contains(Constants.MICROSOFT)) {
                System.out.println(element.find(".h4").getText() + Constants.WITHMICROSOFT);
            }
        }

        SelenideElement mobile = $(byAttribute(Constants.HREF, Constants.MOBILE));
        mobile.click();
        SelenideElement xamarin = $(By.id(Constants.XAMARINID));
        Assert.assertTrue(
                xamarin.innerHtml().contains(Constants.MICROSOFT2) &&
                        xamarin.innerHtml().contains(Constants.APPLE) &&
                        xamarin.innerHtml().contains(Constants.GOOGLE));


        SelenideElement selectionBar = $x("//nav[@data-tlrk-plugin='fixit']");
        Assert.assertTrue(selectionBar.isDisplayed());

        ElementsCollection elementsOnSelectionBar = $$(".NavAlt-anchor.u-b0");
        for (SelenideElement element : elementsOnSelectionBar) {
            element.click();
            ElementsCollection h2Elements = $$("h2");

            for (int i = 1; i < h2Elements.size(); i++) {
                String h2 = h2Elements.get(i).getText();

                if (h2.equals(element.getText())) {
                    Assert.assertEquals(h2, element.getText());
                    break;
                }
            }
        }

        sfa.assertAll();
    }

    public double getTotalPrice() {
        String tp = $(".u-fr.e2e-total-price").getText();
        return Double.parseDouble(tp
                .replace("$", "")
                .replace(",", "")
                .replace("US ", ""));
    }

    public double getUnitPrice() {
        String up = $("span.e2e-price-per-license.ng-star-inserted").getText();
        return Double.parseDouble(up
                .replace("$", "")
                .replace(",", ""));
    }
    public double getDiscountAmounts(SelenideElement element){
        String text = element.getText();
        return Double.parseDouble(text
                .replace("$", "")
                .replace("-", "")
                .replace(",", ""));
    }


    @Test
    public void validateOrderMechanics() throws InterruptedException {
        open(Constants.TELERIKURL);
        $(withText(Constants.PRICING)).click();
        SelenideElement cookies = $(byId(Constants.ONETRUST));
        cookies.click();
        $x("//th[@class='Complete']//a[contains(text(), 'Buy now')]").click();
        $(".far.fa-times.label.u-cp").click();


        Assert.assertEquals(getUnitPrice(), getTotalPrice());

        $("span.k-input").click();
        $(byText(Constants.TWO)).click();

//        should და shouldBe-ს ვერ ვიყენებ ვერსად და ვერგავიგე რატომ. მაგისგამო მიწერია სლიფი რაღაცით უნდა შემეჩერებინა ცოტახნით
//        $x("//div[contains(text(), 'Save')]").shouldBe(visible);
        Thread.sleep(1000);
        double totalPriceAfterDiscount = getTotalPrice();
        double expectedTotalPrice = getUnitPrice() * 2;
        Assert.assertEquals(expectedTotalPrice, totalPriceAfterDiscount);

        double discountNotOnHover = getDiscountAmounts($(".u-fr.e2e-total-discounts-price"));
        $("[class='far fa-question-circle tooltip-icon']").hover();
        double discountOnHover = getDiscountAmounts($(".u-pr5.e2e-licenses-discounts"));
        Assert.assertEquals(discountOnHover,discountNotOnHover);

        $(".btn-content.ng-star-inserted").scrollTo().click();
        cookies.click();
        $(".k-icon.k-i-arrow-s").click();
        $(byText(Constants.AFGHANISTAN)).click();
        ElementsCollection fillable = $$x("//input[@type='text']");
        for (SelenideElement element : fillable) {
            if (element.getAttribute(Constants.ID).equals(Constants.BIEMAIL)) {
                element.sendKeys(Constants.MAIL);
            } else {
                    element.scrollTo();
                    element.sendKeys(Constants.FULLNAME);
            }
        }
        driver.navigate().back();
        driver.navigate().forward();
        for (SelenideElement element : fillable){
            sfa.assertTrue(element.has(exactText(Constants.FULLNAME)));
        }
        sfa.assertAll();
    }

    @Test
    public void chainedLocatorsTest() {
        open(Constants.DEMOQAURL);
        ElementsCollection books = $$(".rt-tr-group");

        for (SelenideElement bookRow : books) {
            String title = bookRow.$(".rt-td:nth-child(2)").text();
            String publisher = bookRow.$(".rt-td:nth-child(4)").text();

            if (title.contains(Constants.JS) && publisher.equals(Constants.MEDIA)) {
                sfa.assertTrue(bookRow.$(".rt-td:nth-child(1)").isDisplayed());
                System.out.println(title);
            }
        }
    }

    @Test
    public void softAssertTest() {
        open(Constants.DEMOQAURL);
        SelenideElement table = $(".rt-table").find(".rt-tbody");
        ElementsCollection books = table
                .findAll(By.xpath(".//div[contains(@class, 'rt-tr') " +
                        "and not(contains(@class, '-odd')) " +
                        "and not(contains(@class, '-even'))]"));

        List<SelenideElement> correctBooks = new ArrayList<>();
        for (SelenideElement book:books){
            String title = book.find(".rt-td:nth-child(2)").getText();
            String publisher = book.find(".rt-td:nth-child(4)").getText();
            if (title.contains(Constants.JS) && publisher.equals(Constants.MEDIA)) {
                correctBooks.add(book);
            }
        }
        List<String> bookTitles = new ArrayList<>();
        for (SelenideElement book : correctBooks) {
            String title = book.find(".rt-td:nth-child(2)").getText();
            bookTitles.add(title);
        }
        sfa.assertEquals(correctBooks.size(), 10);
        sfa.assertEquals(Constants.TITLETOEQUAL, bookTitles.get(0));
        sfa.assertAll();

        System.out.println("ERRORERRORERROR");
    }
}
