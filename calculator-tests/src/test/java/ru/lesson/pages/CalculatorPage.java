package ru.lesson.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.lesson.config.MobileConfig;
import ru.lesson.model.Operation;

import java.util.Map;

public final class CalculatorPage extends BasePage {
    private static final String RESOURCE_PREFIX = MobileConfig.APP_PACKAGE + ":id/";
    private static final String DIGIT_PREFIX = "digit_";
    private static final String CLEAR_BUTTON_ID = "clr";
    private static final By CLEAR = id(CLEAR_BUTTON_ID);
    private static final By DELETE = id("del");
    private static final By EQUALS = id("eq");
    private static final By FINAL_RESULT = id("result_final");

    public CalculatorPage(AndroidDriver driver) {
        super(driver);
    }

    public CalculatorPage calculate(String left, Operation operation, String right) {
        Allure.step("Очистить калькулятор", this::clear);
        Allure.step("Ввести первое число: " + left, () -> enterNumber(left));
        Allure.step("Выбрать операцию: " + operation, () -> click(id(operation.buttonId())));
        Allure.step("Ввести второе число: " + right, () -> enterNumber(right));
        Allure.step("Нажать равно", () -> click(EQUALS));
        return this;
    }

    public String displayedResult() {
        return Allure.step("Прочитать итоговый результат с экрана", () -> wait.until(currentDriver -> {
            WebElement result = currentDriver.findElement(FINAL_RESULT);
            String text = result.getText();
            return result.isDisplayed() && !text.isBlank() ? text : null;
        }));
    }

    private void clear() {
        WebElement button = wait.until(currentDriver -> {
            for (By locator : new By[]{CLEAR, DELETE}) {
                for (WebElement element : currentDriver.findElements(locator)) {
                    if (element.isDisplayed() && element.isEnabled()) {
                        return element;
                    }
                }
            }
            return null;
        });
        if (button.getAttribute("resource-id").equals(RESOURCE_PREFIX + CLEAR_BUTTON_ID)) {
            button.click();
        } else {
            driver.executeScript("mobile: longClickGesture", Map.of(
                    "elementId", ((RemoteWebElement) button).getId(),
                    "duration", MobileConfig.LONG_PRESS_DURATION.toMillis()));
        }
    }

    private void enterNumber(String number) {
        if (!number.matches("[0-9]+")) {
            throw new IllegalArgumentException("Операнд должен быть целым неотрицательным числом: " + number);
        }
        for (char digit : number.toCharArray()) {
            click(id(DIGIT_PREFIX + digit));
        }
    }

    private static By id(String name) {
        return AppiumBy.id(RESOURCE_PREFIX + name);
    }
}
