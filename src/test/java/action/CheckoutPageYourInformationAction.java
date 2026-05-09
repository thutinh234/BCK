package action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ui.CheckoutPageYourInformationUI;

public class CheckoutPageYourInformationAction {

    private WebDriver driver;
    private CheckoutPageYourInformationUI checkoutPage;

    public CheckoutPageYourInformationAction(WebDriver driver) {
        this.driver = driver;
        checkoutPage = new CheckoutPageYourInformationUI(driver);
    }

    public void fillInformation(String first, String last, String zip) {
        checkoutPage.sendKeys(CheckoutPageYourInformationUI.FIRSTNAME, first);
        checkoutPage.sendKeys(CheckoutPageYourInformationUI.LASTNAME, last);
        checkoutPage.sendKeys(CheckoutPageYourInformationUI.ZIPCODE, zip);
    }

    public void submitInformation() {
        checkoutPage.click(CheckoutPageYourInformationUI.CONTINUE_BUTTON);
    }

    public String getErrorMessage() {
        return checkoutPage.getText(CheckoutPageYourInformationUI.ERROR_MESSAGE);
    }

    public boolean isProductDisplayed(String productName) {
        By locator = By.xpath(String.format(CheckoutPageYourInformationUI.PRODUCT_NAME_XPATH, productName));
        return !driver.findElements(locator).isEmpty() && driver.findElement(locator).isDisplayed();
    }

    public void cancelCheckout() {
        checkoutPage.click(CheckoutPageYourInformationUI.CANCEL_BUTTON);
    }
}
