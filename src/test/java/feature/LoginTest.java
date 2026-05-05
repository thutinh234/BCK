package feature;

import action.InventoryAction;
import action.LoginAction;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    LoginAction loginAction;

    @BeforeMethod
    public void init() {
        loginAction = new LoginAction(driver);
    }

    @Test
    public void testLoginSuccess() {
        loginAction.login("standard_user", "secret_sauce");

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"),
                "Login failed!");
    }

    @Test
    public void testLoginWithInvalidUsername() {
        loginAction.login("standard1", "secret_sauce");

        String error = loginAction.getErrorMessage();

        Assert.assertTrue(error.contains("Username and password do not match"));
    }

    @Test
    public void testLoginWithInvalidPassword() {
        loginAction.login("standard_user", "secret_sauce1");

        String error = loginAction.getErrorMessage();

        Assert.assertTrue(error.contains("Username and password do not match"));
    }

    @Test
    public void testLoginWithEmptyUsername() {
        loginAction.login("", "secret_sauce");

        String error = loginAction.getErrorMessage();

        Assert.assertTrue(error.contains("Username is required"));
    }

    @Test
    public void testLoginWithEmptyPassword() {
        loginAction.login("standard_user", "");

        String error = loginAction.getErrorMessage();

        Assert.assertTrue(error.contains("Password is required"));
    }
    @Test
    public void testLockedUser() {
        loginAction.login("locked_out_user", "secret_sauce");
        Assert.assertTrue(loginAction.getErrorMessage().contains("locked out"));
    }
}