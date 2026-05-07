package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


import java.util.List;

public class CheckoutPageOverviewUI extends BasePageUI {
    public static final By ITEM_PRICES = By.className("inventory_item_price");
    public static final By ITEM_TOTAL = By.className("summary_subtotal_label");
    public static final By TAX = By.className("summary_tax_label");
    public static final By TOTAL = By.className("summary_total_label");
    public static final By FINISH_BUTTON = By.id("finish");
    public static final By BACK_HOME_BUTTON = By.id("back-to-products");
    public static final By CANCEL_BUTTON = By.id("cancel");
    public CheckoutPageOverviewUI(WebDriver driver) {
        super(driver);
    }
    public void clickCancel() {
        click(CANCEL_BUTTON);
    }

    public void clickBackHome() {
        click(BACK_HOME_BUTTON);
    }

    public void clickFinish() {
        click(FINISH_BUTTON);
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
