package com.api.helper;

import com.api.core.constants.Constant;
import com.api.core.pojo.CreateUserPojo;
import com.api.core.utils.Request;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.ITestContext;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestSuiteHelper {

    Faker faker = new Faker();

    public List<CreateUserPojo> getUserBody() throws JsonProcessingException {
        //Generate dynamic values for user
        int id = 0;
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String username = firstName  + lastName;
        String email = username + "@gmail.com";
        String password = faker.internet().password();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        //int userStatus = faker.number().numberBetween(0,2);
        int userStatus = 0;
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
}
