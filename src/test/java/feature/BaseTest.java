
package feature;
import action.LoginAction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected WebDriver driver;

//    @BeforeClass
//    public void setup() {
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//    }
//
//    @BeforeMethod
//    public void openUrl() {
//        if (driver == null) {
//            driver = new ChromeDriver();
//        }
//        driver.manage().deleteAllCookies();
//        driver.get("https://www.saucedemo.com/");
//    }
//    @AfterClass
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }

    @BeforeClass
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void openUrl() {
        driver.get("https://www.saucedemo.com/");
        driver.navigate().refresh();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}