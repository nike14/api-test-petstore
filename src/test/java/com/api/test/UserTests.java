package com.api.test;

import com.api.core.constants.Constant;
import com.api.core.pojo.CreateUserPojo;
import com.api.core.utils.Request;
import com.api.core.utils.RequestExecutor;
import com.api.helper.BuildRequest;
import com.api.helper.TestSuiteHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class UserTests {
    TestSuiteHelper testSuiteHelper = new TestSuiteHelper();
    List<CreateUserPojo> userBody;
    RequestExecutor requestExecutor = new RequestExecutor();
    CreateUserPojo cuObj;

    /*
    - Jackson's lib converts "username" key to "userName" and endpoint gives success response
    - When we search that username doesn't appear in get API call.
    - This is issue with endpoint and Jackson's lib.

    Solution- Use Gson API for conversion.
    */
    @Test(priority = 1)
    public void endPointIssueForCreateUser(ITestContext testContext) throws JsonProcessingException {
        cuObj = testSuiteHelper.getUserBody();
        userBody = testSuiteHelper.addUserObjectToList(cuObj);
        ObjectMapper obj = new ObjectMapper();
        String user = obj.writerWithDefaultPrettyPrinter().writeValueAsString(userBody);
        BuildRequest buildRequest = new BuildRequest();
        buildRequest.setContentType(ContentType.JSON);
        buildRequest.setRequestBody(user);
        buildRequest.setRequestType(Method.POST);
        buildRequest.setBaseUrl(Constant.baseUri);
        buildRequest.setApiPath(Constant.User.createUserWithArray);
        Request apiRequest = buildRequest.buildRequestObject();
        Response apiResponse = requestExecutor.executeRequest(apiRequest);
        testSuiteHelper.setITextContext(testContext, apiRequest, apiResponse);
        Assert.assertEquals(apiResponse.getStatusCode(), 200, "Correct status code returned");
    }

    @Test(priority = 2)
    public void createUser(ITestContext testContext) {
        cuObj = testSuiteHelper.getUserBody();
        userBody = testSuiteHelper.addUserObjectToList(cuObj);
        System.out.println(userBody.get(0).getUserName());
        String user = new com.google.gson.Gson().toJson(userBody);
        BuildRequest buildRequest = new BuildRequest();
        buildRequest.setContentType(ContentType.JSON);
        buildRequest.setRequestBody(user);
        buildRequest.setRequestType(Method.POST);
        buildRequest.setBaseUrl(Constant.baseUri);
        buildRequest.setApiPath(Constant.User.createUserWithArray);
        Request apiRequest = buildRequest.buildRequestObject();
        Response apiResponse = requestExecutor.executeRequest(apiRequest);
        testSuiteHelper.setITextContext(testContext, apiRequest, apiResponse);
        Assert.assertEquals(apiResponse.getStatusCode(), 200, "Correct status code returned");
    }

    @Test(priority = 3)
    public void updateUserName(ITestContext testContext) {
        String userName = userBody.get(0).getUserName();
        cuObj = testSuiteHelper.getUserBody();
        String user = new com.google.gson.Gson().toJson(cuObj);
        BuildRequest buildRequest = new BuildRequest();
        buildRequest.setContentType(ContentType.JSON);
        buildRequest.setRequestBody(user);
        buildRequest.setRequestType(Method.PUT);
        buildRequest.setBaseUrl(Constant.baseUri);
        buildRequest.setApiPath(Constant.User.updateUserName.replace("{username}", userName));
        Request apiRequest = buildRequest.buildRequestObject();
        Response apiResponse = requestExecutor.executeRequest(apiRequest);
        testSuiteHelper.setITextContext(testContext, apiRequest, apiResponse);
        Assert.assertEquals(apiResponse.getStatusCode(), 200, "Correct status code returned");
    }

    @Test(priority = 4)
    public void getUserDetails(ITestContext testContext){
        String userName = cuObj.getUserName();
        BuildRequest buildRequest = new BuildRequest();
        buildRequest.setContentType(ContentType.JSON);
        buildRequest.setRequestType(Method.GET);
        buildRequest.setBaseUrl(Constant.baseUri);
        buildRequest.setApiPath(Constant.User.getUserName.replace("{username}", userName));
        Request apiRequest = buildRequest.buildRequestObject();
        Response apiResponse = requestExecutor.executeRequest(apiRequest);
        testSuiteHelper.setITextContext(testContext, apiRequest, apiResponse);
        Assert.assertEquals(apiResponse.getStatusCode(), 200, "Correct status code returned");
        testSuiteHelper.assertGetUser(apiResponse, cuObj);
    }

    /*Negative scenario
    Endpoint missing with validation for empty list of object.
    Also, no key is mandatory because of this we can't search name using get API.

    Same issue for post and put user.
     */
    @Test(priority = 5)
    public void createUserWithoutAnyKey(ITestContext testContext){
        BuildRequest buildRequest = new BuildRequest();
        buildRequest.setContentType(ContentType.JSON);
        buildRequest.setRequestBody("[{}]");
        buildRequest.setRequestType(Method.POST);
        buildRequest.setBaseUrl(Constant.baseUri);
        buildRequest.setApiPath(Constant.User.createUserWithArray);
        Request apiRequest = buildRequest.buildRequestObject();
        Response apiResponse = requestExecutor.executeRequest(apiRequest);
        testSuiteHelper.setITextContext(testContext, apiRequest, apiResponse);
        Assert.assertEquals(apiResponse.getStatusCode(), 400, "Bad Request");
    }
}
