package com.api.test;

import com.api.core.constants.Constant;
import com.api.core.pojo.CreatePetPojo;
import com.api.core.utils.Request;
import com.api.core.utils.RequestExecutor;
import com.api.helper.BuildRequest;
import com.api.helper.TestSuiteHelper;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetTests {

    TestSuiteHelper testSuiteHelper = new TestSuiteHelper();
    RequestExecutor requestExecutor = new RequestExecutor();
    List<CreatePetPojo> createPetPojoList = new ArrayList<>();
    CreatePetPojo cpObj;

    @Test(priority = 1)
    public void createFirstPet(ITestContext testContext) {
        cpObj = testSuiteHelper.getPetBody(Constant.PetConstant.newUser);
        createPetPojoList.add(cpObj);
        String petBody = new com.google.gson.Gson().toJson(cpObj);
        BuildRequest buildRequest = new BuildRequest();
        buildRequest.setContentType(ContentType.JSON);
        buildRequest.setRequestBody(petBody);
        buildRequest.setRequestType(Method.POST);
        buildRequest.setBaseUrl(Constant.baseUri);
        buildRequest.setApiPath(Constant.PetConstant.createPet);
        Request apiRequest = buildRequest.buildRequestObject();
        Response apiResponse = requestExecutor.executeRequest(apiRequest);
        testSuiteHelper.setITextContext(testContext, apiRequest, apiResponse);
        Assert.assertEquals(apiResponse.getStatusCode(), 200, "Correct status code returned");
    }

    @Test(priority = 2)
    public void createSecondPet(ITestContext testContext) {
        cpObj = testSuiteHelper.getPetBody(Constant.PetConstant.newUser);
        createPetPojoList.add(cpObj);
        String petBody = new com.google.gson.Gson().toJson(cpObj);
        BuildRequest buildRequest = new BuildRequest();
        buildRequest.setContentType(ContentType.JSON);
        buildRequest.setRequestBody(petBody);
        buildRequest.setRequestType(Method.POST);
        buildRequest.setBaseUrl(Constant.baseUri);
        buildRequest.setApiPath(Constant.PetConstant.createPet);
        Request apiRequest = buildRequest.buildRequestObject();
        Response apiResponse = requestExecutor.executeRequest(apiRequest);
        testSuiteHelper.setITextContext(testContext, apiRequest, apiResponse);
        Assert.assertEquals(apiResponse.getStatusCode(), 200, "Correct status code returned");
    }

    @Test(priority = 3)
    public void updateFirstPet(ITestContext testContext) {
        String name = createPetPojoList.get(0).getName();
        cpObj = testSuiteHelper.getPetBody(name);
        String petBody = new com.google.gson.Gson().toJson(cpObj);
        BuildRequest buildRequest = new BuildRequest();
        buildRequest.setContentType(ContentType.JSON);
        buildRequest.setRequestBody(petBody);
        buildRequest.setRequestType(Method.PUT);
        buildRequest.setBaseUrl(Constant.baseUri);
        buildRequest.setApiPath(Constant.PetConstant.createPet);
        Request apiRequest = buildRequest.buildRequestObject();
        Response apiResponse = requestExecutor.executeRequest(apiRequest);
        testSuiteHelper.setITextContext(testContext, apiRequest, apiResponse);
        Assert.assertEquals(apiResponse.getStatusCode(), 200, "Correct status code returned");
    }

    @Test(priority = 4)
    public void getPetByStatus(ITestContext testContext) {
        String statusValue = createPetPojoList.get(0).getStatus();
        Map<String,Object> queryParams = new HashMap<>();
        queryParams.put("status", statusValue);
        BuildRequest buildRequest = new BuildRequest();
        buildRequest.setContentType(ContentType.JSON);
        buildRequest.setRequestType(Method.GET);
        buildRequest.setBaseUrl(Constant.baseUri);
        buildRequest.setApiPath(Constant.PetConstant.getPet);
        buildRequest.setQueryParameters(queryParams);
        Request apiRequest = buildRequest.buildRequestObject();
        Response apiResponse = requestExecutor.executeRequest(apiRequest);
        testSuiteHelper.setITextContext(testContext, apiRequest, apiResponse);
        Assert.assertEquals(apiResponse.getStatusCode(), 200, "Correct status code returned");
        testSuiteHelper.assertGetPetByStatus(apiResponse, createPetPojoList);
    }

    /*Negative scenario
    Endpoint missing with validation for empty object.
    Also, no key is mandatory because of this we can't search by status.

    Same issue for post and put pet.
     */
    @Test(priority = 5)
    public void createPetWithoutAnyKey(ITestContext testContext) {
        BuildRequest buildRequest = new BuildRequest();
        buildRequest.setContentType(ContentType.JSON);
        buildRequest.setRequestBody("{}");
        buildRequest.setRequestType(Method.POST);
        buildRequest.setBaseUrl(Constant.baseUri);
        buildRequest.setApiPath(Constant.PetConstant.createPet);
        Request apiRequest = buildRequest.buildRequestObject();
        Response apiResponse = requestExecutor.executeRequest(apiRequest);
        testSuiteHelper.setITextContext(testContext, apiRequest, apiResponse);
        Assert.assertEquals(apiResponse.getStatusCode(), 400, "Correct status code returned");
    }
}
