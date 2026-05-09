package feature;

import action.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckoutTest extends Base {

    private InventoryAction inventoryAction;
    private CheckoutPageYourInformationAction checkoutActionYourInformation;
    private CheckoutPageOverviewAction checkoutActionOverview;
    private CheckoutPageCompleteAction checkoutActionComplete;
    private CartAction cartAction;
    private LoginAction loginAction;

    @BeforeMethod
    public void init() {
        inventoryAction = new InventoryAction(driver);
        checkoutActionYourInformation = new CheckoutPageYourInformationAction(driver);
        checkoutActionOverview = new CheckoutPageOverviewAction(driver);
        checkoutActionComplete = new CheckoutPageCompleteAction(driver);
        cartAction = new CartAction(driver);
        loginAction = new LoginAction(driver);
        loginAction.login("standard_user", "secret_sauce");
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.goToCart();
        cartAction.clickCheckout();
    }

    @Test
    public void verifyCheckoutSuccess() {
        checkoutActionYourInformation.fillInformation("Tinh", "Tran", "10000");
        checkoutActionYourInformation.submitInformation();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two"), "Not navigate to Overview page");
    }

    @Test
    public void verifyFirstNameRequired() {
        checkoutActionYourInformation.fillInformation("", "Tran", "10000");
        checkoutActionYourInformation.submitInformation();
        Assert.assertEquals(checkoutActionYourInformation.getErrorMessage(), "Error: First Name is required");
    }

    @Test
    public void verifyLastNameRequired() {
        checkoutActionYourInformation.fillInformation("Tinh", "", "10000");
        checkoutActionYourInformation.submitInformation();
        Assert.assertEquals(checkoutActionYourInformation.getErrorMessage(), "Error: Last Name is required");
    }

    @Test
    public void verifyPostalCodeRequired() {
        checkoutActionYourInformation.fillInformation("Tinh", "Tran", "");
        checkoutActionYourInformation.submitInformation();
        Assert.assertEquals(checkoutActionYourInformation.getErrorMessage(), "Error: Postal Code is required");
    }

    @Test
    public void verifyCancelCheckout() {
        checkoutActionYourInformation.cancelCheckout();
        Assert.assertTrue(cartAction.isOnCartPage(), "Cancel did not return to cart page");
    }

    @Test
    public void verifyBackFromOverview() {
        checkoutActionYourInformation.fillInformation("Tinh", "Tran", "10000");
        checkoutActionYourInformation.submitInformation();
        checkoutActionOverview.cancelCheckout();
        Assert.assertTrue(inventoryAction.isOnInventoryPage());
    }

    @Test
    public void verifyItemDisplayedInOverview() {
        checkoutActionYourInformation.fillInformation("Tinh", "Tran", "10000");
        checkoutActionYourInformation.submitInformation();
        Assert.assertTrue(checkoutActionYourInformation.isProductDisplayed("Sauce Labs Backpack"), "Product not displayed in overview");
    }

    @Test
    public void verifyPriceBreakdown() {
        checkoutActionYourInformation.fillInformation("Tinh", "Tran", "10000");
        checkoutActionYourInformation.submitInformation();
        double itemTotal = checkoutActionOverview.getItemTotal();
        double tax = checkoutActionOverview.getTax();
        double total = checkoutActionOverview.getActualTotal();
        Assert.assertEquals(itemTotal + tax, total, 0.01, "Price breakdown incorrect");
    }

    @Test
    public void verifyCompleteOrder() {
        checkoutActionYourInformation.fillInformation("Tinh", "Tran", "10000");
        checkoutActionYourInformation.submitInformation();
        checkoutActionOverview.finishCheckout();
        String actualMessageComplete = checkoutActionComplete.getCompleteMessage();
        Assert.assertTrue(actualMessageComplete.equalsIgnoreCase("Thank you for your order!"), "Expected 'thank you' but got: " + actualMessageComplete);
        Assert.assertEquals(checkoutActionComplete.getTitlePage(), "Checkout: Complete!", "Page Title mismatch");
        Assert.assertTrue(checkoutActionComplete.isBackHomeButtonDisplayed());
        Assert.assertTrue(checkoutActionComplete.isCompleteImageDisplayed());
    }

    @Test
    public void verifyBackHomeFromComplete() {
        checkoutActionYourInformation.fillInformation("Tinh", "Tran", "10000");
        checkoutActionYourInformation.submitInformation();
        checkoutActionOverview.finishCheckout();
        checkoutActionComplete.clickBackHome();
        Assert.assertTrue(inventoryAction.isOnInventoryPage(), "Not back to inventory");
    }

    @Test
    public void verifyCheckoutWithMultipleItems() {
        // Clear cart first or just add more
        driver.navigate().to("https://www.saucedemo.com/inventory.html");
        inventoryAction.addProductToCart("Sauce Labs Bike Light");
        inventoryAction.goToCart();
        cartAction.clickCheckout();
        
        checkoutActionYourInformation.fillInformation("Tinh", "Tran", "10000");
        checkoutActionYourInformation.submitInformation();
        
        double expected = checkoutActionOverview.calculateExpectedTotal();
        double actual = checkoutActionOverview.getActualTotal();
        Assert.assertEquals(actual, expected, 0.01, "Total price for multiple items is incorrect");
        
        checkoutActionOverview.finishCheckout();
        Assert.assertTrue(checkoutActionComplete.getCompleteMessage().equalsIgnoreCase("Thank you for your order!"));
    }
}
