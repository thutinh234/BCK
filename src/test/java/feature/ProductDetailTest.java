package feature;

import action.InventoryAction;
import action.LoginAction;
import action.ProductDetailAction;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProductDetailTest extends Base {

    LoginAction loginAction;
    InventoryAction inventoryAction;
    ProductDetailAction productDetailAction;

    @BeforeMethod
    public void setUp() {
        loginAction = new LoginAction(driver);
        inventoryAction = new InventoryAction(driver);
        loginAction.login("standard_user", "secret_sauce");
        productDetailAction = inventoryAction.clickProduct("Sauce Labs Backpack");
    }

    @Test
    public void verifyAddToCartButtonDisplayed() {
        Assert.assertTrue(productDetailAction.isAddToCartButtonDisplayed(), "Button Add to Cart not displayed");
    }

    @Test
    public void verifyBackToInventory() {
        productDetailAction.clickBackButton();
        Assert.assertTrue(inventoryAction.isOnInventoryPage(), "Not navigated back to inventory");
    }

    @Test
    public void testProductDetail() {
        String actualName = productDetailAction.getProductName();
        String actualPrice = productDetailAction.getProductPrice();
        Assert.assertEquals(actualName, "Sauce Labs Backpack", "Display correct product name");
        Assert.assertEquals(actualPrice, "$29.99", "Display correct product price");
    }
}
