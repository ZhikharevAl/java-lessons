package ru.lesson.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.lesson.base.BaseApiTest;

import java.io.IOException;

@DisplayName("Postman Echo — Request Methods")
class RequestMethodsTest extends BaseApiTest {
    @Test
    @DisplayName("GET Request: код 200 и все поля тела ответа")
    void getRequest() throws IOException {
        assertResponse(client.get(), "get");
    }

    @Test
    @DisplayName("POST Raw Text: код 200 и все поля тела ответа")
    void postRawText() throws IOException {
        assertResponse(client.postRawText(), "post-raw-text");
    }

    @Test
    @DisplayName("POST Form Data: код 200 и все поля тела ответа")
    void postFormData() throws IOException {
        assertResponse(client.postFormData(), "post-form-data");
    }

    @Test
    @DisplayName("PUT Request: код 200 и все поля тела ответа")
    void putRequest() throws IOException {
        assertResponse(client.put(), "put");
    }

    @Test
    @DisplayName("PATCH Request: код 200 и все поля тела ответа")
    void patchRequest() throws IOException {
        assertResponse(client.patch(), "patch");
    }

    @Test
    @DisplayName("DELETE Request: код 200 и все поля тела ответа")
    void deleteRequest() throws IOException {
        assertResponse(client.delete(), "delete");
    }
}
