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
    public static final By SHOPPING_CART_LINK = By.className("shopping_cart_link");

    public InventoryPageUI(WebDriver driver) {
        super(driver);
    }

}


