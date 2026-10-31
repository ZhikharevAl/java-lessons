package ru.lesson.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import ru.lesson.client.EchoClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.lesson.config.ApiConstants.OK_STATUS;

public abstract class BaseApiTest {
    protected final EchoClient client = new EchoClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    protected void assertResponse(Response response, String fixtureName) throws IOException {
        JsonNode expected;
        try (InputStream fixture = Objects.requireNonNull(
                getClass().getResourceAsStream("/expected/" + fixtureName + ".json"),
                "Не найден ожидаемый ответ: " + fixtureName)) {
            expected = objectMapper.readTree(fixture);
        }
        assertAll("Код ответа и полное тело JSON",
                () -> assertEquals(OK_STATUS, response.statusCode(), "Код ответа"),
                () -> assertEquals(expected, objectMapper.readTree(response.asString()),
                        "Все поля тела ответа, включая вложенные headers"));
    }
}
