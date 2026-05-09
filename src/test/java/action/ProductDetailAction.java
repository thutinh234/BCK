package action;

import org.openqa.selenium.WebDriver;
import ui.ProductDetailPageUI;

public class ProductDetailAction {
    private WebDriver driver;
    private ProductDetailPageUI productDetailPage;

    public ProductDetailAction(WebDriver driver) {
        this.driver = driver;
        productDetailPage = new ProductDetailPageUI(driver);
    }

    public String getProductName() {
        return productDetailPage.getText(ProductDetailPageUI.PRODUCT_NAME);
    }

    public String getProductPrice() {
        return productDetailPage.getText(ProductDetailPageUI.PRODUCT_PRICE);
    }

    public void clickBackButton() {
        productDetailPage.click(ProductDetailPageUI.BACK_BUTTON);
    }

    public boolean isBackButtonDisplayed() {
        return driver.findElement(ProductDetailPageUI.BACK_BUTTON).isDisplayed();
    }

    public boolean isAddToCartButtonDisplayed() {
        return driver.findElement(ProductDetailPageUI.ADD_TO_CART_BUTTON).isDisplayed();
    }

    public boolean isRemoveButtonDisplayed() {
        return driver.findElement(ProductDetailPageUI.REMOVE_BUTTON).isDisplayed();
    }

    public void addToCart() {
        productDetailPage.click(ProductDetailPageUI.ADD_TO_CART_BUTTON);
    }

    public void removeProduct() {
        productDetailPage.click(ProductDetailPageUI.REMOVE_BUTTON);
    }
}
