package feature;

import action.CartAction;
import action.InventoryAction;
import action.LoginAction;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ui.ProductDetailPageUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventoryTest extends BaseTest {

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
    //verify hiển thị thông tin sản phẩm
    @Test
    public void verifyProductInfoDisplayed() {

        int itemCount = inventoryAction.getInventoryItemCount();

        Assert.assertEquals(inventoryAction.getImageCount(), itemCount,
                "Some products missing image");

        Assert.assertEquals(inventoryAction.getDescriptionCount(), itemCount,
                "Some products missing description");

        List<String> prices = inventoryAction.getAllPrices();

        for (String price : prices) {
            Assert.assertTrue(price.matches("\\$\\d+\\.\\d{2}"),
                    "Invalid price format: " + price);
        }
    }
    //verify thêm sản phẩm vào giỏ hàng
    @Test
    public void testAddProductToCart() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        boolean isRemoveDisplayed = inventoryAction.isRemoveButtonDisplayed("Sauce Labs Backpack");
        Assert.assertTrue(isRemoveDisplayed,"Sản phẩm chưa được thêm vào giỏ hàng");
    }
    //verify đếm số lượng hiển thị khi thêm sản phẩm vào giỏ hàng
    @Test
    public void verifyCartBadgeCount() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.addProductToCart("Sauce Labs Bike Light");

        int badge = inventoryAction.getCartCount();
        Assert.assertEquals(badge, 2, "Cart badge count incorrect");
    }
//verify xoá sản phẩm khỏi giỏ hàng
    @Test
    public void testRemoveProduct() {
        inventoryAction.addProductToCart("Sauce Labs Bolt T-Shirt");
        inventoryAction.removeProduct("Sauce Labs Bolt T-Shirt");
        boolean isAddBtnDisplayed = inventoryAction.isAddToCartButtonDisplayed("Sauce Labs Bolt T-Shirt");
        Assert.assertTrue(isAddBtnDisplayed, "Sản phẩm chưa được remove khỏi giỏ hàng");

    }
// verofy màn chi tiết sản phẩm khi click vào sản phẩm
    @Test
    public void testClickProduct() {
        ProductDetailPageUI detailPage = inventoryAction.clickProduct("Sauce Labs Backpack");

        String actualName = detailPage.getProductName();
        Assert.assertEquals(actualName, "Sauce Labs Backpack");
    }
    //verify khi back về từ màn chi tiết sản phẩm
    @Test
    public void verifyBackFromProductDetail() {
        inventoryAction.clickProduct("Sauce Labs Backpack");

        driver.navigate().back();

        Assert.assertTrue(inventoryAction.isOnInventoryPage(),
                "Not returned to inventory page");
    }
    // verify chức năng đi đến giỏ hàng
    @Test
    public void goToCart(){
        inventoryAction.goToCart();
        Assert.assertEquals(inventoryAction.getCartTitle(), "Your Cart");
    }
    // verify cart rỗng khi mới vào gian hàng
    @Test
    public void verifyInitialCartEmpty() {
        Assert.assertEquals(inventoryAction.getCartCount(), 0,
                "Cart should be empty at start");
    }
    //verify thông tin giỏ hàng sau khi refresh
    @Test
    public void verifyCartAfterRefresh() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");

        driver.navigate().refresh();

        Assert.assertEquals(inventoryAction.getCartCount(), 1,
                "Cart lost after refresh");
    }
    // verify thông tin giỏ hàng sau khi login lại
    @Test
    public void verifyCartAfterRelogin() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");

        inventoryAction.logout();
        loginAction.login("standard_user", "secret_sauce");

        Assert.assertEquals(inventoryAction.getCartCount(), 1,
                "Cart not persisted after relogin");
    }
//verify số lượng hiển thị item của giỏ hàng ở màn inventory và màn your cart
    @Test
    public void verifyCartBadgeMatchesItemCount() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.addProductToCart("Sauce Labs Bike Light");

        int badge = inventoryAction.getCartCount();
        inventoryAction.goToCart();

        int actual = cartAction.getCartItemCount();

        Assert.assertEquals(badge, actual, "Badge and cart mismatch");
    }
    //verify remove item và check số lượng
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
   // verify chức năng sort
    @Test
    public void verifySortNameAZ() {
        InventoryAction action = new InventoryAction(driver);

        action.sortBy("Name (A to Z)");

        List<String> actual = action.getNames();

        List<String> expected = new ArrayList<>(actual);
        Collections.sort(expected);

        Assert.assertEquals(actual, expected,
                "Sort Name A-Z failed. Actual: " + actual + " | Expected: " + expected);
    }
    @Test
    public void verifySortNameZA() {
        InventoryAction action = new InventoryAction(driver);

        action.sortBy("Name (Z to A)");

        List<String> actual = action.getNames();

        List<String> expected = new ArrayList<>(actual);
        expected.sort(Collections.reverseOrder());

        Assert.assertEquals(actual, expected);
    }
    @Test
    public void verifySortPriceLowToHigh() {
        InventoryAction action = new InventoryAction(driver);

        action.sortBy("Price (low to high)");

        List<Double> actual = action.getPrices();

        List<Double> expected = new ArrayList<>(actual);
        Collections.sort(expected);

        Assert.assertEquals(actual, expected);
    }
    @Test
    public void verifySortPriceHighToLow() {
        InventoryAction action = new InventoryAction(driver);

        action.sortBy("Price (high to low)");

        List<Double> actual = action.getPrices();

        List<Double> expected = new ArrayList<>(actual);
        expected.sort(Collections.reverseOrder());

        Assert.assertEquals(actual, expected);
    }

}