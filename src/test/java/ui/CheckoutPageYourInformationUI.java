package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CheckoutPageYourInformationUI extends BasePageUI {

    public static final By FIRSTNAME = By.id("first-name");
    public static final By LASTNAME = By.id("last-name");
    public static final By ZIPCODE = By.id("postal-code");
    public static final By CONTINUE_BUTTON = By.id("continue");
    public static final By CANCEL_BUTTON = By.id("cancel");
    public static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");

    public CheckoutPageYourInformationUI(WebDriver driver) {
        super(driver);
    }
    private By getProductLocator(String name) {
        return By.xpath("//div[@class='inventory_item_name' and text()='" + name + "']");
    }
    public boolean isProductDisplayed(String productName) {
        List<WebElement> elements = driver.findElements(getProductLocator(productName));
        return !elements.isEmpty() && elements.get(0).isDisplayed();
    }
    public void inputFirstName(String value) {
        sendKeys(FIRSTNAME, value);
    }

    public void inputLastName(String value) {
        sendKeys(LASTNAME, value);
    }

    public void inputZip(String value) {
        sendKeys(ZIPCODE, value);
    }

    public void clickContinue() {
        click(CONTINUE_BUTTON);
    }

    public void clickCancel() {
        click(CANCEL_BUTTON);
    }

    public String getErrorMessage() {
        return getText(ERROR_MESSAGE);
    }
}