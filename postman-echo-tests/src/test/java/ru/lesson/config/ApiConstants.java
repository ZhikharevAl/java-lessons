package ru.lesson.config;

public final class ApiConstants {
    public static final String BASE_URL = "https://postman-echo.com";
    public static final String USER_AGENT = "lessons-16-tests/1.0";
    public static final String ACCEPT = "application/json";
    public static final String ACCEPT_ENCODING = "identity";
    public static final String TEXT_CONTENT_TYPE = "text/plain";
    public static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";
    public static final int OK_STATUS = 200;
    public static final int CONNECT_TIMEOUT_MILLIS = 10_000;
    public static final int READ_TIMEOUT_MILLIS = 20_000;
    public static final String GET_PATH = "/get";
    public static final String POST_PATH = "/post";
    public static final String PUT_PATH = "/put";
    public static final String PATCH_PATH = "/patch";
    public static final String DELETE_PATH = "/delete";

    private ApiConstants() {
    }
}
