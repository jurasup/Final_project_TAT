package pages;

import org.openqa.selenium.WebDriver;

/**
 * Represents dashboard page Page Object Model.
 * @author Yury Suponev
 */
public class DashboardPage extends Page {
    public static final String TITLE = "Dashboard";
    private static final String URL = DOMAIN + "wp-admin/";
    private static final String ADD_POST_PREFIX = "post-new.php";
    private static final String POST_TITLE = "title";
    private static final String CONTENT_FIELD = "content";
    private static final String PUBLISH = "publish";
    private static final String POST_PERMALINK = "sample-permalink";

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Allows to add new post.
     * @param title
     * @param content
     * @return link to the added post
     */
    public String addNewPost(String title, String content) {
        driver.get(URL + ADD_POST_PREFIX);
        findById(title).sendKeys(title);
        findById(content).sendKeys(content);
        findById(PUBLISH).submit();
        return findById(POST_PERMALINK).getAttribute("href");
    }

    @Override
    public void openPage() {
        driver.get(URL);
    }

    @Override
    public boolean isOnPage() {
        return false;
    }
}
