package feature;

import action.CartAction;
import action.InventoryAction;
import action.LoginAction;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class CartTest extends Base {

    private CartAction cartAction;
    private InventoryAction inventoryAction;
    LoginAction loginAction;

    @BeforeMethod
    public void init() {
        loginAction = new LoginAction(driver);
        loginAction.login("standard_user", "secret_sauce");
        cartAction = new CartAction(driver);
        inventoryAction = new InventoryAction(driver);
    }

    @Test
    public void verifyCartButtonsDisplayed() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.goToCart();
        Assert.assertTrue(cartAction.isCheckoutButtonDisplayed(), "Checkout button not displayed");
        Assert.assertTrue(cartAction.isContinueShoppingDisplayed(), "Continue Shopping button not displayed");
    }

    @Test
    public void verifyCartEmptyInitially() {
        inventoryAction.goToCart();
        Assert.assertEquals(cartAction.getCartItemCount(), 0, "Cart should be empty when opening cart page");
    }

    @Test
    public void verifyBackFromCart() {
        inventoryAction.goToCart();
        driver.navigate().back();
        Assert.assertTrue(inventoryAction.isOnInventoryPage(), "Back navigation failed");
    }

    @Test
    public void verifyCartAfterRefresh() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.goToCart();
        driver.navigate().refresh();
        Assert.assertEquals(cartAction.getCartItemCount(), 1, "Cart lost after refresh");
    }

    @Test
    public void verifyRemoveMultipleItems() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.addProductToCart("Sauce Labs Bike Light");
        inventoryAction.goToCart();
        cartAction.removeProductInCart("Sauce Labs Backpack");
        cartAction.removeProductInCart("Sauce Labs Bike Light");
        Assert.assertEquals(cartAction.getCartItemCount(), 0, "Removed all items");
    }

    @Test
    public void verifyAllItemsDataInCart() {
        List<String> expected = List.of("Sauce Labs Backpack", "Sauce Labs Bike Light");
        inventoryAction.addProductToCart(expected.get(0));
        inventoryAction.addProductToCart(expected.get(1));
        inventoryAction.goToCart();
        List<String> actual = inventoryAction.getNames();
        Assert.assertTrue(actual.containsAll(expected), "Cart data mismatch. Actual: " + actual);
    }

    @Test
    public void verifyCheckoutWithEmptyCart() {
        inventoryAction.goToCart();
        cartAction.clickCheckout();
        Assert.assertTrue(inventoryAction.isOnCheckoutPage(), "Checkout failed with empty cart");
    }

    @Test
    public void verifyCartItemCount() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.addProductToCart("Sauce Labs Bike Light");
        inventoryAction.goToCart();
        int count = cartAction.getCartItemCount();
        Assert.assertEquals(count, 2, "Cart item count mismatch");
    }

    @Test
    public void verifyItemQuantityDefault() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.goToCart();
        List<String> quantities = cartAction.getAllQuantities();
        for (String qty : quantities) {
            Assert.assertEquals(qty, "1", "Quantity is not 1");
        }
    }

    @Test
    public void verifyProductPriceInCart() {
        String product = "Sauce Labs Backpack";
        String priceInventory = inventoryAction.getProductPrice(product);
        inventoryAction.addProductToCart(product);
        inventoryAction.goToCart();
        String priceCart = cartAction.getCartProductPrice(product);
        Assert.assertEquals(priceCart, priceInventory, "Price mismatch. Cart: " + priceCart + " | Inventory: " + priceInventory);
    }

    @Test
    public void verifyRemoveProduct() {
        String product = "Sauce Labs Backpack";
        inventoryAction.addProductToCart(product);
        inventoryAction.goToCart();
        cartAction.removeProductInCart(product);
        int count = cartAction.getCartItemCount();
        Assert.assertEquals(count, 0, "Product not removed from cart");
    }

    @Test
    public void verifyContinueShopping() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.goToCart();
        cartAction.clickContinueShopping();
        Assert.assertTrue(inventoryAction.isOnInventoryPage(), "Not navigated back to inventory");
    }

    @Test
    public void verifyRemoveFromInventoryReflectedInCart() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        Assert.assertEquals(inventoryAction.getCartCount(), 1);
        inventoryAction.removeProduct("Sauce Labs Backpack");
        Assert.assertEquals(inventoryAction.getCartCount(), 0);
        inventoryAction.goToCart();
        Assert.assertEquals(cartAction.getCartItemCount(), 0, "Cart should be empty");
    }

    @Test
    public void verifyAddMultipleItemsSequential() {
        String[] products = {"Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt"};
        for (String p : products) {
            inventoryAction.addProductToCart(p);
        }
        inventoryAction.goToCart();
        Assert.assertEquals(cartAction.getCartItemCount(), 3, "All 3 items should be in cart");
    }
}
