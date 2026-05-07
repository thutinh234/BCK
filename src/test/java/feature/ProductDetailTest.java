package feature;

import action.InventoryAction;
import action.LoginAction;
import action.ProductDetailAction;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ui.ProductDetailPageUI;

public class ProductDetailTest extends Base {

    LoginAction loginAction;
    InventoryAction inventoryAction;
    ProductDetailAction productDetailAction;
    ProductDetailPageUI productDetailPage;
    @BeforeMethod
    public void setUp(){

        loginAction = new LoginAction(driver);
        inventoryAction = new InventoryAction(driver);
        productDetailAction = new ProductDetailAction(driver);
        productDetailPage =
                new ProductDetailPageUI(driver);
        loginAction.login(
                "standard_user",
                "secret_sauce"
        );
        inventoryAction.clickProduct("Sauce Labs Backpack");
    }
    @Test
    public void verifyAddToCartButtonDisplayed(){

        Assert.assertTrue(
                productDetailAction.isAddToCartButtonDisplayed("Sauce Labs Backpack")
        );
    }
    @Test
    public void verifyBackToInventory(){
        productDetailAction.clickBackButton();
        Assert.assertTrue(inventoryAction.isOnInventoryPage(), "Not navigated back to inventory");
    }
    @Test
    public void testProductDetail() {

        String actualName = productDetailPage.getProductName();
        String actualPrice = productDetailPage.getProductPrice();
        Assert.assertEquals(actualName, "Sauce Labs Backpack");
        Assert.assertEquals(actualPrice, "$29.99");
    }
}
