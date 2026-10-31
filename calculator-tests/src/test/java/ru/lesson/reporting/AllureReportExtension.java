package ru.lesson.reporting;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import ru.lesson.config.MobileConfig;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.function.Supplier;

public final class AllureReportExtension implements AfterTestExecutionCallback {
    private final Supplier<AndroidDriver> driverSupplier;

    public AllureReportExtension(Supplier<AndroidDriver> driverSupplier) {
        this.driverSupplier = driverSupplier;
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        AndroidDriver driver = driverSupplier.get();
        if (driver == null) {
            return;
        }
        attachSafely("Снимок экрана", () -> Allure.addAttachment(
                "Снимок экрана", "image/png",
                new ByteArrayInputStream(driver.getScreenshotAs(OutputType.BYTES)), "png"));

        if (context.getExecutionException().isPresent()) {
            attachSafely("Иерархия элементов при падении", () -> Allure.addAttachment(
                    "Иерархия элементов при падении", "application/xml",
                    new ByteArrayInputStream(driver.getPageSource().getBytes(StandardCharsets.UTF_8)), "xml"));
        }
    }

    public static void writeEnvironment() throws IOException {
        Path results = Path.of(System.getProperty("allure.results.directory", "target/allure-results"));
        Files.createDirectories(results);
        Properties environment = new Properties();
        environment.setProperty("Platform", "Android");
        environment.setProperty("Application.Package", MobileConfig.APP_PACKAGE);
        environment.setProperty("Device.UDID", MobileConfig.DEVICE_UDID);
        environment.setProperty("Java.Version", System.getProperty("java.version"));
        try (OutputStream output = Files.newOutputStream(results.resolve("environment.properties"))) {
            environment.store(output, "Calculator test environment");
        }
    }

    private void attachSafely(String name, Runnable attachment) {
        try {
            attachment.run();
        } catch (RuntimeException error) {
            Allure.addAttachment("Не удалось добавить: " + name, error.toString());
        }
    }
}
