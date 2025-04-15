package org.qa.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.qa.pages.LoginPage;
import org.qa.textcontext.TestContext;

public class LoginTest {

    TestContext testContext;
    LoginPage loginPage;


    public LoginTest(TestContext testContext){
        this.testContext = testContext;
    }

    @Given("^User can be able to caputre username and passwrod to login to Application UserName(.+), Password(.+)$")
    public void user_can_be_able_to_caputre_username_and_passwrod_to_login_to_application_user_name_password(String UserName, String Password) {
        loginPage = testContext.pageObjectManager.loginPage();
        loginPage.loginTestUser(UserName,Password);
    }
    @When("^User should be login into Swag Labs Home page$")
    public void user_should_be_login_into_swagLabs_home_page() {

    }
    @And("^User can be able to see validate the titile of the page$")
    public void user_can_be_able_to_see_validate_the_titile_of_the_page() {

    }
    @Then("^User should be able to logout from the Swag Labs page$")
    public void user_should_be_able_to_logout_from_the_swagLabs_page() {

    }

    @Given("^User should be enter invalid username (.+) and password (.+) on login screen$")
    public void user_should_be_enter_invalid_username_and_password_on_login_screen(String username, String password) {
        loginPage = testContext.pageObjectManager.loginPage();
        loginPage.loginTestUser(username,password);
    }
    @When("user should be click on Login button")
    public void user_should_be_click_on_login_button() {

    }
    @Then("user must be see the error message")
    public void user_must_be_see_the_error_message() {

    }
}
