package ru.lesson.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.lesson.base.BasePage;
import ru.lesson.model.PaymentSystem;

import java.time.Duration;
import java.util.List;

public class PaymentWidgetPage extends BasePage {
    private final By paymentFrame = By.cssSelector("iframe.payment-widget-iframe");
    private final By cardNumber = By.id("cc-number");
    private final By amount = By.cssSelector(".pay-description__cost");
    private final By description = By.cssSelector(".pay-description__text");
    private final By payButton = By.cssSelector("button[type='submit']");
    private final By cardFieldLabels = By.cssSelector("app-input .content > label");
    private final By cardFields = By.cssSelector("app-input input[formcontrolname]");
    private final By expirationDate = By.cssSelector("input[autocomplete='cc-exp']");

    public PaymentWidgetPage(WebDriver driver) {
        super(driver, Duration.ofSeconds(40));
        wait.withMessage("После нажатия «Продолжить» не появился платёжный виджет bePaid")
                .until(ExpectedConditions.visibilityOfElementLocated(paymentFrame));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(paymentFrame));
        wait.withMessage("Виджет bePaid не загрузил поле номера карты")
                .until(ExpectedConditions.visibilityOfElementLocated(cardNumber));
        // Имя держателя и кнопка появляются позже поля номера карты.
        visible(payButton);
    }

    public String getAmount() {
        return getText(amount);
    }

    public String getDescription() {
        return getText(description);
    }

    public String getPayButtonText() {
        return getText(payButton);
    }

    public List<String> getCardFieldLabels() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cardFieldLabels))
                .stream().map(label -> normalize(label.getText())).toList();
    }

    public List<String> getCardFieldValues() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cardFields))
                .stream().map(input -> input.getDomProperty("value")).toList();
    }

    public String getExpirationPlaceholder() {
        return visible(expirationDate).getDomAttribute("placeholder");
    }

    public boolean isPaymentSystemIconDisplayed(PaymentSystem system) {
        // Maestro и МИР показываются по очереди: ожидаем появления каждой иконки.
        return visible(paymentSystemIcon(system)).isDisplayed();
    }

    public boolean isPaymentSystemIconLoaded(PaymentSystem system) {
        return isImageLoaded(wait.until(ExpectedConditions.presenceOfElementLocated(paymentSystemIcon(system))));
    }

    private By paymentSystemIcon(PaymentSystem system) {
        return By.cssSelector(".cards-brands__container img[src$='" + system.getIconFile() + "']");
    }
}
