package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPageCompleteUI extends BasePageUI {
    public static final By TITLE_PAGE=By.xpath("//span[@class='title']");
    public static final By COMPLETE_HEADER = By.className("complete-header");
    public static final By BACK_HOME_BUTTON = By.xpath("//button[@id='back-to-products']");
    public static final By COMPLETE_IMAGE= By.xpath("//img[@class='pony_express']");

    public CheckoutPageCompleteUI(WebDriver driver) {
        super(driver);
    }
    public String getCompleteMessage() {
        return getText(COMPLETE_HEADER);
    }
    public boolean isBackHomeButtonDisplayed(){
        return driver.findElement(BACK_HOME_BUTTON).isDisplayed();
    }
    public String getTitlePage() {
        return getText(TITLE_PAGE);
    }
    public boolean isCompleteImageDisplayed(){
        return driver.findElement(COMPLETE_IMAGE).isDisplayed();
    }
}
