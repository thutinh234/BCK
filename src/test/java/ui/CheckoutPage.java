package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class CheckoutPage extends BasePage {

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    // ===== Your Info =====
    private By firstName = By.id("first-name");
    private By lastName = By.id("last-name");
    private By zipCode = By.id("postal-code");
    private By continueBtn = By.id("continue");
    private By errorMsg = By.cssSelector("[data-test='error']");
    private By itemPrices = By.className("inventory_item_price");
    private By itemTotal = By.className("summary_subtotal_label");
    private By tax = By.className("summary_tax_label");
    private By total = By.className("summary_total_label");
    // ===== Overview =====
    private By finishBtn = By.id("finish");
    private By cancelBtn = By.id("cancel");

    // ===== Complete =====
    private By completeHeader = By.className("complete-header");
    private By backHomeBtn = By.id("back-to-products");

    // ===== ACTION =====
    public void inputFirstName(String value) {
        sendKeys(firstName, value);
    }

    public void inputLastName(String value) {
        sendKeys(lastName, value);
    }

    public void inputZip(String value) {
        sendKeys(zipCode, value);
    }

    public void clickContinue() {
        click(continueBtn);
    }

    public void clickFinish() {
        click(finishBtn);
    }

    public void clickCancel() {
        click(cancelBtn);
    }

    public void clickBackHome() {
        click(backHomeBtn);
    }

    // ===== VERIFY =====
    public String getErrorMessage() {
        return getText(errorMsg);
    }

    public String getCompleteMessage() {
        return getText(completeHeader);
    }
    public List<Double> getAllItemPrices() {
        return driver.findElements(itemPrices)
                .stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .toList();
    }

    public double getItemTotal() {
        String text = getText(itemTotal); // "Item total: $29.99"
        return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }

    public double getTax() {
        String text = getText(tax);
        return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }

    public double getTotal() {
        String text = getText(total);
        return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }
}