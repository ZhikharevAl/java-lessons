package ru.lesson.driver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.ScreenOrientation;
import ru.lesson.config.MobileConfig;

import java.net.MalformedURLException;
import java.net.URI;

public final class DriverFactory {
    private DriverFactory() {
    }

    public static AndroidDriver create() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("Android")
                .setUdid(MobileConfig.DEVICE_UDID)
                .setAppPackage(MobileConfig.APP_PACKAGE)
                .setAppActivity(MobileConfig.APP_ACTIVITY)
                .setNoReset(true)
                .setOrientation(ScreenOrientation.PORTRAIT)
                .setNewCommandTimeout(MobileConfig.COMMAND_TIMEOUT);
        options.setCapability("appium:forceAppLaunch", true);
        return new AndroidDriver(URI.create(MobileConfig.APPIUM_URL).toURL(), options);
    }
}
