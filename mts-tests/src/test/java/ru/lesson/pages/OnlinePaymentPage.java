package ru.lesson.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.lesson.base.BasePage;

import java.time.Duration;
import java.util.List;

public class OnlinePaymentPage extends BasePage {
    private static final String BASE_URL = "https://www.mts.by/";

    private final By blockTitle = By.cssSelector("section.pay h2");
    private final By partners = By.cssSelector("section.pay .pay__partners");
    private final By partnerLogos = By.cssSelector("section.pay .pay__partners img");
    private final By moreInfoLink = By.xpath("//section[contains(@class, 'pay')]//a[normalize-space()='Подробнее о сервисе']");
    private final By serviceHeader = By.cssSelector("section.pay .select__header");
    private final By connectionOption = By.xpath("//section[contains(@class, 'pay')]//li[contains(@class, 'select__item')][normalize-space()='Услуги связи']");
    private final By selectedService = By.cssSelector("section.pay .select__now");
    private final By connectionForm = By.cssSelector("#pay-connection.opened");
    private final By connectionPhone = By.id("connection-phone");
    private final By connectionSum = By.id("connection-sum");
    private final By connectionEmail = By.id("connection-email");
    private final By continueButton = By.cssSelector("#pay-connection button[type='submit']");
    private final By acceptCookies = By.id("cookie-agree");
    private final By cookieBanner = By.cssSelector(".cookie.show");

    public OnlinePaymentPage(WebDriver driver) {
        super(driver);
    }

    public OnlinePaymentPage open() {
        driver.get(BASE_URL);
        visible(blockTitle);
        visible(connectionForm);
        dismissCookieBanner();
        scrollTo(visible(blockTitle));
        return this;
    }

    public String getBlockTitle() {
        return getTextContent(blockTitle);
    }

    public List<String> getPartnerLogoNames() {
        scrollTo(visible(partners));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(partnerLogos))
                .stream().map(logo -> logo.getDomAttribute("alt")).toList();
    }

    public boolean isPartnerLogoDisplayed(String name) {
        return findPartnerLogo(name).isDisplayed();
    }

    public boolean isPartnerLogoLoaded(String name) {
        WebElement logo = findPartnerLogo(name);
        scrollTo(logo);
        return wait.withMessage("Не загрузился логотип " + name)
                .until(d -> Boolean.TRUE.equals(((JavascriptExecutor) d).executeScript(
                        "return arguments[0].complete && arguments[0].naturalWidth > 0 && arguments[0].naturalHeight > 0;", logo)));
    }

    public String getMoreInfoHref() {
        return visible(moreInfoLink).getAttribute("href");
    }

    public ServiceInfoPage clickMoreInfo() {
        click(moreInfoLink);
        return new ServiceInfoPage(driver);
    }

    public void selectConnectionService() {
        click(serviceHeader);
        click(connectionOption);
        wait.until(ExpectedConditions.textToBe(selectedService, "Услуги связи"));
        visible(connectionForm);
    }

    public String getSelectedService() {
        return getText(selectedService);
    }

    public void fillConnectionForm(String phone, String sum, String email) {
        enter(connectionPhone, phone);
        enter(connectionSum, sum);
        enter(connectionEmail, email);
    }

    public PaymentWidgetPage clickContinue() {
        click(continueButton);
        return new PaymentWidgetPage(driver);
    }

    private WebElement findPartnerLogo(String name) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("section.pay .pay__partners img[alt='" + name + "']")));
    }

    private void dismissCookieBanner() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(acceptCookies));
        } catch (TimeoutException noBanner) {
            // Баннер может отсутствовать после сохранения согласия.
            return;
        }
        driver.findElement(acceptCookies).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(cookieBanner));
    }
}
