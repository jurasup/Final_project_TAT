package pages;

import org.openqa.selenium.WebDriver;

/**
 * Represents post page Page Object Model.
 * @author Yury Suponev
 */
public class PostPage extends Page {
    private String url;
    private static final String COMMENTS = "#comments li";

    public PostPage(WebDriver driver, String postURL) {
        this.driver = driver;
        url = postURL;
    }

    public int getNumberOfComments() {
        return findElementsByCss(COMMENTS).size();
    }

    @Override
    public void openPage() {
        driver.get(url);
    }

    @Override
    public boolean isOnPage() {
        return driver.getCurrentUrl().equals(url);
    }
}
