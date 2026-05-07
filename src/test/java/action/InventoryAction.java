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

    public void logout() {
        inventoryPage.clickLogout();
    }
    public int getImageCount() {
        return inventoryPage.getImageCount();
    }

    public int getDescriptionCount() {
        return inventoryPage.getDescriptionCount();
    }

    public List<String> getAllPrices() {
        return inventoryPage.getAllPrices();
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
    public String getCartTitle() {
        return inventoryPage.getCartTitle();
    }

    public int getCartCount() {
        return inventoryPage.getCartBadgeCount();
    }

    public int getInventoryItemCount() {
        return inventoryPage.getInventoryItemCount();
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


    public String getProductPrice(String name) {
        return inventoryPage.getPriceByProductName(name);
    }

    public boolean isOnInventoryPage() {
        return driver.getCurrentUrl().contains("inventory");
    }

    public boolean isOnCheckoutPage() {
        return driver.getCurrentUrl().contains("checkout");
    }
}