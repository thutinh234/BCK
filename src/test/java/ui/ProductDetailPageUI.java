package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailPageUI extends BasePageUI {

    public static final By PRODUCT_NAME=By.className("inventory_details_name");
    public ProductDetailPageUI(WebDriver driver) {
        super(driver); // ✅ truyền driver lên BasePage
    }

    public String getProductName() {

        return driver.findElement(PRODUCT_NAME).getText();
    }
}