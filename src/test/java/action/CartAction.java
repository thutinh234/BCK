package action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ui.CartPageUI;
import java.util.List;

public class CartAction {
    private WebDriver driver;
    private CartPageUI cartPage;

    public CartAction(WebDriver driver) {
        this.driver = driver;
        cartPage = new CartPageUI(driver);
    }

    public List<String> getAllQuantities() {
        return driver.findElements(CartPageUI.QUANTITIES)
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public String getCartProductPrice(String productName) {
        List<WebElement> items = driver.findElements(CartPageUI.CARTITEMS);
        for (WebElement item : items) {
            String name = item.findElement(CartPageUI.PRODUCT_NAMES).getText();
            if (name.equals(productName)) {
                return item.findElement(CartPageUI.PRODUCT_PRICES).getText();
            }
        }
        throw new RuntimeException("Product not found in cart: " + productName);
    }

    public int getCartItemCount() {
        return driver.findElements(CartPageUI.CARTITEMS).size();
    }

    public void removeProductInCart(String productName) {
        List<WebElement> items = driver.findElements(CartPageUI.CARTITEMS);
        for (WebElement item : items) {
            String name = item.findElement(CartPageUI.PRODUCT_NAMES).getText();
            if (name.equals(productName)) {
                item.findElement(CartPageUI.REMOVE_BUTTON).click();
                break;
            }
        }
    }

    public void clickContinueShopping() {
        cartPage.click(CartPageUI.CONTINUE_SHOPPING_BUTTON);
    }

    public boolean isOnCartPage() {
        return cartPage.getText(CartPageUI.pageTitle).equals("Your Cart");
    }

    public void clickCheckout() {
        cartPage.click(CartPageUI.CHECKOUT_BUTTON);
    }

    public boolean isCheckoutButtonDisplayed() {
        return driver.findElement(CartPageUI.CHECKOUT_BUTTON).isDisplayed();
    }

    public boolean isContinueShoppingDisplayed() {
        return driver.findElement(CartPageUI.CONTINUE_SHOPPING_BUTTON).isDisplayed();
    }
}
