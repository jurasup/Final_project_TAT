package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.common.base.Predicate;

import java.util.concurrent.TimeUnit;

/**
 * Represents login page Page Object Model.
 * @author Yury Suponev
 */
public class LoginPage extends Page {
    private static final String TITLE = "Log In";
    private static final String URL = DOMAIN + "wp-login.php/";

    private static final String USER_LOGIN = "user_login";
    private static final String USER_PASSWORD = "user_pass";
    private static final String LOGIN_BUTTON = "wp-submit";
    private static final String ERROR_MESSAGE = "login_error";

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    private void enterUsername(String username) {
        WebElement loginField = findById(USER_LOGIN);
        loginField.sendKeys("");
        loginField.sendKeys(username);
    }

    private void enterPassword(String password) {
        WebElement passwordField = findById(USER_PASSWORD);
        passwordField.sendKeys("");
        passwordField.sendKeys(password);
    }

    private void submitLogin() {
        findById(LOGIN_BUTTON).submit();
    }

    /**
     * Allows to login as user with defined username and password.
     * @param username
     * @param password
     */
    public void loginAs(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        submitLogin();
    }

    /**
     * Allows to check if login was failed.
     * @return true if login was failed, otherwise return false
     */
    public boolean isLoginFailed() {
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        try {
            wait.until(ExpectedConditions.visibilityOf(findById(ERROR_MESSAGE)));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Allows to check if login was successful.
     * @return true if login was successful, otherwise return false
     */
    public boolean isLoginSuccessful() {
        WebDriverWait wait = new WebDriverWait(driver, 10, 1000);
        try {
            wait.until(new Predicate<WebDriver>() {
                public boolean apply(WebDriver input) {
                    return driver.getTitle().contains(DashboardPage.TITLE) ||
                            driver.getTitle().contains(ProfilePage.TITLE);
                }
            });
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    @Override
    public void openPage() {
        driver.get(URL);
    }

    @Override
    public boolean isOnPage() {
        return driver.getTitle().contains(TITLE);
    }
}
