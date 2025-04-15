package org.qa.stepdefinitions;

import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.qa.textcontext.TestContext;

import static io.restassured.RestAssured.given;
@Slf4j
public class ApiResponce {

    TestContext testContext;

    public ApiResponce(TestContext testContext){
        this.testContext = testContext;
    }

    @Given("User should be able to Retrive list of all dog breeds")
    public void user_should_be_able_to_retrive_list_of_all_dog_breeds() {
        RestAssured.baseURI = " https://api.coindesk.com/v1/bpi/currentprice.json";
        Response response = given().
                contentType(ContentType.JSON)
                .when().get("/all")
                .then()
                .extract()
                .response();
        String body = response.getBody().asPrettyString();
        System.out.println("Response : " + body);
        Assertions.assertEquals(200, response.getStatusCode());
    }
}
