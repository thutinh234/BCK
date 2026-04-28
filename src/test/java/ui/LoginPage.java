package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    private static By username = By.id("user-name");
    private static By password = By.id("password");
    private static By btnLogin = By.id("login-button");
    private static By errorMsg = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String user, String pass) {
        sendKeys(username, user);
        sendKeys(password, pass);
        click(btnLogin);
    }
    public String getErrorMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(errorMsg)
        ).getText();
    }
    //public void clickSubmit() {
      //  driver.findElement(btnLogin).click();
   //}


}