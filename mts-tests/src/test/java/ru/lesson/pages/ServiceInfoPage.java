package ru.lesson.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.lesson.base.BasePage;

public class ServiceInfoPage extends BasePage {
    private static final String URL =
            "https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/";
    private final By paymentHeading = By.cssSelector(".container-fluid > h3");

    public ServiceInfoPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.urlToBe(URL));
        visible(paymentHeading);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getPaymentHeading() {
        return getTextContent(paymentHeading);
    }
}
