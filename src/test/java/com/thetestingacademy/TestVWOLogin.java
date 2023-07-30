package com.thetestingacademy;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.Duration;


public class TestVWOLogin {

    // 2 Test cases

    // One Negative - Invalid Username, password -> Error
    // One Positive - Valid Username, password -> Dashboard Page

    ChromeOptions options;
    WebDriver driver;

    @BeforeSuite
    public void setUp() {
        options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS) ;
    }


    @Test(priority = 1, groups = {"negative", "sanity", "reg"})
    @Severity(SeverityLevel.BLOCKER)
    @Description("TC#1 - Verify that with Invalid username and Valid password, Login is not successfull !!")
    public void testInvalidLogin() throws InterruptedException {

        driver.get("https://app.vwo.com/#/login");
        driver.findElement(By.id("login-username")).sendKeys("93npu2yyb0@esiix.co");
        driver.findElement(By.id("login-password")).sendKeys("Wingify@123");
        driver.findElement(By.id("js-login-btn")).click();

        WebElement errorMessgae = driver.findElement(By.className("notification-box-description"));
        // 3 seconds
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(errorMessgae));

        String errorString = driver.findElement(By.className("notification-box-description")).getText();
        Assert.assertEquals(errorMessgae.getText(), "Your email, password, IP address or location did not match");


    }

    @Test(enabled = true, priority = 2, groups = {"positive", "sanity", "stage"})
    @Description("Verify that with Valid username and Valid password, Login is successfull !!")
    public void testValidLogin() throws InterruptedException {

        // ID, Name, ClassName -> CssSelector, Xppath - Firefox or Chrome
        // xpath. CSS Selector -> Chrome, will not work Firefox.


        driver.get("https://app.vwo.com/#/login");
        driver.findElement(By.id("login-username")).sendKeys("93npu2yyb0@esiix.com");
        driver.findElement(By.id("login-password")).sendKeys("Wingify@123");
        driver.findElement(By.id("js-login-btn")).click();

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.page-heading")));

        // Assertion
        // Expected Result == Actual Result

        Assert.assertEquals(driver.getTitle(), "Dashboard");
        Assert.assertEquals(driver.getCurrentUrl(), "https://app.vwo.com/#/dashboard");

        driver.get("https://app.vwo.com/logout");


    }

    @AfterSuite
    public void tearDown() {
        driver.quit();
    }


}
