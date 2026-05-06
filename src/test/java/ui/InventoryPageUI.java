package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class InventoryPageUI extends BasePageUI {
    public static final By ADD_TO_CART_ITEM1 = By.xpath("//div[text()='Sauce Labs Backpack']/ancestor::div[@class='inventory_item_description']//button");
    public static final By PRODUCT_NAME1 = By.xpath("//div[text()='Sauce Labs Backpack']");

    public static final By ADD_TO_CART_ITEM2= By.xpath("//div[text()='Sauce Labs Fleece Jacket']/ancestor::div[@class='inventory_item_description']//button");
    public static final By SORT_BY_NAME_AZ=By.xpath("//option[@value='az']");
    public static final By SORT_BY_NAME_ZA=By.xpath("//option[@value='za']");
    public static final By SORT_BY_PRICE_LOW_TO_HIGH=By.xpath("//option[@value='lohi']");
    public static final By SORT_BY_PRICE_HIGH_TO_LOW=By.xpath("//option[@value='hilo']");
    public static final By SORT_DROPDOWN = By.className("product_sort_container");
    public static final By PRODUCT_NAMES = By.className("inventory_item_name");
    public static final By PRODUCT_PRICES = By.className("inventory_item_price");
    public static final By CART_BADGE = By.className("shopping_cart_badge");
    public static final By ITEMS=By.className("inventory_item");
    public static final String BUTTON_BY_PRODUCT =
            "//div[text()='%s']/ancestor::div[@class='inventory_item']//button[contains(@id,'%s')]";
    public static final String PRODUCT_NAME_XPATH =
            "//div[@class='inventory_item_name' and text()='%s']";
    public InventoryPageUI(WebDriver driver) {
        super(driver);
    }

    private By getButtonByProduct(String productName, String action) {
        return By.xpath(String.format(BUTTON_BY_PRODUCT, productName, action));
    }
    //  Click button
    public void clickButton(String productName, String action) {
        click(getButtonByProduct(productName, action));
    }

    // Add to cart
    public void addToCart(String productName) {
        click(getButtonByProduct(productName, "add-to-cart"));
    }
    //  Remove
    public void clickRemove(String productName) {
        click(getButtonByProduct(productName, "remove"));
    }
    private By getProductNameLocator(String productName) {
        return By.xpath(String.format(PRODUCT_NAME_XPATH, productName));
    }

    //  Click product
    public void openProductDetail(String productName) {
        click(getProductNameLocator(productName));
    }
    public boolean isRemoveButtonDisplayed(String productName) {
        return driver.findElements(getButtonByProduct(productName, "remove")).size() > 0;
    }

    public boolean isAddToCartButtonDisplayed(String productName) {
        return driver.findElements(getButtonByProduct(productName, "add-to-cart")).size() > 0;
    }

    //  Cart icon
    public void clickCartIcon() {
        click(By.className("shopping_cart_link"));
    }

    //  Cart badge
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


