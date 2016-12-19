import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.MainPage;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Tests for main page.
 * @author Yury Suponev
 */
public class MainPageTest {
    private WebDriver driver;

    @BeforeClass
    public void setUpTestClass() {
        //System.setProperty("webdriver.gecko.driver", "src\\main\\resources\\geckodriver.exe");
        driver = new HtmlUnitDriver();
    }

    @AfterClass
    public void tearDownTestClass() {
        driver.quit();
    }

    @Test
    public void openHomePageTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openPage();
        assertTrue(mainPage.isOnPage());
    }
}
