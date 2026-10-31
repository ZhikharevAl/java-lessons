package ru.lesson.config;

import java.time.Duration;

public final class MobileConfig {
    public static final String APP_PACKAGE = "com.google.android.calculator";
    public static final String APP_ACTIVITY = System.getProperty(
            "app.activity", "com.android.calculator2.Calculator");
    public static final String APPIUM_URL = System.getProperty("appium.url", "http://127.0.0.1:4723");
    public static final String DEVICE_UDID = System.getProperty("android.udid", "emulator-5554");
    public static final Duration ELEMENT_TIMEOUT = Duration.ofSeconds(15);
    public static final Duration COMMAND_TIMEOUT = Duration.ofSeconds(60);
    public static final Duration LONG_PRESS_DURATION = Duration.ofSeconds(1);

    private MobileConfig() {
    }
}
