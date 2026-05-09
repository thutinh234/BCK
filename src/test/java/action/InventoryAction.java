package action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import ui.InventoryPageUI;
import java.util.List;

public class InventoryAction {
    private WebDriver driver;
    private InventoryPageUI inventoryPage;

    public InventoryAction(WebDriver driver) {
        this.driver = driver;
        this.inventoryPage = new InventoryPageUI(driver);
    }

    public void logout() {
        openSidebar();
        inventoryPage.click(InventoryPageUI.LOGOUT_LINK);
    }

    public void openSidebar() {
        inventoryPage.click(InventoryPageUI.MENU_BUTTON);
        // Add a small sleep to allow animation to start/complete as headless can be sensitive
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        // Add explicit wait for visibility to ensure animation is done
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(InventoryPageUI.LOGOUT_LINK));
    }

    public int getImageCount() {
        return driver.findElements(InventoryPageUI.productImages).size();
    }

    public int getDescriptionCount() {
        return driver.findElements(InventoryPageUI.productDescriptions).size();
    }

    public List<String> getAllPrices() {
        return driver.findElements(InventoryPageUI.productPrices)
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public void addProductToCart(String productName) {
        inventoryPage.click(getButtonByProduct(productName, "add-to-cart"));
    }

    public void removeProduct(String productName) {
        inventoryPage.click(getButtonByProduct(productName, "remove"));
    }

    public boolean isRemoveButtonDisplayed(String productName) {
        return !driver.findElements(getButtonByProduct(productName, "remove")).isEmpty();
    }

    public boolean isAddToCartButtonDisplayed(String productName) {
        return !driver.findElements(getButtonByProduct(productName, "add-to-cart")).isEmpty();
    }

    public ProductDetailAction clickProduct(String productName) {
        inventoryPage.click(getProductNameLocator(productName));
        return new ProductDetailAction(driver);
    }

    public String getCartTitle() {
        return inventoryPage.getText(InventoryPageUI.cartTitle);
    }

    public int getCartCount() {
        List<WebElement> badges = driver.findElements(InventoryPageUI.CART_BADGE);
        if (badges.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(badges.get(0).getText());
    }

    public String getCartBadgeCount() {
        List<WebElement> badges = driver.findElements(InventoryPageUI.CART_BADGE);
        if (badges.isEmpty()) {
            return "0";
        }
        return badges.get(0).getText();
    }

    public int getInventoryItemCount() {
        return driver.findElements(InventoryPageUI.ITEMS).size();
    }

    public int getTotalProducts() {
        return driver.findElements(InventoryPageUI.PRODUCT_LIST).size();
    }

    public void goToCart() {
        inventoryPage.click(InventoryPageUI.SHOPPING_CART_LINK);
    }

    public void sortBy(String option) {
        Select select = new Select(driver.findElement(InventoryPageUI.SORT_DROPDOWN));
        select.selectByVisibleText(option);
    }

    public List<String> getNames() {
        return driver.findElements(InventoryPageUI.PRODUCT_NAMES)
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public List<Double> getPrices() {
        return driver.findElements(InventoryPageUI.PRODUCT_PRICES)
                .stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .toList();
    }

    public String getProductPrice(String productName) {
        List<WebElement> items = driver.findElements(InventoryPageUI.ITEMS);
        for (WebElement item : items) {
            String name = item.findElement(InventoryPageUI.PRODUCT_NAMES).getText();
            if (name.equals(productName)) {
                return item.findElement(InventoryPageUI.PRODUCT_PRICES).getText();
            }
        }
        return null;
    }

    public boolean isOnInventoryPage() {
        return driver.getCurrentUrl().contains("inventory");
    }

    public boolean isOnCheckoutPage() {
        return driver.getCurrentUrl().contains("checkout");
    }

    private By getButtonByProduct(String productName, String action) {
        return By.xpath(String.format(InventoryPageUI.BUTTON_BY_PRODUCT, productName, action));
    }

    private By getProductNameLocator(String productName) {
        return By.xpath(String.format(InventoryPageUI.PRODUCT_NAME_XPATH, productName));
    }
}
