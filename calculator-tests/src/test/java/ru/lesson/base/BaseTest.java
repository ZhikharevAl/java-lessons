package ru.lesson.base;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import ru.lesson.driver.DriverFactory;
import ru.lesson.pages.CalculatorPage;
import ru.lesson.reporting.AllureReportExtension;

import java.io.IOException;
import java.net.MalformedURLException;

public abstract class BaseTest {
    private AndroidDriver driver;
    protected CalculatorPage calculator;

    @RegisterExtension
    protected final AllureReportExtension allureReport = new AllureReportExtension(() -> driver);

    @BeforeAll
    static void prepareReportEnvironment() throws IOException {
        AllureReportExtension.writeEnvironment();
    }

    @BeforeEach
    void setUp() throws MalformedURLException {
        driver = Allure.step("Открыть Google Калькулятор", DriverFactory::create);
        calculator = new CalculatorPage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            Allure.step("Закрыть Appium-сессию", () -> driver.quit());
        }
    }
}
