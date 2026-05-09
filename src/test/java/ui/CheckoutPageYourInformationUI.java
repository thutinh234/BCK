package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPageYourInformationUI extends BasePageUI {

    public static final By FIRSTNAME = By.id("first-name");
    public static final By LASTNAME = By.id("last-name");
    public static final By ZIPCODE = By.id("postal-code");
    public static final By CONTINUE_BUTTON = By.id("continue");
    public static final By CANCEL_BUTTON = By.id("cancel");
    public static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");
    public static final String PRODUCT_NAME_XPATH = "//div[@class='inventory_item_name' and text()='%s']";

    public CheckoutPageYourInformationUI(WebDriver driver) {
        super(driver);
    }
}
