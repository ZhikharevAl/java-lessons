package ru.lesson.config;

import java.time.Duration;

public final class BrowserConstants {
    public static final int WINDOW_WIDTH = 1920;
    public static final int WINDOW_HEIGHT = 1080;
    public static final long DEFAULT_UI_TIMEOUT_SECONDS = 45;
    public static final Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(45);

    private BrowserConstants() {
    }
}
