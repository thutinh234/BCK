package action;

import org.openqa.selenium.WebDriver;
import ui.LoginPageUI;

public class LoginAction {

    private LoginPageUI loginPage;

    public LoginAction(WebDriver driver) {
        this.loginPage = new LoginPageUI(driver);
    }

    public void login(String user, String pass) {
        loginPage.sendKeys(LoginPageUI.USERNAME, user);
        loginPage.sendKeys(LoginPageUI.PASSWORD, pass);
        loginPage.click(LoginPageUI.LOGIN_BUTTON);
    }

    public String getErrorMessage() {
        return loginPage.getText(LoginPageUI.ERROR_MESSAGE);
    }
}