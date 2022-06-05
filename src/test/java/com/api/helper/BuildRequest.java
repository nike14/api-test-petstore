package com.api.helper;

import com.api.core.utils.Request;
import io.restassured.http.ContentType;
import io.restassured.http.Method;

import java.util.Map;

public class BuildRequest {

    private Map<String, Object> queryParameters;
    private String apiPath;
    private String baseUrl;
    private Method requestType;
    private Map<String, String> headers;
    private ContentType contentType;
    private String requestBody;

    public void setQueryParameters(Map<String, Object> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setRequestType(Method requestType) {
        this.requestType = requestType;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public Request buildRequestObject() {
        return new Request(this.baseUrl,this.apiPath, this.requestType, this.headers, this.requestBody, this.queryParameters,this.contentType
        );
    }
}
