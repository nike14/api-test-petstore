# API-TEST-PETSTORE

This project provides an example of how to use open source tools like RESTAssured library to write black-box, functional tests for REST APIs in Java.

RESTAssured runs on top of existing Java testing frameworks (JUnit/testNg), and includes a DSL for building API requests and asserting API responses.

In this example project, REST-assured is used to implement a suite of functional tests for a couple of REST APIs hosted.

## Requirements
```
* Java 1.8
* Install maven.
```

## Installation
 ```
* git clone project : git clone https://github.com/nike14/api-test-petstore.git

* cd api-test-petstore

* mvn clean compile
```

#RUN TESTS
To compile and run the tests from the command line enter the command:
```
mvn clean test
```

#DEBUG TESTS

Import the generated project into your IDE.

Open the project in your IDE and run the tests contained in one of the aforementioned test classes as you would a testNg test.



## Reports

Html Reports at the end of execution is available in /target folder with the name `extent.html'
with test name, test status, test data and screenshots for failures


Sample Report Screenshot

![alt text](https://i.postimg.cc/DZg1Ygvj/Screenshot-2022-06-07-at-12-51-15-PM.png)
![alt text](https://i.postimg.cc/C5wC72b6/Screenshot-2022-06-07-at-12-59-06-PM.png)


## CI/CD
Can be directely integrated with CI/CD tools like Jenkins, Travis.CircleCI for deployement and 
send notifications on Slack, PagerDuty.



## Pending Items

1. Schema validation
2. Added main negative scenario for empty request(Pending for key combinations)

