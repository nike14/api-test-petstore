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

import java.util.List;

public class PetTests {

    TestSuiteHelper testSuiteHelper = new TestSuiteHelper();
    RequestExecutor requestExecutor = new RequestExecutor();

    @Test(priority = 1)
    public void createAndVerifyFirstPet(ITestContext testContext) {
        List<CreatePetPojo> createPetPojoList = testSuiteHelper.createPet(requestExecutor, testContext, Constant.PetConstant.newUser);
        testSuiteHelper.verifyPetByStatus(requestExecutor, testContext, createPetPojoList);
    }

    @Test(priority = 2)
    public void createAndVerifySecondPet(ITestContext testContext) {
        List<CreatePetPojo> createPetPojoList = testSuiteHelper.createPet(requestExecutor, testContext, Constant.PetConstant.newUser);
        testSuiteHelper.verifyPetByStatus(requestExecutor, testContext, createPetPojoList);
    }

    @Test(priority = 3)
    public void updateAndVerifyFirstPet(ITestContext testContext) {
        List<CreatePetPojo> createPetPojoList = testSuiteHelper.createPet(requestExecutor, testContext, Constant.PetConstant.newUser);
        createPetPojoList = testSuiteHelper.updatePet(requestExecutor, testContext, createPetPojoList);
        testSuiteHelper.verifyPetByStatus(requestExecutor, testContext, createPetPojoList);
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
