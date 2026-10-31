package ru.lesson.base;

import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.lesson.driver.DriverFactory;
import ru.lesson.pages.CalculatorPage;

import java.net.MalformedURLException;

public abstract class BaseTest {
    private AndroidDriver driver;
    protected CalculatorPage calculator;

    @BeforeEach
    void setUp() throws MalformedURLException {
        driver = DriverFactory.create();
        calculator = new CalculatorPage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
