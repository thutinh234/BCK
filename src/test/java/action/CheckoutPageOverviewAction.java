package action;

import org.openqa.selenium.WebDriver;

import ui.CheckoutPageOverviewUI;

import java.util.List;


public class CheckoutPageOverviewAction {
    private CheckoutPageOverviewUI checkoutPageOverview;

    public CheckoutPageOverviewAction(WebDriver driver) {
        checkoutPageOverview = new CheckoutPageOverviewUI(driver);
    }
    public void finishCheckout() {
        checkoutPageOverview.clickFinish();
    }

    public void backToHome() {
        checkoutPageOverview.clickBackHome();
    }
    public double calculateExpectedTotal() {
        List<Double> prices = checkoutPageOverview.getAllItemPrices();

        double sum = prices.stream().mapToDouble(Double::doubleValue).sum();

        double tax = checkoutPageOverview.getTax();

        return sum + tax;
    }

    public double getActualTotal() {
        return checkoutPageOverview.getTotal();
    }
    public double getItemTotal() {
        return checkoutPageOverview.getItemTotal();
    }

    public double getTax() {
        return checkoutPageOverview.getTax();
    }

}
