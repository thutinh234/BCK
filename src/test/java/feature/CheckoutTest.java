package feature;

import action.InventoryAction;
import action.CheckoutAction;
import action.LoginAction;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    private InventoryAction inventoryAction;
    private CheckoutAction checkoutAction;
    LoginAction loginAction;

    @BeforeMethod
    public void init() {
        inventoryAction = new InventoryAction(driver);
        checkoutAction = new CheckoutAction(driver);
        loginAction = new LoginAction(driver);
        loginAction.login("standard_user", "secret_sauce");
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.goToCart();
        inventoryAction.clickCheckout();
    }

    // ✅ Case 1: valid info
    @Test
    public void verifyCheckoutSuccess() {
        checkoutAction.fillInformation("Tinh", "Tran", "10000");
        checkoutAction.submitInformation();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two"),
                "Not navigate to Overview page");
    }

    // ❌ Case 2: validate empty first name
    @Test
    public void verifyFirstNameRequired() {
        checkoutAction.fillInformation("", "Tran", "10000");
        checkoutAction.submitInformation();

        Assert.assertEquals(checkoutAction.getErrorMessage(),
                "Error: First Name is required");
    }

    // ✅ Case 5: finish order
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

    // ✅ Case 6: back home
    @Test
    public void verifyBackHome() {
        checkoutAction.fillInformation("Tinh", "Tran", "10000");
        checkoutAction.submitInformation();
        checkoutAction.finishCheckout();

        checkoutAction.backToHome();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"),
                "Not back to inventory");
    }
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