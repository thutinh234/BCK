package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPageUI extends BasePageUI {

    public CartPageUI(WebDriver driver) {
        super(driver);
    }

    public static final By CARTITEMS = By.className("cart_item");
    public static final By QUANTITIES = By.className("cart_quantity");
    public static final By PRODUCT_NAMES = By.className("inventory_item_name");
    public static final By PRODUCT_PRICES = By.className("inventory_item_price");
    public static final By pageTitle = By.xpath("//span[@class='title']");
    public static final By CHECKOUT_BUTTON = By.id("checkout");
    public static final By CONTINUE_SHOPPING_BUTTON = By.id("continue-shopping");
    public static final By REMOVE_BUTTON=By.xpath(".//button[contains(@id,'remove')]");

    public String getPageTitle() {
        return driver.findElement(pageTitle).getText();
    }
    public int getItemCount() {
        return driver.findElements(CARTITEMS).size();
    }

    public List<String> getAllQuantities() {
        return driver.findElements(QUANTITIES)
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public String getPriceByProductName(String productName) {
        List<WebElement> items = driver.findElements(CARTITEMS);

        for (WebElement item : items) {
            String name = item.findElement(PRODUCT_NAMES).getText();

            if (name.equals(productName)) {
                return item.findElement(PRODUCT_PRICES).getText();
            }
        }

        throw new RuntimeException("Product not found in cart: " + productName);
    }

    public void clickRemove(String productName) {
        List<WebElement> items = driver.findElements(CARTITEMS);

        for (WebElement item : items) {
            String name = item.findElement(PRODUCT_NAMES).getText();
            if (name.equals(productName)) {
                item.findElement(REMOVE_BUTTON).click();
                break;
            }
        }
    }

    public void clickCheckout() {
        click(CHECKOUT_BUTTON);
    }

    public void clickContinueShopping() {
        click(CONTINUE_SHOPPING_BUTTON);
    }

    public boolean isCheckoutDisplayed() {
        return driver.findElement(CHECKOUT_BUTTON).isDisplayed();
    }

    public boolean isContinueShoppingDisplayed() {
        return driver.findElement(CONTINUE_SHOPPING_BUTTON).isDisplayed();
    }
}