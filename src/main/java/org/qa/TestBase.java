package org.qa;

import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import javax.swing.plaf.PanelUI;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestBase {

    public static WebDriver driver;
    public static Properties prop;
    public static Scenario scenario;
    public String url;

    public WebDriver initilizeBrowser() throws IOException {
        prop = new Properties();
        FileInputStream fp = new FileInputStream("src/main/java/config/config.properties");
        prop.load(fp);
        String browserName = prop.getProperty("browser");
        url = prop.getProperty("url");
        switch (browserName.toLowerCase()) {
            case "chrome":
                driver = WebDriverManager.chromedriver().create();
                break;
            case "firefox":
                driver = WebDriverManager.firefoxdriver().create();
                break;
            case "safari":
                driver = WebDriverManager.safaridriver().create();
                break;
            case "edge":
                driver = WebDriverManager.edgedriver().create();
                break;
            default:
                System.out.println("please provide the correct browserName ......");
                break;
        }
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        return driver;
    }
}



