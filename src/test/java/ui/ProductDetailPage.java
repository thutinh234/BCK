package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailPage extends BasePage {

    public ProductDetailPage(WebDriver driver) {
        super(driver); // ✅ truyền driver lên BasePage
    }

    public String getProductName() {
        return driver.findElement(By.className("inventory_details_name")).getText();
    }
}