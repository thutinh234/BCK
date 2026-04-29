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
        loginAction.login("standard_user", "secret_sauce");
    }
    @Test
    public void testAddProductToCart() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
    }

    @Test
    public void testRemoveProduct() {
        inventoryAction.removeProduct("Sauce Labs Backpack");
    }
    @Test
    public void testClickProduct() {
        inventoryAction.clickProduct("Sauce Labs Backpack");
    }
}