package ge.tbc.itacademy.Tests.SwoopTests;

import ge.tbc.itacademy.Tests.Configuration.ConfigTests;
import ge.tbcitacademy.DataProvider.SalesValueDataProvider;
import ge.tbcitacademy.data.Constants;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static com.codeborne.selenide.Selectors.by;
import static com.codeborne.selenide.Selenide.*;

public class ParametrizedSwoopTests extends ConfigTests {
    @Test(dataProvider = "dataSupply", dataProviderClass = SalesValueDataProvider.class)
    public void checkSaleValuesTest(List<Integer> realtimePrices,
                                    List<Integer> startPrices,
                                    List<Integer> percentages){
        open(Constants.SWOOPURL);
        $x("//div[@class='Menus']").
                $(by("href", "/category/110/sporti"))
                .click();
        for (int i=0; i<10; i++)
            Assert.assertEquals(realtimePrices.get(i).intValue(), (startPrices.get(i) * percentages.get(i) / 100));
    }

}