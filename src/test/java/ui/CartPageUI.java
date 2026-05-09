package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPageUI extends BasePageUI {

    public static final By CARTITEMS = By.className("cart_item");
    public static final By QUANTITIES = By.className("cart_quantity");
    public static final By PRODUCT_NAMES = By.className("inventory_item_name");
    public static final By PRODUCT_PRICES = By.className("inventory_item_price");
    public static final By pageTitle = By.xpath("//span[@class='title']");
    public static final By CHECKOUT_BUTTON = By.id("checkout");
    public static final By CONTINUE_SHOPPING_BUTTON = By.id("continue-shopping");
    public static final By REMOVE_BUTTON = By.xpath(".//button[contains(@id,'remove')]");

    public CartPageUI(WebDriver driver) {
        super(driver);
    }
}
