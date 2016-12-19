import database.DatabaseWorker;
import database.User;
import database.UserBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.ProfilePage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

/**
 * Tests for login page.
 * @author Yury Suponev
 */
public class LoginTest {
    private WebDriver driver;
    private DatabaseWorker worker = new DatabaseWorker();
    private List<User> users = new ArrayList<>();

    @BeforeClass
    public void setUp() {
        UserBuilder builder = new UserBuilder();
        users.add(builder.build(UserBuilder.ADMIN));
        users.add(builder.build(UserBuilder.EDITOR));
        users.add(builder.build(UserBuilder.AUTHOR));
        users.add(builder.build(UserBuilder.CONTRIBUTOR));
        users.add(builder.build(UserBuilder.SUBSCRIBER));
        for (User user : users) {
            worker.addUser(user);
        }
    }

    @AfterClass
    public void tearDown() {
        for (User user : users) {
            worker.deleteUser(user.getID());
        }
        driver.quit();
    }

    @BeforeMethod
    public void setUpDriver() {
        driver = new HtmlUnitDriver();
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void tearDownDriver() {
        driver.quit();
    }

    @DataProvider(name = "Users for valid login")
    public Object[][] validUsers() {
        return new Object[][]{
                {users.get(0)},
                {users.get(1)},
                {users.get(2)},
                {users.get(3)},
                {users.get(4)}
        };
    }

    @DataProvider(name = "Users for invalid login")
    public Object[][] invalidLoginData() {
        return new Object[][]{
                {users.get(0).getLogin(), ""},
                {users.get(0).getLogin(), "wrong"},
                {"wrong", users.get(0).getPassword()}
        };
    }

    @Test(dataProvider = "Users for valid login")
    public void positiveValidLogin(User user) throws Exception {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openPage();
        loginPage.loginAs(user.getLogin(), user.getPassword());
        assertTrue(loginPage.isLoginSuccessful());
    }

    @Test(dataProvider = "Users for invalid login")
    public void negativeInvalidLogin(String login, String password) throws Exception {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openPage();
        loginPage.loginAs(login, password);
        assertTrue(loginPage.isLoginFailed());
    }

    @Test
    public void openLoginPageTest() throws Exception {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openPage();
        assertTrue(loginPage.isOnPage());
    }
}
