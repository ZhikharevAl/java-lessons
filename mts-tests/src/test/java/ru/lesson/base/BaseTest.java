package ru.lesson.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public abstract class BaseTest {
    protected WebDriver driver;

    @RegisterExtension
    protected final AfterTestExecutionCallback failureArtifacts = context -> {
        if (driver != null && context.getExecutionException().isPresent()) {
            try {
                Path directory = Files.createDirectories(Path.of("target", "failure-artifacts",
                        context.getRequiredTestClass().getSimpleName()));
                String name = context.getRequiredTestMethod().getName();
                Files.write(directory.resolve(name + ".png"),
                        ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
                Files.writeString(directory.resolve(name + ".html"), driver.getPageSource());
            } catch (Exception artifactError) {
                context.getExecutionException().orElseThrow().addSuppressed(artifactError);
            }
        }
    };

    @BeforeEach
    protected final void setUpBrowser() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080", "--lang=ru");
        if (Boolean.parseBoolean(System.getProperty("headless", "true"))) {
            options.addArguments("--headless=new");
        }
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(45));
    }

    @AfterEach
    protected final void tearDownBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
