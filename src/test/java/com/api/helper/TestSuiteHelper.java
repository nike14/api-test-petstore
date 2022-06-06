package com.api.helper;

import com.api.core.constants.Constant;
import com.api.core.pojo.CreateUserPojo;
import com.api.core.utils.Request;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestSuiteHelper {

    Faker faker = new Faker();

    public CreateUserPojo getUserBody() {
        //Generate dynamic values for user
        int id = 0;
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String username = firstName  + lastName;
        String email = username + "@gmail.com";
        String password = faker.internet().password();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        int userStatus = faker.number().numberBetween(0,2);
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

    public List<CreateUserPojo> addUserObjectToList(CreateUserPojo cuObj){
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

    public void assertGetUser(Response apiResponse, CreateUserPojo cuObj){
        JSONObject jsonObject = new JSONObject(apiResponse.asString());
        Assert.assertEquals(jsonObject.get("username"), cuObj.getUserName(), "Verify username");
        Assert.assertEquals(jsonObject.get("firstName"), cuObj.getFirstName(), "Verify first name");
        Assert.assertEquals(jsonObject.get("lastName"), cuObj.getLastName(), "Verify last name");
        Assert.assertEquals(jsonObject.get("email"), cuObj.getEmail(), "Verify email");
        Assert.assertEquals(jsonObject.get("password"), cuObj.getPassword(), "Verify password");
        Assert.assertEquals(jsonObject.get("phone"), cuObj.getPhone(), "Verify phone number");
        Assert.assertEquals(jsonObject.get("userStatus"), cuObj.getUserStatus(), "Verify user status");
    }
}
