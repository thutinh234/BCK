package action;

import org.openqa.selenium.WebDriver;
import ui.CheckoutPageCompleteUI;
import ui.CheckoutPageOverviewUI;
import ui.CheckoutPageYourInformationUI;

import java.util.List;

public class CheckoutPageYourInformationAction {

    private CheckoutPageYourInformationUI checkoutPageYourInformation;


    public CheckoutPageYourInformationAction(WebDriver driver) {
        checkoutPageYourInformation = new CheckoutPageYourInformationUI(driver);
    }

    public void fillInformation(String first, String last, String zip) {
        checkoutPageYourInformation.inputFirstName(first);
        checkoutPageYourInformation.inputLastName(last);
        checkoutPageYourInformation.inputZip(zip);
    }

    public void submitInformation() {
        checkoutPageYourInformation.clickContinue();
    }

    public String getErrorMessage() {
        return checkoutPageYourInformation.getErrorMessage();
    }


    public boolean isProductDisplayed(String productName) {
        return checkoutPageYourInformation.isProductDisplayed(productName);
    }
    public void cancelCheckout() {
        checkoutPageYourInformation.clickCancel();
    }

}