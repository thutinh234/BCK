package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CheckoutPageUI extends BasePageUI {

    public static final By FIRSTNAME = By.id("first-name");
    public static final By LASTNAME = By.id("last-name");
    public static final By ZIPCODE = By.id("postal-code");
    public static final By CONTINUE_BUTTON = By.id("continue");
    public static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");
    public static final By ITEM_PRICES = By.className("inventory_item_price");
    public static final By ITEM_TOTAL = By.className("summary_subtotal_label");
    public static final By TAX = By.className("summary_tax_label");
    public static final By TOTAL = By.className("summary_total_label");
    public static final By FINISH_BUTTON = By.id("finish");
    public static final By CANCEL_BUTTON = By.id("cancel");
    public static final By COMPLETE_HEADER = By.className("complete-header");
    public static final By BACK_HOME_BUTTON = By.id("back-to-products");

    public CheckoutPageUI(WebDriver driver) {
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

    public void clickFinish() {
        click(FINISH_BUTTON);
    }

    public void clickCancel() {
        click(CANCEL_BUTTON);
    }

    public void clickBackHome() {
        click(BACK_HOME_BUTTON);
    }

    public String getErrorMessage() {
        return getText(ERROR_MESSAGE);
    }

    public String getCompleteMessage() {
        return getText(COMPLETE_HEADER);
    }

    public List<Double> getAllItemPrices() {
        return driver.findElements(ITEM_PRICES)
                .stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .toList();
    }
    private double extractPrice(String text) {
        return Double.parseDouble(
                text.replaceAll("[^0-9.]", "")
        );
    }

    public double getItemTotal() {
        String text = driver.findElement(ITEM_TOTAL).getText();
        return extractPrice(text);
    }

    public double getTax() {
        String text = driver.findElement(TAX).getText();
        return extractPrice(text);
    }

    public double getTotal() {
        String text = getText(TOTAL);
        return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }
}