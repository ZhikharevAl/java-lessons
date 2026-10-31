package ru.lesson.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.lesson.base.BasePage;

import java.time.Duration;

public class PaymentWidgetPage extends BasePage {
    private final By paymentFrame = By.cssSelector("iframe.payment-widget-iframe");
    private final By cardNumber = By.id("cc-number");
    private final By amount = By.cssSelector(".pay-description__cost");
    private final By description = By.cssSelector(".pay-description__text");

    public PaymentWidgetPage(WebDriver driver) {
        super(driver, Duration.ofSeconds(40));
        wait.withMessage("После нажатия «Продолжить» не появился платёжный виджет bePaid")
                .until(ExpectedConditions.visibilityOfElementLocated(paymentFrame));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(paymentFrame));
        wait.withMessage("Виджет bePaid не загрузил поле номера карты")
                .until(ExpectedConditions.visibilityOfElementLocated(cardNumber));
    }

    public String getAmount() {
        return getText(amount);
    }

    public String getDescription() {
        return getText(description);
    }
}
