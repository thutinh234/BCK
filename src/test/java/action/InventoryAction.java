package action;

import org.openqa.selenium.WebDriver;
import ui.CartPageUI;
import ui.InventoryPageUI;
import ui.ProductDetailPageUI;

import java.util.List;

public class InventoryAction {
    private WebDriver driver;
    private InventoryPageUI inventoryPage;
    private CartPageUI cartPage;

    public InventoryAction(WebDriver driver) {
        this.driver = driver;
        this.inventoryPage = new InventoryPageUI(driver);
        cartPage = new CartPageUI(driver);
    }

    public void addProductToCart(String productName) {
        inventoryPage.addToCart(productName);
    }

    public void removeProduct(String productName) {
        inventoryPage.clickRemove(productName);
    }

    public boolean isRemoveButtonDisplayed(String productName) {
        return inventoryPage.isRemoveButtonDisplayed(productName);
    }
    public boolean isAddToCartButtonDisplayed(String productName) {
        return inventoryPage.isAddToCartButtonDisplayed(productName);
    }

    public ProductDetailPageUI clickProduct(String productName) {
        inventoryPage.openProductDetail(productName);
        return new ProductDetailPageUI(driver);
    }


    public int getCartCount() {
        return inventoryPage.getCartBadgeCount();
    }

    public void goToCart() {
        inventoryPage.clickCartIcon();

    }

    public void sortBy(String option) {
        inventoryPage.selectSort(option);
    }

    public List<String> getNames() {
        return inventoryPage.getProductNames();
    }

    public List<Double> getPrices() {
        return inventoryPage.getProductPrices();
    }
    public List<String> getAllQuantities() {
        return cartPage.getAllQuantities();
    }

    public String getProductPrice(String name) {
        return inventoryPage.getPriceByProductName(name);
    }

    public String getCartProductPrice(String name) {
        return cartPage.getPriceByProductName(name);
    }

    public int getCartItemCount() {
        return cartPage.getItemCount();
    }

    // ===== ACTION =====

    public void removeProductInCart(String name) {
        cartPage.clickRemove(name);
    }

    public void clickContinueShopping() {
        cartPage.clickContinueShopping();
    }

    public void clickCheckout() {
        cartPage.clickCheckout();
    }

    // ===== VERIFY =====

    public boolean isCheckoutButtonDisplayed() {
        return cartPage.isCheckoutDisplayed();
    }

    public boolean isContinueShoppingDisplayed() {
        return cartPage.isContinueShoppingDisplayed();
    }

    public boolean isOnInventoryPage() {
        return driver.getCurrentUrl().contains("inventory");
    }

    public boolean isOnCheckoutPage() {
        return driver.getCurrentUrl().contains("checkout");
    }
}