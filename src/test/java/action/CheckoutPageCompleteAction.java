package action;

import org.openqa.selenium.WebDriver;
import ui.CheckoutPageCompleteUI;

public class CheckoutPageCompleteAction {
    private WebDriver driver;
    private CheckoutPageCompleteUI checkoutPage;

    public CheckoutPageCompleteAction(WebDriver driver) {
        this.driver = driver;
        this.checkoutPage = new CheckoutPageCompleteUI(driver);
    }

    public String getCompleteMessage() {
        return checkoutPage.getText(CheckoutPageCompleteUI.COMPLETE_HEADER);
    }

    public boolean isBackHomeButtonDisplayed() {
        return driver.findElement(CheckoutPageCompleteUI.BACK_HOME_BUTTON).isDisplayed();
    }

    public String getTitlePage() {
        return checkoutPage.getText(CheckoutPageCompleteUI.TITLE_PAGE);
    }

    public boolean isCompleteImageDisplayed() {
        return driver.findElement(CheckoutPageCompleteUI.COMPLETE_IMAGE).isDisplayed();
    }

    public void clickBackHome() {
        checkoutPage.click(CheckoutPageCompleteUI.BACK_HOME_BUTTON);
    }
}
