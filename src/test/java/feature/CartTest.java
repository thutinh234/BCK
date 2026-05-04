package feature;

import action.InventoryAction;
import action.LoginAction;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class CartTest extends BaseTest {

    private InventoryAction inventoryAction;
    LoginAction loginAction;

    @BeforeMethod
    public void init() {
        loginAction = new LoginAction(driver);
        loginAction.login("standard_user", "secret_sauce");
        inventoryAction = new InventoryAction(driver);
    }

    // ✅ 1. Verify hiển thị button
    @Test
    public void verifyCartButtonsDisplayed() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.goToCart();

        Assert.assertTrue(inventoryAction.isCheckoutButtonDisplayed(), "Checkout button not displayed");
        Assert.assertTrue(inventoryAction.isContinueShoppingDisplayed(), "Continue Shopping button not displayed");
    }

    // ✅ 2. Verify số lượng product
    @Test
    public void verifyCartItemCount() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.addProductToCart("Sauce Labs Bike Light");

        inventoryAction.goToCart();

        int count = inventoryAction.getCartItemCount();
        Assert.assertEquals(count, 2, "Cart item count mismatch");
    }

    // ✅ 3. Verify quantity = 1
    @Test
    public void verifyItemQuantityDefault() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.goToCart();

        List<String> quantities = inventoryAction.getAllQuantities();

        for (String qty : quantities) {
            Assert.assertEquals(qty, "1", "Quantity is not 1");
        }
    }

    // ✅ 4. Verify giá đúng
    @Test
    public void verifyProductPriceInCart() {
        String product = "Sauce Labs Backpack";

        String priceInventory = inventoryAction.getProductPrice(product);

        inventoryAction.addProductToCart(product);
        inventoryAction.goToCart();

        String priceCart = inventoryAction.getCartProductPrice(product);

        Assert.assertEquals(priceCart, priceInventory,
                "Price mismatch. Cart: " + priceCart + " | Inventory: " + priceInventory);
    }

    // ✅ 5. Remove product
    @Test
    public void verifyRemoveProduct() {
        String product = "Sauce Labs Backpack";

        inventoryAction.addProductToCart(product);
        inventoryAction.goToCart();

        inventoryAction.removeProductInCart(product);

        int count = inventoryAction.getCartItemCount();
        Assert.assertEquals(count, 0, "Product not removed from cart");
    }

    // ✅ 6. Continue shopping
    @Test
    public void verifyContinueShopping() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.goToCart();

        inventoryAction.clickContinueShopping();

        Assert.assertTrue(inventoryAction.isOnInventoryPage(), "Not navigated back to inventory");
    }

    // ✅ 7. Checkout
    @Test
    public void verifyCheckoutNavigation() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.goToCart();

        inventoryAction.clickCheckout();

        Assert.assertTrue(inventoryAction.isOnCheckoutPage(), "Not navigated to checkout page");
    }
}