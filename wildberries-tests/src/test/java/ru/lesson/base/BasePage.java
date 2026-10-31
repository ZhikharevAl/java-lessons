package ru.lesson.base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.lesson.config.BrowserConstants;

import java.time.Duration;
import java.util.regex.Pattern;

public abstract class BasePage {
    private static final String SCROLL_INTO_VIEW_SCRIPT = "arguments[0].scrollIntoView({block: 'center'});";
    private static final String ACCESS_BLOCKED_MESSAGE = "Подозрительная активность";
    private static final By ACCESS_MESSAGE = By.cssSelector(".support-subtitle");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s\\p{Zs}]+");

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(
                Long.getLong("uiTimeoutSeconds", BrowserConstants.DEFAULT_UI_TIMEOUT_SECONDS)));
    }

    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected String nonEmptyText(By locator) {
        return wait.until(d -> {
            WebElement element = d.findElement(locator);
            scrollTo(element);
            String text = normalize(element.getText());
            return element.isDisplayed() && !text.isBlank() ? text : null;
        });
    }

    protected void click(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollTo(element);
        element.click();
    }

    protected void scrollTo(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(SCROLL_INTO_VIEW_SCRIPT, element);
    }

    protected static String normalize(String text) {
        return WHITESPACE.matcher(text).replaceAll(" ").trim();
    }

    protected void checkSiteAccess() {
        for (WebElement message : driver.findElements(ACCESS_MESSAGE)) {
            if (message.isDisplayed() && message.getText().contains(ACCESS_BLOCKED_MESSAGE)) {
                throw new IllegalStateException("Wildberries заблокировал тестовый браузер: " + message.getText()
                        + ". Откройте отдельный тестовый Chrome вручную; см. README.");
            }
        }
    }
}
