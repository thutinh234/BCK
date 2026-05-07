package action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ui.CheckoutPageCompleteUI;


public class CheckoutPageCompleteAction{
    private CheckoutPageCompleteUI checkoutPageComplete;
    public static final By COMPLETE_HEADER = By.className("complete-header");

    public CheckoutPageCompleteAction(WebDriver driver) {
        checkoutPageComplete = new CheckoutPageCompleteUI(driver);
    }
    public String getCompleteMessage() {
        return checkoutPageComplete.getCompleteMessage();
    }
    public boolean isBackHomeButtonDisplayed() {
        return checkoutPageComplete.isBackHomeButtonDisplayed();
    }
    public String getTitlePage() {
        return checkoutPageComplete.getTitlePage();
    }
    public boolean isCompleteImageDisplayed(){
        return checkoutPageComplete.isCompleteImageDisplayed();
    }
}
