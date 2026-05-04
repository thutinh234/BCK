package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    private By cartItems = By.className("cart_item");
    private By quantities = By.className("cart_quantity");
    private By productNames = By.className("inventory_item_name");
    private By productPrices = By.className("inventory_item_price");

    private By checkoutBtn = By.id("checkout");
    private By continueShoppingBtn = By.id("continue-shopping");

    // ===== DATA =====

    public int getItemCount() {
        return driver.findElements(cartItems).size();
    }

    public List<String> getAllQuantities() {
        return driver.findElements(quantities)
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public String getPriceByProductName(String productName) {
        List<WebElement> items = driver.findElements(cartItems);

        for (WebElement item : items) {
            String name = item.findElement(By.className("inventory_item_name")).getText();
            if (name.equals(productName)) {
                return item.findElement(By.className("inventory_item_price")).getText();
            }
        }
        return null;
    }

    // ===== ACTION =====

    public void clickRemove(String productName) {
        List<WebElement> items = driver.findElements(cartItems);

        for (WebElement item : items) {
            String name = item.findElement(By.className("inventory_item_name")).getText();
            if (name.equals(productName)) {
                item.findElement(By.tagName("button")).click();
                break;
            }
        }
    }

    public void clickCheckout() {
        click(checkoutBtn);
    }

    public void clickContinueShopping() {
        click(continueShoppingBtn);
    }

    // ===== VERIFY =====

    public boolean isCheckoutDisplayed() {
        return driver.findElement(checkoutBtn).isDisplayed();
    }

    public boolean isContinueShoppingDisplayed() {
        return driver.findElement(continueShoppingBtn).isDisplayed();
    }
}