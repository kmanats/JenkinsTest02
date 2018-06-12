package test;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class SeleniumWordPress {

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    public SeleniumWordPress() { // tova e nujno za Run na Test File 
        System.setProperty("webdriver.gecko.driver", "C:\\Program Files\\Gecko\\geckodriver-v0.17.0-win64\\geckodriver.exe");
    }

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost/";
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void testSeleniumWordPress() throws Exception {
        driver.get(baseUrl + "/wordpress/wp-login.php");
        driver.findElement(By.id("user_login")).clear();
        driver.findElement(By.id("user_login")).sendKeys("testUser");
        driver.findElement(By.id("user_pass")).clear();
        driver.findElement(By.id("user_pass")).sendKeys("testUser");
        driver.findElement(By.id("wp-submit")).click();
        Thread.sleep(3000);
        try {
            assertEquals("testUser", driver.findElement(By.cssSelector("span.display-name")).getText()); // verify Login
            System.out.println("");
            System.out.println("              My Outputs from System.out.println                       ");
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("Login successful, username was found --> was verified!");
        } catch (Error e) {
            System.out.println("Login not successful, Username can not be found --> was not verified!");
            verificationErrors.append(e.toString());
        }
        driver.get(baseUrl + "wordpress/wp-admin/users.php");
        Thread.sleep(2000);
        
        // verify Fehlermeldung mit VerifyElementPresent
        try {
            assertTrue(isElementPresent(By.cssSelector("h1")));                                          
            System.out.println("Fehlermeldung: You need a higher level of permission.  --> was verified!");
            System.out.println("-----------------------------------------------------------------------");
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        
//        // verify Fehlermeldung mit VerifyText
//        try {
//            assertEquals("You need a higher level of permission.", driver.findElement(By.cssSelector("h1")).getText());
//        } catch (Error e) {
//            verificationErrors.append(e.toString());
//        }
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
