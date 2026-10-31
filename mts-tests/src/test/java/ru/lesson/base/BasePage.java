package ru.lesson.base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this(driver, Duration.ofSeconds(20));
    }

    protected BasePage(WebDriver driver, Duration timeout) {
        this.driver = driver;
        wait = new WebDriverWait(driver, timeout);
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected String getText(By locator) {
        return normalize(visible(locator).getText());
    }

    protected String getTextContent(By locator) {
        // textContent сохраняет исходный регистр текста независимо от CSS text-transform.
        return normalize(visible(locator).getDomProperty("textContent"));
    }

    protected void click(By locator) {
        scrollTo(visible(locator));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void enter(By locator, String value) {
        WebElement input = visible(locator);
        scrollTo(input);
        input.clear();
        input.sendKeys(value);
    }

    protected void scrollTo(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    protected static String normalize(String text) {
        return text.replace('\u00a0', ' ').replaceAll("\\s+", " ").trim();
    }
}
