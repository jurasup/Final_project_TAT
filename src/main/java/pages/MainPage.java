package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Represents home page Page Object Model.
 * @author Yury Suponev
 */
public class MainPage extends Page {
    public static final String TITLE = "WordPressTest – Just another WordPress site";
    private static final String URL = DOMAIN;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Allows to get current number of posts.
     * @return number of posts
     */
    public int getPostsNumber() {
        openPage();
        List<WebElement> posts = findElementsByCss("article");
        return posts.size();
    }

    @Override
    public void openPage() {
        driver.get(URL);
    }

    @Override
    public boolean isOnPage() {
        return driver.getTitle().equals(TITLE);
    }
}
