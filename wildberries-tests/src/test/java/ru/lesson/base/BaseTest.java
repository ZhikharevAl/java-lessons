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
import ru.lesson.config.BrowserConstants;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public abstract class BaseTest {
    private static final Path FAILURE_ARTIFACTS_DIRECTORY = Path.of("target", "failure-artifacts");

    protected WebDriver driver;

    @RegisterExtension
    protected final AfterTestExecutionCallback failureArtifacts = context -> {
        if (driver != null && context.getExecutionException().isPresent()) {
            try {
                Path directory = Files.createDirectories(FAILURE_ARTIFACTS_DIRECTORY.resolve(
                        context.getRequiredTestClass().getSimpleName()));
                String name = context.getRequiredTestMethod().getName();
                Files.write(directory.resolve(name + ".png"),
                        ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
                Files.writeString(directory.resolve(name + ".html"), driver.getPageSource());
                Files.writeString(directory.resolve(name + ".url.txt"), driver.getCurrentUrl());
            } catch (Exception artifactError) {
                context.getExecutionException().orElseThrow().addSuppressed(artifactError);
            }
        }
    };

    @BeforeEach
    protected final void setUpBrowser() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=" + BrowserConstants.WINDOW_WIDTH + "," + BrowserConstants.WINDOW_HEIGHT,
                "--lang=ru");
        String debuggerAddress = System.getProperty("debuggerAddress", "");
        if (!debuggerAddress.isBlank()) {
            options.setExperimentalOption("debuggerAddress", debuggerAddress);
        } else if (Boolean.parseBoolean(System.getProperty("headless", "true"))) {
            options.addArguments("--headless=new");
        }
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        driver = new ChromeDriver(options);
        if (!debuggerAddress.isBlank()) {
            ChromeDriver chrome = (ChromeDriver) driver;
            chrome.executeCdpCommand("Emulation.clearDeviceMetricsOverride", Map.of());
            Map<String, Object> window = chrome.executeCdpCommand("Browser.getWindowForTarget", Map.of());
            chrome.executeCdpCommand("Browser.setWindowBounds", Map.of("windowId", window.get("windowId"),
                    "bounds", Map.of("width", BrowserConstants.WINDOW_WIDTH, "height", BrowserConstants.WINDOW_HEIGHT)));
        }
        driver.manage().timeouts().pageLoadTimeout(BrowserConstants.PAGE_LOAD_TIMEOUT);
    }

    @AfterEach
    protected final void tearDownBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
