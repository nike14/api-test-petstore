package com.api.core.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RequestExecutor {

    public Response executeRequest(Request request) {
        RequestSpecification requestSpecification = generateRequestSpec(request);
        Response response = given().spec(requestSpecification).request(request.requestType);
        return response;
    }

    private RequestSpecification generateRequestSpec(Request request) {
        RequestSpecBuilder builder = new RequestSpecBuilder();

        if (request.baseUrl != null) {
            builder.setBaseUri(request.baseUrl);
        }

        if (request.apiPath != null) {
            builder.setBasePath(request.apiPath);
        }

        if (request.contentType != null) {
            builder.setContentType(request.contentType);
        }

        if (request.requestBody != null) {
            builder.setBody(request.requestBody);
        }

        if (request.headers != null) {
            builder.addHeaders(request.headers);
        }
        if (request.queryParameters != null) {
            builder.addQueryParams(request.queryParameters);
        }

        RequestSpecification requestSpec = builder.build();
        return requestSpec;
    }
}
