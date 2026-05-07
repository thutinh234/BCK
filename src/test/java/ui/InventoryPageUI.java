package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class InventoryPageUI extends BasePageUI {

    public static final By SORT_DROPDOWN = By.className("product_sort_container");
    public static final By PRODUCT_NAMES = By.className("inventory_item_name");
    public static final By PRODUCT_PRICES = By.className("inventory_item_price");
    public static final By CART_BADGE = By.className("shopping_cart_badge");
    public static final By ITEMS=By.className("inventory_item");
    public static final String BUTTON_BY_PRODUCT =
            "//div[text()='%s']/ancestor::div[@class='inventory_item']//button[contains(@id,'%s')]";
    public static final String PRODUCT_NAME_XPATH =
            "//div[contains(@class,'inventory_item')]//div[contains(text(),'%s')]";
    public static final By productImages = By.cssSelector(".inventory_item_img img");
    public static final By productDescriptions = By.className("inventory_item_desc");
    public static final By productPrices = By.className("inventory_item_price");
    public static final By  cartTitle = By.xpath("//span[@class='title']");
    public static final By MENU_BUTTON = By.id("react-burger-menu-btn");
    public static final By LOGOUT_LINK = By.id("logout_sidebar_link");
    public static final By PRODUCT_LIST =
            By.className("inventory_item");
    public void clickLogout() {
        openSidebar();
        driver.findElement(LOGOUT_LINK).click();
    }
    public void openSidebar() {
        click(MENU_BUTTON);
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGOUT_LINK));
    }

    public String getCartTitle() {
        return driver.findElement(cartTitle).getText();
    }
    public int getInventoryItemCount() {
        return driver.findElements(ITEMS).size();
    }
    public int getImageCount() {
        return driver.findElements(productImages).size();
    }

    public int getDescriptionCount() {
        return driver.findElements(productDescriptions).size();
    }

    public List<String> getAllPrices() {
        return driver.findElements(productPrices)
                .stream()
                .map(e -> e.getText())
                .toList();
    }
    public InventoryPageUI(WebDriver driver) {
        super(driver);
    }

    private By getButtonByProduct(String productName, String action) {
        return By.xpath(String.format(BUTTON_BY_PRODUCT, productName, action));
    }

    public void addToCart(String productName) {
        click(getButtonByProduct(productName, "add-to-cart"));
    }

    public void clickRemove(String productName) {
        click(getButtonByProduct(productName, "remove"));
    }
    private By getProductNameLocator(String productName) {
        return By.xpath(String.format(PRODUCT_NAME_XPATH, productName));
    }

    public void openProductDetail(String productName) {
        click(getProductNameLocator(productName));
    }
    public boolean isRemoveButtonDisplayed(String productName) {
        return driver.findElements(getButtonByProduct(productName, "remove")).size() > 0;
    }

    public boolean isAddToCartButtonDisplayed(String productName) {
        return driver.findElements(getButtonByProduct(productName, "add-to-cart")).size() > 0;
    }

    public void clickCartIcon() {
        click(By.className("shopping_cart_link"));
    }

    public int getCartBadgeCount() {
        List<WebElement> badges = driver.findElements(CART_BADGE);

        if (badges.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(badges.get(0).getText());
    }
    public List<String> getProductNames() {
        return driver.findElements(PRODUCT_NAMES)
                .stream()
                .map(e -> e.getText())
                .toList();
    }
    public List<Double> getProductPrices() {
        return driver.findElements(PRODUCT_PRICES)
                .stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .toList();
    }
    public void selectSort(String option) {
        Select select = new Select(driver.findElement(SORT_DROPDOWN));
        select.selectByVisibleText(option);
    }
    public String getPriceByProductName(String productName) {
        List<WebElement> items = driver.findElements(ITEMS);

        for (WebElement item : items) {
            String name = item.findElement(PRODUCT_NAMES).getText();
            if (name.equals(productName)) {
                return item.findElement(PRODUCT_PRICES).getText();
            }
        }
        return null;
    }
}


