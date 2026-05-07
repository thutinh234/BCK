package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailPageUI extends BasePageUI {

    public static final By PRODUCT_NAME = By.xpath("//div[@class='inventory_details_name large_size']");
    public static final By PRODUCT_PRICE = By.xpath("//div[@class='inventory_details_price']");
    public static final By BACK_BUTTON = By.xpath("//button[@id='back-to-products']");
    public static final By ADD_TO_CART_BUTTON = By.xpath("//button[text()='Add to cart']");
    public static final By REMOVE_BUTTON = By.xpath("//button[text()='Remove']");

    public ProductDetailPageUI(WebDriver driver) {
        super(driver);
    }

    public String getProductName() {

        return driver.findElement(PRODUCT_NAME).getText();
    }

    public String getProductPrice() {

        return driver.findElement(PRODUCT_PRICE).getText();
    }

    public void clickBackButton() {
        click(BACK_BUTTON);
    }

    public boolean isAddToCartButtonDisplayed() {

        return driver.findElement(ADD_TO_CART_BUTTON)
                .isDisplayed();
    }

    public boolean isRemoveButtonDisplayed() {

        return driver.findElement(REMOVE_BUTTON)
                .isDisplayed();
    }

}