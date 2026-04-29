package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InventoryPage extends BasePage {
    private static By addToCartItem1 = By.xpath("//div[text()='Sauce Labs Backpack']/ancestor::div[@class='inventory_item_description']//button");
    private static By productName1 = By.xpath("//div[text()='Sauce Labs Backpack']");

    private static By addToCartItem2= By.xpath("//div[text()='Sauce Labs Fleece Jacket']/ancestor::div[@class='inventory_item_description']//button");
    private static By sortByNameAZ=By.xpath("//option[@value='az']");
    private static By sortByNameZA=By.xpath("//option[@value='za']");
    private static By sortByPriceLowToHigh=By.xpath("//option[@value='lohi']");
    private static By sortByPriceHighToLow=By.xpath("//option[@value='hilo']");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    //  Click button
    public void clickButton(String productName, String action) {
        By btn = By.xpath("//div[text()='" + productName + "']" +
                "/ancestor::div[@class='inventory_item']" +
                "//button[contains(@id,'" + action + "')]");
        click(btn);
    }
    // Add to cart
    public void addToCart(String productName) {
        clickButton(productName, "add-to-cart");
    }
    //  Remove
    public void clickRemove(String productName) {
        By btnRemove = By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button[text()='Remove']");
        click(btnRemove);
    }

    //  Click product
    public void openProductDetail(String productName) {
        By product = By.xpath("//div[text()='" + productName + "']");
        click(product);
    }
    public boolean isRemoveButtonDisplayed(String productName) {
        By removeBtn = By.xpath("//div[text()='" + productName + "']" +
                "/ancestor::div[@class='inventory_item']" +
                "//button[contains(@id,'remove')]");

        return driver.findElements(removeBtn).size() > 0;
    }

    //  Cart icon
    public void clickCartIcon() {
        click(By.className("shopping_cart_link"));
    }

    //  Cart badge
    public int getCartBadgeCount() {
        String count = driver.findElement(By.className("shopping_cart_badge")).getText();
        return Integer.parseInt(count);
    }
}


