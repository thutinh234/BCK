package feature;

import action.CartAction;
import action.InventoryAction;
import action.CheckoutAction;
import action.LoginAction;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    private InventoryAction inventoryAction;
    private CheckoutAction checkoutAction;
    private CartAction cartAction;
    LoginAction loginAction;

    @BeforeMethod
    public void init() {
        inventoryAction = new InventoryAction(driver);
        checkoutAction = new CheckoutAction(driver);
        cartAction = new CartAction(driver);
        loginAction = new LoginAction(driver);
        loginAction.login("standard_user", "secret_sauce");
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.goToCart();
        cartAction.clickCheckout();
    }

    //  valid info
    @Test
    public void verifyCheckoutSuccess() {
        checkoutAction.fillInformation("Tinh", "Tran", "10000");
        checkoutAction.submitInformation();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two"),
                "Not navigate to Overview page");
    }

    // validate empty first name
    @Test
    public void verifyFirstNameRequired() {
        checkoutAction.fillInformation("", "Tran", "10000");
        checkoutAction.submitInformation();

        Assert.assertEquals(checkoutAction.getErrorMessage(),
                "Error: First Name is required");
    }
    //verify empty Last name
    @Test
    public void verifyLastNameRequired() {
        checkoutAction.fillInformation("Tinh", "", "10000");
        checkoutAction.submitInformation();

        Assert.assertEquals(
                checkoutAction.getErrorMessage(),
                "Error: Last Name is required"
        );
    }
    //verify khi không nhập postal code
    @Test
    public void verifyPostalCodeRequired() {
        checkoutAction.fillInformation("Tinh", "Tran", "");
        checkoutAction.submitInformation();

        Assert.assertEquals(
                checkoutAction.getErrorMessage(),
                "Error: Postal Code is required"
        );
    }
    //verìy khi click cancel ở màn checkout
    @Test
    public void verifyCancelCheckout() {
        checkoutAction.cancelCheckout();

        Assert.assertTrue(
                cartAction.isOnCartPage(),
                "Cancel did not return to cart page"
        );
    }
    //verify khi quay lại từ màn overview
    @Test
    public void verifyBackFromOverview() {
        checkoutAction.fillInformation("Tinh", "Tran", "10000");
        checkoutAction.submitInformation();

        checkoutAction.cancelCheckout();
        System.out.println(driver.getCurrentUrl());
        Assert.assertTrue(inventoryAction.isOnInventoryPage());
    }
    //verify luồng checkout vói cart rỗng
    @Test
    public void verifyCheckoutWithEmptyCart() {
        // reset flow
        inventoryAction = new InventoryAction(driver);
        cartAction = new CartAction(driver);

        inventoryAction.goToCart();
        cartAction.clickCheckout();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("checkout-step-one"),
                "Checkout should still allow empty cart or handle properly"
        );
    }
    //verify item hiển thị ở màn overview
    @Test
    public void verifyItemDisplayedInOverview() {
        checkoutAction.fillInformation("Tinh", "Tran", "10000");
        checkoutAction.submitInformation();

        Assert.assertTrue(
                checkoutAction.isProductDisplayed("Sauce Labs Backpack"),
                "Product not displayed in overview"
        );
    }
//verify tổbg tiền ở bước checkout
    @Test public void verifyPriceBreakdown() {
        checkoutAction.fillInformation("Tinh", "Tran", "10000");
        checkoutAction.submitInformation();
        double itemTotal = checkoutAction.getItemTotal();
        double tax = checkoutAction.getTax();
        double total = checkoutAction.getActualTotal();
        Assert.assertEquals(itemTotal + tax, total, 0.01, "Price breakdown incorrect");
    }
    //verify màn checkout sau khi reload lại trang
    @Test
    public void verifyCheckoutAfterRefresh() {
        checkoutAction.fillInformation("Tinh", "Tran", "10000");

        driver.navigate().refresh();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("checkout"),
                "Lost checkout state after refresh"
        );
    }

    // finish order
    @Test
    public void verifyCompleteOrder() {
        checkoutAction.fillInformation("Tinh", "Tran", "10000");
        checkoutAction.submitInformation();

        checkoutAction.finishCheckout();
        System.out.println(checkoutAction.getCompleteMessage());
        String actual = checkoutAction.getCompleteMessage();

        Assert.assertTrue(
                actual.equalsIgnoreCase("THANK YOU FOR YOUR ORDER!"),
                "Expected 'thank you' but got: " + actual
        );
    }

    //  back home
    @Test
    public void verifyBackHome() {
        checkoutAction.fillInformation("Tinh", "Tran", "10000");
        checkoutAction.submitInformation();
        checkoutAction.finishCheckout();

        checkoutAction.backToHome();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"),
                "Not back to inventory");
    }
    //verify tính tổng tiền
    @Test
    public void verifyTotalPriceCalculation() {

        checkoutAction.fillInformation("Tinh", "Tran", "10000");
        checkoutAction.submitInformation();

        double expected = checkoutAction.calculateExpectedTotal();
        double actual = checkoutAction.getActualTotal();

        System.out.println("Expected: " + expected);
        System.out.println("Actual: " + actual);

        Assert.assertEquals(actual, expected, 0.01,
                "Total price calculation is incorrect");
    }
}