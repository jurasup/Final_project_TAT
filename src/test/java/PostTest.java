import database.DatabaseWorker;
import database.User;
import database.UserBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.MainPage;

import static org.testng.Assert.assertTrue;

/**
 * Tests for adding posts and comments.
 * @author Yury Suponev
 */
public class PostTest {
    private WebDriver driver;
    private DatabaseWorker worker = new DatabaseWorker();
    private User user;
    private String postTitle = "Test post title";
    private String postContent = "Test post content";


    @BeforeClass
    public void setUp() {
        driver = new HtmlUnitDriver();
        UserBuilder builder = new UserBuilder();
        user = builder.build(UserBuilder.AUTHOR);
        worker.addUser(user);
    }

    @AfterClass
    public void tearDown() {
        worker.deleteUser(user.getID());
        driver.quit();
    }

    @Test
    public void positiveAddPostTest() throws Exception {
        MainPage mainPage = new MainPage(driver);
        int initialPostsNumber = mainPage.getPostsNumber();
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.addNewPost(postTitle, postContent);
        int resultingPostsNumber = mainPage.getPostsNumber();
        assertTrue(resultingPostsNumber > initialPostsNumber);
    }
}
