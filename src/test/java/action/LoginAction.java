package action;

import org.openqa.selenium.WebDriver;
import ui.LoginPageUI;

public class LoginAction {

    private LoginPageUI loginPage;

    public LoginAction(WebDriver driver) {
        this.loginPage = new LoginPageUI(driver);
    }

    public void login(String user, String pass) {
        loginPage.login(user, pass);
    }

    public String getErrorMessage() {
        return loginPage.getErrorMessage();
    }
}