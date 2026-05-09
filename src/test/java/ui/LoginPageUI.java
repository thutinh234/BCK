package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPageUI extends BasePageUI {

    public static final By USERNAME = By.id("user-name");
    public static final By PASSWORD = By.id("password");
    public static final By LOGIN_BUTTON = By.id("login-button");
    public static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");

    public LoginPageUI(WebDriver driver) {
        super(driver);
    }

}