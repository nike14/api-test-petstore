package com.api.helper;

import com.api.core.constants.Constant;
import com.api.core.pojo.CreatePetPojo;
import com.api.core.pojo.CreateUserPojo;
import com.api.core.pojo.PetCategoryPojo;
import com.api.core.pojo.PetTagPojo;
import com.api.core.utils.Request;
import com.api.core.utils.RequestExecutor;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestSuiteHelper {

    Faker faker = new Faker();

    public CreateUserPojo getUserBody() {
        //Generate dynamic values for user
        int id = 0;
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String username = firstName + lastName;
        String email = username + "@gmail.com";
        String password = faker.internet().password();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        int userStatus = faker.number().numberBetween(0, 2);
        CreateUserPojo cuObj = new CreateUserPojo();

        //Set Value to Pojo
        cuObj.setId(id);
        cuObj.setFirstName(firstName);
        cuObj.setLastName(lastName);
        cuObj.setUserName(username);
        cuObj.setEmail(email);
        cuObj.setPassword(password);
        cuObj.setPhone(phoneNumber);
        cuObj.setUserStatus(userStatus);
        return cuObj;
    }

    public List<CreateUserPojo> addUserObjectToList(CreateUserPojo cuObj) {
        List<CreateUserPojo> cuList = new ArrayList<>();
        cuList.add(cuObj);
        return cuList;
    }

    public ITestContext setITextContext(ITestContext context, Request request, Response response) {
        context.setAttribute(Constant.RequestResponseConstant.REQUEST, request);
        context.setAttribute(Constant.RequestResponseConstant.RESPONSE, response);
        context.setAttribute(Constant.RequestResponseConstant.STATUS_CODE, response.getStatusCode());
        context.setAttribute(Constant.RequestResponseConstant.RESPONSE_TIME, response.getTimeIn(TimeUnit.MILLISECONDS));
        return context;
    }

    public void assertGetUser(Response apiResponse, CreateUserPojo cuObj) {
        JSONObject jsonObject = new JSONObject(apiResponse.asString());
        Assert.assertEquals(jsonObject.get("username"), cuObj.getUserName(), "Verify username");
        Assert.assertEquals(jsonObject.get("firstName"), cuObj.getFirstName(), "Verify first name");
        Assert.assertEquals(jsonObject.get("lastName"), cuObj.getLastName(), "Verify last name");
        Assert.assertEquals(jsonObject.get("email"), cuObj.getEmail(), "Verify email");
        Assert.assertEquals(jsonObject.get("password"), cuObj.getPassword(), "Verify password");
        Assert.assertEquals(jsonObject.get("phone"), cuObj.getPhone(), "Verify phone number");
        Assert.assertEquals(jsonObject.get("userStatus"), cuObj.getUserStatus(), "Verify user status");
    }

    public CreatePetPojo getPetBody(String nameValue) {
        //Generate dynamic values for pet
        int id = faker.number().randomDigitNotZero();
        PetCategoryPojo pcObj = new PetCategoryPojo();
        pcObj.setId(faker.number().randomDigitNotZero());
        pcObj.setName(faker.cat().breed());
        String name;
        if (nameValue.equalsIgnoreCase(Constant.PetConstant.newUser)){
            name = faker.cat().name();
        } else {
            name = nameValue;
        }
        List<String> photoUrls = new ArrayList<String>() {{
            add(faker.music().instrument());
            add(faker.music().instrument());
        }};
        PetTagPojo ptObj = new PetTagPojo();
        ptObj.setId(faker.number().randomDigitNotZero());
        ptObj.setName(faker.name().firstName());
        List<PetTagPojo> tags = new ArrayList<PetTagPojo>() {{
            add(ptObj);
        }};
        String status = Constant.PetConstant.status.get(faker.number().numberBetween(0, Constant.PetConstant.status.size()));
        CreatePetPojo cpObj = new CreatePetPojo();
        cpObj.setId(id);
        cpObj.setName(name);
        cpObj.setCategory(pcObj);
        cpObj.setPhotoUrls(photoUrls);
        cpObj.setTags(tags);
        cpObj.setStatus(status);
        return cpObj;
    }

    public void assertGetPetByStatus(Response apiResponse, List<CreatePetPojo> createPetPojoList) {
        String statusValue = createPetPojoList.get(0).getStatus();
        String nameValue = createPetPojoList.get(0).getName();
        // First get the JsonPath object instance from the Response interface
        JsonPath jsonPathEvaluator = apiResponse.jsonPath();
        // Read all the status as a List of String. Each item in the list
        List<String> status = jsonPathEvaluator.getList("status");
        List<String> ResponseNames = jsonPathEvaluator.getList("name");
        verifyStatusInList(status, statusValue);
        verifyNameValue(ResponseNames, nameValue);
    }

    public void verifyStatusInList(List<String> status, String statusValue) {
        for (int i = 0; i < status.size(); i++) {
            if (!status.get(i).equalsIgnoreCase(statusValue)) {
                Assert.fail("Wrong status value for query params");
            }
        }
    }

    public void verifyNameValue(List<String> ResponseNames, String nameValue) {
        boolean value = false;
        for (int i = 0; i < ResponseNames.size(); i++) {
            if (ResponseNames.get(i).equalsIgnoreCase(nameValue)) {
                value = true;
            }
        }
        if (!value) {
            Assert.fail("Not able to search name in get list");
        }
    }

    public List<CreatePetPojo> createPet(RequestExecutor requestExecutor, ITestContext testContext, String userType){
        List<CreatePetPojo> createPetPojoList = new ArrayList<>();
        CreatePetPojo cpObj = getPetBody(userType);
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
        setITextContext(testContext, apiRequest, apiResponse);
        Assert.assertEquals(apiResponse.getStatusCode(), 200, "Correct status code returned");
        return createPetPojoList;
    }

    public List<CreatePetPojo> updatePet(RequestExecutor requestExecutor, ITestContext testContext, List<CreatePetPojo> createPetPojoList){
        //Update pet values
        String name = createPetPojoList.get(0).getName();
        CreatePetPojo cpObj = getPetBody(name);
        String petBody = new com.google.gson.Gson().toJson(cpObj);
        BuildRequest buildRequest = new BuildRequest();
        buildRequest.setContentType(ContentType.JSON);
        buildRequest.setRequestBody(petBody);
        buildRequest.setRequestType(Method.PUT);
        buildRequest.setBaseUrl(Constant.baseUri);
        buildRequest.setApiPath(Constant.PetConstant.createPet);
        Request apiRequest = buildRequest.buildRequestObject();
        Response apiResponse = requestExecutor.executeRequest(apiRequest);
        setITextContext(testContext, apiRequest, apiResponse);
        Assert.assertEquals(apiResponse.getStatusCode(), 200, "Correct status code returned");
        return createPetPojoList;
    }

    public List<CreateUserPojo> createUser(RequestExecutor requestExecutor, ITestContext testContext) {
        CreateUserPojo cuObj = getUserBody();
        List<CreateUserPojo> userBody = addUserObjectToList(cuObj);
        String user = new com.google.gson.Gson().toJson(userBody);
        BuildRequest buildRequest = new BuildRequest();
        buildRequest.setContentType(ContentType.JSON);
        buildRequest.setRequestBody(user);
        buildRequest.setRequestType(Method.POST);
        buildRequest.setBaseUrl(Constant.baseUri);
        buildRequest.setApiPath(Constant.User.createUserWithArray);
        Request apiRequest = buildRequest.buildRequestObject();
        Response apiResponse = requestExecutor.executeRequest(apiRequest);
        setITextContext(testContext, apiRequest, apiResponse);
        Assert.assertEquals(apiResponse.getStatusCode(), 200, "Correct status code returned");
        return userBody;
    }

    public CreateUserPojo updateUser(RequestExecutor requestExecutor, ITestContext testContext, List<CreateUserPojo> userBody){
        String userName = userBody.get(0).getUserName();
        CreateUserPojo cuObj = getUserBody();
        String user = new com.google.gson.Gson().toJson(cuObj);
        BuildRequest buildRequest = new BuildRequest();
        buildRequest.setContentType(ContentType.JSON);
        buildRequest.setRequestBody(user);
        buildRequest.setRequestType(Method.PUT);
        buildRequest.setBaseUrl(Constant.baseUri);
        buildRequest.setApiPath(Constant.User.updateUserName.replace("{username}", userName));
        Request apiRequest = buildRequest.buildRequestObject();
        Response apiResponse = requestExecutor.executeRequest(apiRequest);
        setITextContext(testContext, apiRequest, apiResponse);
        Assert.assertEquals(apiResponse.getStatusCode(), 200, "Correct status code returned");
        return cuObj;
    }

    public void verifyPetByStatus(RequestExecutor requestExecutor, ITestContext testContext,List<CreatePetPojo> createPetPojoList){
        //Get pet details
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
        setITextContext(testContext, apiRequest, apiResponse);
        Assert.assertEquals(apiResponse.getStatusCode(), 200, "Correct status code returned");
        assertGetPetByStatus(apiResponse, createPetPojoList);
    }
}
