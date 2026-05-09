package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
}
