package org.qa.stepdefinitions;

import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.qa.TestBase;
import org.qa.report.Report;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
@Slf4j
public class ApiResponce extends TestBase {

    public ApiResponce(){
    }

    @Given("^User should be to hit the gorest api to get the user response$")
    public void user_should_be_to_hit_the_gorest_api_to_get_the_user_response() {
        RestAssured.baseURI = "https://gorest.co.in/public";
        Response response = given().
                contentType(ContentType.JSON)
                .when().get("/v2/users")
                .then()
                .extract()
                .response();
        String body = response.getBody().asPrettyString();
        System.out.println("Response : " + body);
        Assert.assertEquals(200, response.getStatusCode());
        Report.log(scenario, "Extracted Response code is : " + response.getStatusCode());
    }
}
