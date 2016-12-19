package pages;

import org.openqa.selenium.WebDriver;

/**
 * Represents profile page Page Object Model.
 * @author Yury Suponev
 */
public class ProfilePage extends Page {
    public static final String TITLE = "Profile";


    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void openPage() {

    }

    @Override
    public boolean isOnPage() {
        return false;
    }
}
