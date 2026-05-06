package action;

import org.openqa.selenium.WebDriver;
import ui.CheckoutPageUI;

import java.util.List;

public class CheckoutAction {

    private CheckoutPageUI checkoutPage;

    public CheckoutAction(WebDriver driver) {
        checkoutPage = new CheckoutPageUI(driver);
    }

    public void fillInformation(String first, String last, String zip) {
        checkoutPage.inputFirstName(first);
        checkoutPage.inputLastName(last);
        checkoutPage.inputZip(zip);
    }

    public void submitInformation() {
        checkoutPage.clickContinue();
    }

    public String getErrorMessage() {
        return checkoutPage.getErrorMessage();
    }

    public void finishCheckout() {
        checkoutPage.clickFinish();
    }

    public void cancelCheckout() {
        checkoutPage.clickCancel();
    }

    public String getCompleteMessage() {
        return checkoutPage.getCompleteMessage();
    }

    public void backToHome() {
        checkoutPage.clickBackHome();
    }
    public double calculateExpectedTotal() {
        List<Double> prices = checkoutPage.getAllItemPrices();

        double sum = prices.stream().mapToDouble(Double::doubleValue).sum();

        double tax = checkoutPage.getTax();

        return sum + tax;
    }

    public double getActualTotal() {
        return checkoutPage.getTotal();
    }
}