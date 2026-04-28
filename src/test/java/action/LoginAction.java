package action;

import org.openqa.selenium.WebDriver;
import ui.LoginPage;

public class LoginAction {

    private LoginPage loginPage;

    public LoginAction(WebDriver driver) {
        this.loginPage = new LoginPage(driver);
    }

    public void login(String user, String pass) {
        loginPage.login(user, pass);
    }

    public String getErrorMessage() {
        return loginPage.getErrorMessage();
    }
}