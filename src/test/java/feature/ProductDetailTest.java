package feature;

import action.InventoryAction;
import action.LoginAction;
import action.ProductDetailAction;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProductDetailTest extends Base {

    LoginAction loginAction;
    InventoryAction inventoryAction;
    ProductDetailAction productDetailAction;

    @BeforeMethod
    public void setUp(){

        loginAction = new LoginAction(driver);
        inventoryAction = new InventoryAction(driver);
        productDetailAction = new ProductDetailAction(driver);

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
//    @Test
//    public void verifyRemoveButtonDisplayed(){
//
//        productDetailAction.clickAddToCart();
//
//        Assert.assertTrue(
//                productDetailAction.isRemoveButtonDisplayed("Sauce Labs Backpack")
//        );
//    }
}
