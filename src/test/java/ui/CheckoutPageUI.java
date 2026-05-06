package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class CheckoutPageUI extends BasePageUI {

    public CheckoutPageUI(WebDriver driver) {
        super(driver);
    }

    // ===== Your Info =====
    public static final By FIRSTNAME = By.id("first-name");
    public static final By LASTNAME = By.id("last-name");
    public static final By ZIPCODE = By.id("postal-code");
    public static final By CONTINUE_BUTTON = By.id("continue");
    public static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");
    public static final By ITEM_PRICES = By.className("inventory_item_price");
    public static final By ITEM_TOTAL = By.className("summary_subtotal_label");
    public static final By TAX = By.className("summary_tax_label");
    public static final By TOTAL = By.className("summary_total_label");
    // ===== Overview =====
    public static final By FINISH_BUTTON = By.id("finish");
    public static final By CANCEL_BUTTON = By.id("cancel");

    // ===== Complete =====
    public static final By COMPLETE_HEADER = By.className("complete-header");
    public static final By BACK_HOME_BUTTON = By.id("back-to-products");

    // ===== ACTION =====
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

    // ===== VERIFY =====
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

    public double getItemTotal() {
        String text = getText(ITEM_TOTAL); // "Item total: $29.99"
        return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }

    public double getTax() {
        String text = getText(TAX);
        return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }

    public double getTotal() {
        String text = getText(TOTAL);
        return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }
}