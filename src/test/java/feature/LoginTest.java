package feature;

import action.LoginAction;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ui.LoginPageUI;

public class LoginTest extends Base {

    LoginAction loginAction;
    LoginPageUI loginPage;

    @BeforeMethod
    public void init() {
        loginAction = new LoginAction(driver);
        loginPage = new LoginPageUI(driver);
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
        Assert.assertTrue(
                error.contains("Username and password do not match"),
                "Expected error not found. Actual: " + error
        );
    }

    @Test
    public void testLoginWithInvalidPassword() {
        loginAction.login("standard_user", "secret_sauce1");

        String error = loginAction.getErrorMessage();

        Assert.assertTrue(
                error.contains("Username and password do not match"),
                "Expected error not found. Actual: " + error
        );
    }
    @Test
    public void testLoginWithInvalidUsernameAndPassword() {
        loginAction.login("standard_user1", "secret_sauce1");

        String error = loginAction.getErrorMessage();

        Assert.assertTrue(
                error.contains("Username and password do not match"),
                "Expected error not found. Actual: " + error
        );
    }

    @Test
    public void testLoginWithEmptyUsername() {
        loginAction.login("", "secret_sauce");

        String error = loginAction.getErrorMessage();

        Assert.assertTrue(
                error.contains("Username is required"),
                "Expected error not found. Actual: " + error
        );
    }

    @Test
    public void testLoginWithEmptyPassword() {
        loginAction.login("standard_user", "");

        String error = loginAction.getErrorMessage();

        Assert.assertTrue(
                error.contains("Password is required"),
                "Expected error not found. Actual: " + error
        );
    }
    @Test
    public void testLoginWithEmptyUsernameAndPassword() {
        loginAction.login("", "");

        String error = loginAction.getErrorMessage();

        Assert.assertTrue(
                error.contains("Username is required"),
                "Expected error not found. Actual: " + error
        );
    }
    @Test
    public void testLockedUser() {
        loginAction.login("locked_out_user", "secret_sauce");
        Assert.assertTrue(loginAction.getErrorMessage().contains("locked out"));
    }
    @Test
    public void testPerformanceUser() {
        loginAction.login("performance_glitch_user", "secret_sauce");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
    }
    @Test
    public void testSQLInjection(){
        LoginAction action = new LoginAction(driver);

        action.login("' OR 1=1", "123456");

        String error = action.getErrorMessage();

        Assert.assertTrue(
                error.contains("Username and password do not match"),
                "System may be vulnerable to SQL Injection!"
        );

        Assert.assertFalse(
                driver.getCurrentUrl().contains("inventory"),
                "SQL Injection bypassed login!"
        );
    }
    @Test
    public void testLoginWithTrimmedInput() {
        loginAction.login(" standard_user ", " secret_sauce ");

        String error = loginAction.getErrorMessage();

        Assert.assertTrue(
                error.contains("Username and password do not match any user in this service"),
                "Expected login to fail due to case sensitivity. Actual: " + error
        );
    }
    @Test
    public void testLoginWithCaseSensitivity() {
        loginAction.login("STANDARD_USER", "secret_sauce");

        String error = loginAction.getErrorMessage();

        Assert.assertTrue(
                error.contains("Username and password do not match"),
                "Expected login to fail due to case sensitivity. Actual: " + error
        );
    }

}