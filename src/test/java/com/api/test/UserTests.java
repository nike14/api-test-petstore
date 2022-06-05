package com.api.test;

import com.api.core.constants.Constant;
import com.api.core.pojo.CreateUserPojo;
import com.api.core.utils.Request;
import com.api.core.utils.RequestExecutor;
import com.api.helper.BuildRequest;
import com.api.helper.TestSuiteHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.List;


public class UserTests {
    TestSuiteHelper testSuiteHelper = new TestSuiteHelper();
    List<CreateUserPojo> userBody;
    RequestExecutor requestExecutor = new RequestExecutor();

    @Test(priority = 1)
    public void createUser(ITestContext testContext) throws Exception {
        userBody= testSuiteHelper.getUserBody();
        System.out.println(userBody.get(0).getUserName());
        String user= new com.google.gson.Gson().toJson(userBody);
        //ObjectMapper obj = new ObjectMapper();
        //String user = obj.writerWithDefaultPrettyPrinter().writeValueAsString(userBody);
        BuildRequest buildRequest = new BuildRequest();
        buildRequest.setContentType(ContentType.JSON);
        buildRequest.setRequestBody(user);
        buildRequest.setRequestType(Method.POST);
        buildRequest.setBaseUrl(Constant.baseUri);
        buildRequest.setApiPath(Constant.User.createUserWithArray);
        Request apiRequest = buildRequest.buildRequestObject();
        Response apiResponse = requestExecutor.executeRequest(apiRequest);
        testSuiteHelper.setITextContext(testContext, apiRequest, apiResponse);
        Assert.assertEquals(apiResponse.getStatusCode() , 200 , "Correct status code returned");
    }
}
