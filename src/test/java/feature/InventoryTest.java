package feature;

import action.InventoryAction;
import action.LoginAction;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InventoryTest extends BaseTest {

    LoginAction loginAction;
    InventoryAction inventoryAction;

    @BeforeMethod
    public void init() {
        loginAction = new LoginAction(driver);
        inventoryAction = new InventoryAction(driver);

        // 👉 luôn login trước khi test inventory
        loginAction.login("standard_user", "secret_sauce");
    }

    // ✅ Test add product to cart
    @Test
    public void testAddProductToCart() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");

        int count = inventoryAction.getCartCount();

        Assert.assertEquals(count, 1, "Add to cart failed!");
    }

    // ✅ Test remove product
    @Test
    public void testRemoveProduct() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.removeProduct("Sauce Labs Backpack");

        int count = inventoryAction.getCartCount();

        Assert.assertEquals(count, 0, "Remove product failed!");
    }

    // ✅ Test click product detail
    @Test
    public void testOpenProductDetail() {
        inventoryAction.clickProduct("Sauce Labs Backpack");

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory-item"),
                "Open product detail failed!");
    }

    // ✅ Test go to cart
    @Test
    public void testGoToCart() {
        inventoryAction.goToCart();

        Assert.assertTrue(driver.getCurrentUrl().contains("cart"),
                "Navigate to cart failed!");
    }
}