package ge.tbc.itacademy.Tests.SwoopTests;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ge.tbc.itacademy.Tests.Configuration.ConfigTests;
import ge.tbcitacademy.data.Constants;
import org.testng.Assert;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selenide.*;

public class ParametrizedSwoopTests2 extends ConfigTests {
    private int index;
    public ParametrizedSwoopTests2(int index) {
        this.index = index;
    }

    @Test
    public void factoryTest(){
        open(Constants.SWOOPURL);
        SelenideElement TBC = $(byAttribute("href", "/BNPL"));
        ElementsCollection options = $$(".MoreCategories");

        if (index >= 0 && index < options.size()) {
            options.get(index).click();
            Assert.assertTrue(TBC.is(visible));
        }
    }

    @Factory
    public static Object[] factoryExecutor() {
        return new Object[]{
                new ParametrizedSwoopTests2(0),
                new ParametrizedSwoopTests2(1),
                new ParametrizedSwoopTests2(2),
                new ParametrizedSwoopTests2(3),
                new ParametrizedSwoopTests2(4),
                new ParametrizedSwoopTests2(5),
                new ParametrizedSwoopTests2(6),
        };
    }
}
