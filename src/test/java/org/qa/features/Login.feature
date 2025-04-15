@Regression
Feature: Validating Login page fileds for Sause Labs

  @Login_Feature_Valid_Credentials
  Scenario Outline: Validating Login fields with valid credentials for Sauce Labs
    Given User can be able to caputre username and passwrod to login to Application UserName<UserName>, Password<Password>
    When User should be login into Swag Labs Home page
    And User can be able to see validate the titile of the page
    Then User should be able to logout from the Swag Labs page

    Examples:
      | UserName      | Password     |
      | standard_user | secret_sauce |

  @Login_Feature_InValid_Credentials
  Scenario Outline: Validating Login fields with Invalid credentials for Sauce Labs
    Given User should be enter invalid username <username> and password <password> on login screen
    When user should be click on Login button
    Then user must be see the error message

    Examples:
      | username      | password     |
    ##@externaldata@src/test/resources/data/Datasheet.xlsx@TESTDATA
|locked_out_user|secret_sauce|


    


