package action;

import org.openqa.selenium.WebDriver;
import ui.InventoryPage;

public class InventoryAction {

    private InventoryPage inventoryPage;

    public InventoryAction(WebDriver driver) {
        this.inventoryPage = new InventoryPage(driver);
    }

    public void addProductToCart(String productName) {
        inventoryPage.clickAddToCart(productName);
    }

    public void removeProduct(String productName) {
        inventoryPage.clickRemove(productName);
    }

    public void clickProduct(String productName) {
        inventoryPage.openProductDetail(productName);
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