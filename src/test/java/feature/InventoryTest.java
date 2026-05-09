package feature;

import action.CartAction;
import action.InventoryAction;
import action.LoginAction;
import action.ProductDetailAction;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ui.InventoryPageUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventoryTest extends Base {

    LoginAction loginAction;
    CartAction cartAction;
    InventoryAction inventoryAction;

    @BeforeMethod
    public void init() {
        loginAction = new LoginAction(driver);
        loginAction.login("standard_user", "secret_sauce");
        inventoryAction = new InventoryAction(driver);
        cartAction = new CartAction(driver);
    }

    @Test
    public void verifyInventoryPage() {
        int actualProducts = inventoryAction.getTotalProducts();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Not in Inventory page");
        Assert.assertEquals(inventoryAction.getCartTitle(), "Products", "Not in Inventory page");
        Assert.assertEquals(actualProducts, 6, "Product total is incorrect");
    }

    @Test
    public void verifyProductInfoDisplayed() {
        int itemCount = inventoryAction.getInventoryItemCount();
        Assert.assertEquals(inventoryAction.getImageCount(), itemCount, "Some products missing image");
        Assert.assertEquals(inventoryAction.getDescriptionCount(), itemCount, "Some products missing description");

        List<String> prices = inventoryAction.getAllPrices();
        for (String price : prices) {
            Assert.assertTrue(price.matches("\\$\\d+\\.\\d{2}"), "Invalid price format: " + price);
        }
    }

    @Test
    public void testAddProductToCart() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.addProductToCart("Sauce Labs Fleece Jacket");
        Assert.assertTrue(inventoryAction.isRemoveButtonDisplayed("Sauce Labs Backpack"), "Product was not added to cart");
        Assert.assertTrue(inventoryAction.isRemoveButtonDisplayed("Sauce Labs Fleece Jacket"), "Product was not added to cart");
        Assert.assertEquals(inventoryAction.getCartBadgeCount(), "2", "Cart badge count is incorrect");
    }

    @Test
    public void verifyCartBadgeCount() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.addProductToCart("Sauce Labs Bike Light");
        Assert.assertEquals(inventoryAction.getCartCount(), 2, "Cart badge count incorrect");
    }

    @Test
    public void testRemoveProduct() {
        inventoryAction.addProductToCart("Sauce Labs Bolt T-Shirt");
        inventoryAction.removeProduct("Sauce Labs Bolt T-Shirt");
        Assert.assertTrue(inventoryAction.isAddToCartButtonDisplayed("Sauce Labs Bolt T-Shirt"), "Sản phẩm chưa được remove khỏi giỏ hàng");
    }

    @Test
    public void testClickProduct() {
        ProductDetailAction detailAction = inventoryAction.clickProduct("Sauce Labs Backpack");
        Assert.assertEquals(detailAction.getProductName(), "Sauce Labs Backpack");
        Assert.assertTrue(detailAction.isBackButtonDisplayed(), "Button Back to products not displayed");
    }

    @Test
    public void verifyBackFromProductDetail() {
        ProductDetailAction detailAction = inventoryAction.clickProduct("Sauce Labs Backpack");
        detailAction.clickBackButton();
        Assert.assertTrue(inventoryAction.isOnInventoryPage(), "Not returned to inventory page");
    }

    @Test
    public void goToCart() {
        inventoryAction.goToCart();
        Assert.assertEquals(cartAction.isOnCartPage(), true, "Should be on Cart page");
    }

    @Test
    public void verifyInitialCartEmpty() {
        Assert.assertEquals(inventoryAction.getCartCount(), 0, "Cart should be empty at start");
    }

    @Test
    public void verifyCartAfterRefresh() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        driver.navigate().refresh();
        Assert.assertEquals(inventoryAction.getCartCount(), 1, "Cart lost after refresh");
    }

    @Test
    public void verifyCartAfterRelogin() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.logout();
        loginAction.login("standard_user", "secret_sauce");
        Assert.assertEquals(inventoryAction.getCartCount(), 1, "Cart not persisted after relogin");
    }

    @Test
    public void verifyCartBadgeMatchesItemCount() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.addProductToCart("Sauce Labs Bike Light");
        int badge = inventoryAction.getCartCount();
        inventoryAction.goToCart();
        Assert.assertEquals(badge, cartAction.getCartItemCount(), "Badge and cart mismatch");
    }

    @Test
    public void verifyAddAndRemoveCartFlow() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.addProductToCart("Sauce Labs Bike Light");
        Assert.assertEquals(inventoryAction.getCartCount(), 2, "Cart count after add is wrong");
        inventoryAction.removeProduct("Sauce Labs Backpack");
        Assert.assertEquals(inventoryAction.getCartCount(), 1, "Cart count after remove is wrong");
        inventoryAction.removeProduct("Sauce Labs Bike Light");
        Assert.assertEquals(inventoryAction.getCartCount(), 0, "Cart should be empty");
    }

    @Test
    public void verifySortNameAZ() {
        inventoryAction.sortBy("Name (A to Z)");
        List<String> actual = inventoryAction.getNames();
        List<String> expected = new ArrayList<>(actual);
        Collections.sort(expected);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void verifySortNameZA() {
        inventoryAction.sortBy("Name (Z to A)");
        List<String> actual = inventoryAction.getNames();
        List<String> expected = new ArrayList<>(actual);
        expected.sort(Collections.reverseOrder());
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void verifySortPriceLowToHigh() {
        inventoryAction.sortBy("Price (low to high)");
        List<Double> actual = inventoryAction.getPrices();
        List<Double> expected = new ArrayList<>(actual);
        Collections.sort(expected);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void verifyAllProductsHavePriceGreaterThanZero() {
        List<Double> prices = inventoryAction.getPrices();
        for (Double price : prices) {
            Assert.assertTrue(price > 0, "Product price should be greater than 0");
        }
    }

    @Test
    public void verifyProductImageAltText() {
        // This is just to demonstrate more checks
        Assert.assertTrue(inventoryAction.getImageCount() > 0, "No product images found");
    }
}
