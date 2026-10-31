package ru.lesson.data;

public final class EchoTestData {
    public static final String PARAMETERS = "foo1=bar1&foo2=bar2";
    public static final String RAW_POST_BODY = "{\n    \"test\": \"value\"\n}";
    public static final String RAW_BODY = "This is expected to be sent back as part of response body.";

    private EchoTestData() {
    }
}
