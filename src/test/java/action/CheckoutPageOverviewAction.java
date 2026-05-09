package action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ui.CheckoutPageOverviewUI;
import java.util.List;

public class CheckoutPageOverviewAction {
    private WebDriver driver;
    private CheckoutPageOverviewUI checkoutPage;

    public CheckoutPageOverviewAction(WebDriver driver) {
        this.driver = driver;
        this.checkoutPage = new CheckoutPageOverviewUI(driver);
    }

    public void finishCheckout() {
        checkoutPage.click(CheckoutPageOverviewUI.FINISH_BUTTON);
    }

    public void backToHome() {
        checkoutPage.click(CheckoutPageOverviewUI.BACK_HOME_BUTTON);
    }

    public void cancelCheckout() {
        checkoutPage.click(CheckoutPageOverviewUI.CANCEL_BUTTON);
    }

    public List<Double> getAllItemPrices() {
        return driver.findElements(CheckoutPageOverviewUI.ITEM_PRICES)
                .stream()
                .map(e -> extractPrice(e.getText()))
                .toList();
    }

    public double calculateExpectedTotal() {
        List<Double> prices = getAllItemPrices();
        double sum = prices.stream().mapToDouble(Double::doubleValue).sum();
        double tax = getTax();
        return sum + tax;
    }

    public double getActualTotal() {
        return extractPrice(checkoutPage.getText(CheckoutPageOverviewUI.TOTAL));
    }

    public double getItemTotal() {
        return extractPrice(checkoutPage.getText(CheckoutPageOverviewUI.ITEM_TOTAL));
    }

    public double getTax() {
        return extractPrice(checkoutPage.getText(CheckoutPageOverviewUI.TAX));
    }

    private double extractPrice(String text) {
        return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }
}
