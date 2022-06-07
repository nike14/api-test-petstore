package com.api.core.utils;

import io.restassured.http.ContentType;
import io.restassured.http.Method;

import java.util.Map;

public class Request {

    public Map<String, Object> queryParameters;
    public String apiPath;
    public String baseUrl;
    public Method requestType;
    public Map<String, String> headers;
    public ContentType contentType;
    public String requestBody;


    public Request(String baseUrl, String apiPath, Method requestType,
                   Map<String, String> headers,
                   String requestBody, Map<String, Object> queryParameters, ContentType contentType
    ) {
        this.baseUrl = baseUrl;
        this.requestType = requestType;
        this.apiPath = apiPath;
        this.headers = headers;
        this.contentType = contentType;
        this.requestBody = requestBody;
        this.queryParameters = queryParameters;
    }
}
