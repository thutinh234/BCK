package feature;

import action.CartAction;
import action.InventoryAction;
import action.LoginAction;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class CartTest extends BaseTest {

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

    // Verify hiển thị button
    @Test
    public void verifyCartButtonsDisplayed() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.goToCart();

        Assert.assertTrue(cartAction.isCheckoutButtonDisplayed(), "Checkout button not displayed");
        Assert.assertTrue(cartAction.isContinueShoppingDisplayed(), "Continue Shopping button not displayed");
    }
    //verìy cart rỗng khi mới vào màn
    @Test
    public void verifyCartEmptyInitially() {
        inventoryAction.goToCart();

        Assert.assertEquals(
                cartAction.getCartItemCount(),
                0,
                "Cart should be empty when opening cart page"
        );
    }
    //verify giỏ hàng rỗng khi remove sản phẩm
    @Test
    public void verifyRemoveWhenCartEmpty() {
        inventoryAction.goToCart();

        cartAction.removeProductInCart("Sauce Labs Backpack");

        Assert.assertEquals(
                cartAction.getCartItemCount(),
                0,
                "Cart should remain empty"
        );
    }
    //verify back về màn gian hàng
    @Test
    public void verifyBackFromCart() {
        inventoryAction.goToCart();

        driver.navigate().back();

        Assert.assertTrue(inventoryAction.isOnInventoryPage(),
                "Back navigation failed");
    }
    //verify số lượng item khi refresh lại trang
    @Test
    public void verifyCartAfterRefresh() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.goToCart();

        driver.navigate().refresh();

        Assert.assertEquals(cartAction.getCartItemCount(), 1,
                "Cart lost after refresh");
    }
    //remove all items in cart
    @Test
    public void verifyRemoveMultipleItems() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.addProductToCart("Sauce Labs Bike Light");

        inventoryAction.goToCart();

        cartAction.removeProductInCart("Sauce Labs Backpack");
        cartAction.removeProductInCart("Sauce Labs Bike Light");

        Assert.assertEquals(cartAction.getCartItemCount(), 0,
                "Removed all items");
    }
    //verify khi xoá toàn bộ item trong giỏ hàng
    @Test
    public void verifyAllItemsDataInCart() {
        List<String> expected = List.of(
                "Sauce Labs Backpack",
                "Sauce Labs Bike Light"
        );

        inventoryAction.addProductToCart(expected.get(0));
        inventoryAction.addProductToCart(expected.get(1));

        inventoryAction.goToCart();

        List<String> actual = inventoryAction.getNames();

        Assert.assertTrue(actual.containsAll(expected),
                "Cart data mismatch. Actual: " + actual);
    }
    //verify checkout khi giỏ hàng rỗng
    @Test
    public void verifyCheckoutWithEmptyCart() {
        inventoryAction.goToCart();

        cartAction.clickCheckout();

        Assert.assertTrue(inventoryAction.isOnCheckoutPage(),
                "Checkout failed with empty cart");
    }
    // Verify số lượng product
    @Test
    public void verifyCartItemCount() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.addProductToCart("Sauce Labs Bike Light");

        inventoryAction.goToCart();

        int count = cartAction.getCartItemCount();
        Assert.assertEquals(count, 2, "Cart item count mismatch");
    }

    // Verify quantity = 1
    @Test
    public void verifyItemQuantityDefault() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.goToCart();

        List<String> quantities = cartAction.getAllQuantities();

        for (String qty : quantities) {
            Assert.assertEquals(qty, "1", "Quantity is not 1");
        }
    }

    // Verify giá đúng
    @Test
    public void verifyProductPriceInCart() {
        String product = "Sauce Labs Backpack";

        String priceInventory = inventoryAction.getProductPrice(product);

        inventoryAction.addProductToCart(product);
        inventoryAction.goToCart();

        String priceCart = cartAction.getCartProductPrice(product);

        Assert.assertEquals(priceCart, priceInventory,
                "Price mismatch. Cart: " + priceCart + " | Inventory: " + priceInventory);
    }

    // Remove product
    @Test
    public void verifyRemoveProduct() {
        String product = "Sauce Labs Backpack";

        inventoryAction.addProductToCart(product);
        inventoryAction.goToCart();

        cartAction.removeProductInCart(product);

        int count = cartAction.getCartItemCount();
        Assert.assertEquals(count, 0, "Product not removed from cart");
    }

    //  Continue shopping
    @Test
    public void verifyContinueShopping() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.goToCart();

        cartAction.clickContinueShopping();

        Assert.assertTrue(inventoryAction.isOnInventoryPage(), "Not navigated back to inventory");
    }

    // Checkout
    @Test
    public void verifyCheckoutNavigation() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.goToCart();

        cartAction.clickCheckout();

        Assert.assertTrue(inventoryAction.isOnCheckoutPage(), "Not navigated to checkout page");
    }
}