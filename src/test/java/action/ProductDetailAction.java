package action;

import org.openqa.selenium.WebDriver;
import ui.ProductDetailPageUI;

public class ProductDetailAction {
    private ProductDetailPageUI productDetailPage;

    public ProductDetailAction(WebDriver driver) {
        productDetailPage = new ProductDetailPageUI(driver);
    }
    public boolean isAddToCartButtonDisplayed(String productName) {
        return productDetailPage.isAddToCartButtonDisplayed();
    }
    public boolean isRemoveButtonDisplayed(String productName) {
        return productDetailPage.isRemoveButtonDisplayed();
    }
    public void clickBackButton() {
        productDetailPage.clickBackButton();
    }

}
