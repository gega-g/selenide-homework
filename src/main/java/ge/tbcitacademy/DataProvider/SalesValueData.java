package ge.tbcitacademy.DataProvider;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class SalesValueData {
    public int counter = 0;

    public List<Integer> numericValue(List<String> priceElements) {
        List<Integer> prices = new ArrayList<>();
        for (String priceElement : priceElements) {
            String numbersFromText = priceElement.replaceAll("[^\\d\s]", "");
            int finalPrice = Integer.parseInt(numbersFromText);
            prices.add(finalPrice);
        }
        return prices;
    }
    public List<String> getRealtimePrices(){
        List<String> prices = new ArrayList<>();
        ElementsCollection tenRealtimePrices =
                $$x(("//div[@class='discounted-prices']//p[@class='deal-voucher-price' and @style='text-decoration: line-through;']"));
        for (SelenideElement priceElement : tenRealtimePrices) {
            if (counter < 10) {
                prices.add(priceElement.getText());
                counter++;
            } else {
                break;
            }
        }
        return prices;
    }
    public List<String> getStartPrices(){
        List<String> prices = new ArrayList<>();
        ElementsCollection tenStartPrices =
                $$("div[class='discounted-prices'] p.deal-voucher-price:not([style='text-decoration: line-through;'])");
        for (SelenideElement priceElement : tenStartPrices) {
            if (counter < 10) {
                prices.add(priceElement.getText());
                counter++;
            } else {
                break;
            }
        }
        return prices;
    }
    public List<String> percentages(){
        List<String> percentages = new ArrayList<>();
        ElementsCollection tenPercentages = $$x("//div[@class='special-offer-discount-percent']");
        for (SelenideElement priceElement : tenPercentages) {
            if (counter < 10) {
                percentages.add(priceElement.getText());
                counter++;
            } else {
                break;
            }
        }
        return percentages;
    }
}