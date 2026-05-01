package feature;

import action.InventoryAction;
import action.LoginAction;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ui.ProductDetailPage;

public class InventoryTest extends BaseTest {

    LoginAction loginAction;
    InventoryAction inventoryAction;

    @BeforeMethod
    public void init() {
        loginAction = new LoginAction(driver);
        loginAction.login("standard_user", "secret_sauce");
        inventoryAction = new InventoryAction(driver);
    }
    @Test
    public void testAddProductToCart() {
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        boolean isRemoveDisplayed = inventoryAction.isRemoveButtonDisplayed("Sauce Labs Backpack");
        Assert.assertTrue(isRemoveDisplayed,"Sản phẩm chưa được thêm vào giỏ hàng");
    }

    @Test
    public void testRemoveProduct() {
        inventoryAction.addProductToCart("Sauce Labs Bolt T-Shirt");
        inventoryAction.removeProduct("Sauce Labs Bolt T-Shirt");
        boolean isAddBtnDisplayed = inventoryAction.isAddToCartButtonDisplayed("Sauce Labs Bolt T-Shirt");
        Assert.assertTrue(isAddBtnDisplayed, "Sản phẩm chưa được remove khỏi giỏ hàng");

    }

    @Test
    public void testClickProduct() {
        ProductDetailPage detailPage = inventoryAction.clickProduct("Sauce Labs Backpack");

        String actualName = detailPage.getProductName();
        Assert.assertEquals(actualName, "Sauce Labs Backpack");
    }
    @Test
    public void goToCart(){
        inventoryAction.goToCart();
        String expectedTitle="Your Cart";
        String actualTitle=driver.findElement(By.xpath("//span[@class='title']")).getText();
        Assert.assertEquals(actualTitle,expectedTitle," Page is not your cart");
    }
    @Test
    public void verifyAddAndRemoveCartFlow() {

        // add 2 item
        inventoryAction.addProductToCart("Sauce Labs Backpack");
        inventoryAction.addProductToCart("Sauce Labs Bike Light");

        // verify = 2
        Assert.assertEquals(inventoryAction.getCartCount(), 2, "Cart count after add is wrong");

        // remove 1 item (bạn sẽ cần thêm hàm remove)
        inventoryAction.removeProduct("Sauce Labs Backpack");

        // verify = 1
        Assert.assertEquals(inventoryAction.getCartCount(), 1, "Cart count after remove is wrong");

        // remove hết
        inventoryAction.removeProduct("Sauce Labs Bike Light");

        // verify = 0
        Assert.assertEquals(inventoryAction.getCartCount(), 0, "Cart should be empty");
    }
}