package com.api.core.constants;

import java.util.ArrayList;
import java.util.List;

public class Constant {

    public static final String PROJECT_PATH = System.getProperty("user.dir");

    public static final String PROJECT_OS = System.getProperty("os.name");

    public static final String REPORTS_PATH = PROJECT_PATH + java.io.File.separatorChar + "Reports"
            + java.io.File.separatorChar;

    public static final String baseUri = "https://petstore.swagger.io/v2/";

    public static class User {
        public static final String createUserWithArray = "user/createWithArray";
        public static final String updateUserName = "user/{username}";
        public static final String getUserName = "user/{username}";
    }

    public static class RequestResponseConstant {
        public static final String REQUEST = "Request";
        public static final String RESPONSE = "Response";
        public static final String STATUS_CODE = "StatusCode";
        public static final String RESPONSE_TIME = "ResponseTime";
    }

    public static class PetConstant {
        public static final List<String> status = new ArrayList<String>() {{
            add("available");
            add("pending");
            add("sold");
        }};
        public static final String createPet = "pet";
        public static final String getPet = "pet/findByStatus";
        public static final String newUser = "newUser";
    }
}
