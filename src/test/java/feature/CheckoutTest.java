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
    LoginAction loginAction;

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

    //  valid info
    @Test
    public void verifyCheckoutSuccess() {
        checkoutActionYourInformation.fillInformation("Tinh", "Tran", "10000");
        checkoutActionYourInformation.submitInformation();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two"),
                "Not navigate to Overview page");
    }

    // validate empty first name
    @Test
    public void verifyFirstNameRequired() {
        checkoutActionYourInformation.fillInformation("", "Tran", "10000");
        checkoutActionYourInformation.submitInformation();

        Assert.assertEquals(checkoutActionYourInformation.getErrorMessage(),
                "Error: First Name is required");
    }
    //verify empty Last name
    @Test
    public void verifyLastNameRequired() {
        checkoutActionYourInformation.fillInformation("Tinh", "", "10000");
        checkoutActionYourInformation.submitInformation();

        Assert.assertEquals(
                checkoutActionYourInformation.getErrorMessage(),
                "Error: Last Name is required"
        );
    }
    //verify khi không nhập postal code
    @Test
    public void verifyPostalCodeRequired() {
        checkoutActionYourInformation.fillInformation("Tinh", "Tran", "");
        checkoutActionYourInformation.submitInformation();

        Assert.assertEquals(
                checkoutActionYourInformation.getErrorMessage(),
                "Error: Postal Code is required"
        );
    }
    //verìy khi click cancel ở màn checkout
    @Test
    public void verifyCancelCheckout() {
        checkoutActionYourInformation.cancelCheckout();

        Assert.assertTrue(
                cartAction.isOnCartPage(),
                "Cancel did not return to cart page"
        );
    }
    //verify khi quay lại từ màn overview
    @Test
    public void verifyBackFromOverview() {
        checkoutActionYourInformation.fillInformation("Tinh", "Tran", "10000");
        checkoutActionYourInformation.submitInformation();

        checkoutActionYourInformation.cancelCheckout();
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
        checkoutActionYourInformation.fillInformation("Tinh", "Tran", "10000");
        checkoutActionYourInformation.submitInformation();

        Assert.assertTrue(
                checkoutActionYourInformation.isProductDisplayed("Sauce Labs Backpack"),
                "Product not displayed in overview"
        );
    }
//verify tổbg tiền ở bước checkout
    @Test public void verifyPriceBreakdown() {
        checkoutActionYourInformation.fillInformation("Tinh", "Tran", "10000");
        checkoutActionYourInformation.submitInformation();
        double itemTotal = checkoutActionOverview.getItemTotal();
        double tax = checkoutActionOverview.getTax();
        double total = checkoutActionOverview.getActualTotal();
        Assert.assertEquals(itemTotal + tax, total, 0.01, "Price breakdown incorrect");
    }
    //verify màn checkout sau khi reload lại trang
    @Test
    public void verifyCheckoutAfterRefresh() {
        checkoutActionYourInformation.fillInformation("Tinh", "Tran", "10000");

        driver.navigate().refresh();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("checkout"),
                "Lost checkout state after refresh"
        );
    }

    // finish order
    @Test
    public void verifyCompleteOrder() {
        checkoutActionYourInformation.fillInformation("Tinh", "Tran", "10000");
        checkoutActionYourInformation.submitInformation();

        checkoutActionOverview.finishCheckout();
       // System.out.println(checkoutActionComplete.getCompleteMessage());
        String actualMessageComplate = checkoutActionComplete.getCompleteMessage();
        String actualTitlePage = checkoutActionComplete.getTitlePage();
        Assert.assertTrue(
                actualMessageComplate.equalsIgnoreCase("THANK YOU FOR YOUR ORDER!"),
                "Expected 'thank you' but got: " + actualMessageComplate
        );
        Assert.assertEquals(actualTitlePage,"Checkout: Complete!","Không hiển thị đúng Pagte Title");
        Assert.assertTrue(checkoutActionComplete.isBackHomeButtonDisplayed());
        Assert.assertTrue(checkoutActionComplete.isCompleteImageDisplayed());
    }

    //  back home
    @Test
    public void verifyBackHome() {
        checkoutActionYourInformation.fillInformation("Tinh", "Tran", "10000");
        checkoutActionYourInformation.submitInformation();
        checkoutActionOverview.finishCheckout();

        checkoutActionOverview.backToHome();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"),
                "Not back to inventory");
    }
    //verify tính tổng tiền
    @Test
    public void verifyTotalPriceCalculation() {

        checkoutActionYourInformation.fillInformation("Tinh", "Tran", "10000");
        checkoutActionYourInformation.submitInformation();

        double expected = checkoutActionOverview.calculateExpectedTotal();
        double actual = checkoutActionOverview.getActualTotal();

        System.out.println("Expected: " + expected);
        System.out.println("Actual: " + actual);

        Assert.assertEquals(actual, expected, 0.01,
                "Total price calculation is incorrect");
    }
}