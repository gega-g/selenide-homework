package ge.tbcitacademy.DataProvider;

import org.testng.annotations.DataProvider;

import java.util.List;


public class SalesValueDataProvider extends SalesValueData{
    @DataProvider()
    public static Object[][] dataSupply(){
        SalesValueData salesValueData = new SalesValueData();
        List<Integer> realtimePrices = salesValueData.numericValue(salesValueData.getRealtimePrices());
        List<Integer> startPrices = salesValueData.numericValue(salesValueData.getStartPrices());
        List<Integer> percentages = salesValueData.numericValue(salesValueData.percentages());

        Object[][] data = new Object[10][3];

        for (int i = 0; i < 10; i++) {
            data[i][0] = realtimePrices.get(i);
            data[i][1] = startPrices.get(i);
            data[i][2] = percentages.get(i);
        }
        return data;
    }
}
