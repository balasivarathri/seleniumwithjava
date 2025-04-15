package org.qa.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.qa.TestBase;
import org.qa.report.Report;

@Slf4j
public class LoginPage extends TestBase {


    public WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By userName_input = By.xpath("//input[@placeholder='Username']");
    private final By password_input = By.xpath("//input[@placeholder='Password']");
    private final By login_button = By.xpath("//input[@name='login-button']");

    public void enterUserName(String username) {
        driver.findElement(userName_input).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(password_input).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(login_button).click();
    }
    public void loginTestUser(String username, String password) {
        enterUserName(username);
        enterPassword(password);
        clickLogin();
        Report.screenshot(scenario);
    }
}
