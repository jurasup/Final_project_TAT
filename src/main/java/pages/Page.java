package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Represent abstract Page Object Model.
 * @author Yury Suponev
 */
public abstract class Page {
    protected WebDriver driver;
    protected static final String DOMAIN = "http://localhost:8888/";

    /**
     * Provides opening page.
     */
    public abstract void openPage();

    /**
     * Verifies if this page is current.
     */
    public abstract boolean isOnPage();

    /*
    Methods for finding elements by different locators.
     */
    protected List<WebElement> findElementsById(String id) {
        return driver.findElements(By.id(id));
    }

    protected List<WebElement> findElementsByName(String name) {
        return driver.findElements(By.name(name));
    }

    protected List<WebElement> findElementsByLink(String linkText) {
        return driver.findElements(By.linkText(linkText));
    }

    protected List<WebElement> findElementsByTagName(String tagName) {
        return driver.findElements(By.tagName(tagName));
    }

    protected List<WebElement> findElementsByCss(String css) {
        return driver.findElements(By.cssSelector(css));
    }

    protected List<WebElement> findElementsByXpath(String xpath) {
        return driver.findElements(By.xpath(xpath));
    }

    protected WebElement findById(String id) {
        List<WebElement> links = findElementsById(id);
        if (links.size() > 0) {
            return links.get(0);
        }
        return null;
    }

    protected WebElement findByName(String name) {
        List<WebElement> links = findElementsByName(name);
        if (links.size() > 0) {
            return links.get(0);
        }
        return null;
    }

    protected WebElement findByTagName(String tagName) {
        List<WebElement> links = findElementsByTagName(tagName);
        if (links.size() > 0) {
            return links.get(0);
        }
        return null;
    }

    protected WebElement findByCss(String css) {
        List<WebElement> links = findElementsByCss(css);
        if (links.size() > 0) {
            return links.get(0);
        }
        return null;
    }

    protected WebElement findByXpath(String xpath) {
        List<WebElement> links = findElementsByXpath(xpath);
        if (links.size() > 0) {
            return links.get(0);
        }
        return null;
    }
}
