package action;

import org.openqa.selenium.WebDriver;
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
        return cartPage.getAllQuantities();
    }
    public String getCartProductPrice(String name) {
        return cartPage.getPriceByProductName(name);
    }

    public int getCartItemCount() {
        return cartPage.getItemCount();
    }

    public void removeProductInCart(String name) {
        cartPage.clickRemove(name);
    }

    public void clickContinueShopping() {
        cartPage.clickContinueShopping();
    }
    public boolean isOnCartPage() {
        return cartPage.getPageTitle().equals("Your Cart");
    }
    public void clickCheckout() {
        cartPage.clickCheckout();
    }
    public boolean isCheckoutButtonDisplayed() {
        return cartPage.isCheckoutDisplayed();
    }

    public boolean isContinueShoppingDisplayed() {
        return cartPage.isContinueShoppingDisplayed();
    }
}
