package ru.lesson.client;

import io.restassured.config.EncoderConfig;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static ru.lesson.config.ApiConstants.*;
import static ru.lesson.data.EchoTestData.*;

public final class EchoClient {
    private RequestSpecification request() {
        return given()
                .baseUri(BASE_URL)
                .config(RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .appendDefaultContentCharsetToContentTypeIfUndefined(false))
                        .httpClient(HttpClientConfig.httpClientConfig()
                                .setParam("http.connection.timeout", CONNECT_TIMEOUT_MILLIS)
                                .setParam("http.socket.timeout", READ_TIMEOUT_MILLIS)))
                .header("User-Agent", USER_AGENT)
                .header("Accept", ACCEPT)
                .header("Accept-Encoding", ACCEPT_ENCODING);
    }

    public Response get() {
        return request().get(GET_PATH + "?" + PARAMETERS);
    }

    public Response postRawText() {
        return sendText(Method.POST, POST_PATH, RAW_POST_BODY);
    }

    public Response postFormData() {
        return request().contentType(FORM_CONTENT_TYPE).body(PARAMETERS).post(POST_PATH);
    }

    public Response put() {
        return sendText(Method.PUT, PUT_PATH, RAW_BODY);
    }

    public Response patch() {
        return sendText(Method.PATCH, PATCH_PATH, RAW_BODY);
    }

    public Response delete() {
        return sendText(Method.DELETE, DELETE_PATH, RAW_BODY);
    }

    private Response sendText(Method method, String path, String body) {
        return request().contentType(TEXT_CONTENT_TYPE).body(body).request(method, path);
    }
}
