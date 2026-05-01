package action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ui.InventoryPage;
import ui.ProductDetailPage;

public class InventoryAction {
    private WebDriver driver;
    private InventoryPage inventoryPage;

    public InventoryAction(WebDriver driver) {
        this.driver = driver;
        this.inventoryPage = new InventoryPage(driver);
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

    public ProductDetailPage clickProduct(String productName) {
        inventoryPage.openProductDetail(productName);
        return new ProductDetailPage(driver); // ✅ phải truyền driver
    }


    public int getCartCount() {
        return inventoryPage.getCartBadgeCount();
    }

    public void goToCart() {
        inventoryPage.clickCartIcon();

    }

//    public void sortBy(String option) {
//        inventoryPage.selectSort(option);
//    }
}