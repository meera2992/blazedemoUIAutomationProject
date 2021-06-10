package com.company.pages.basePages;

import com.company.base.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

public class BasePage extends BaseTest {
    public static WebDriver driver;

    //page Constructor
    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
    }

    public void waitForElementToBeClickable(WebElement we, int Timeout_Seconds) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Timeout_Seconds);
        wait.until(ExpectedConditions.elementToBeClickable(we));
    }

    public void waitForPageload(int Timeout_Seconds) throws Exception {
        Thread.sleep(10000);
        driver.manage().timeouts().implicitlyWait(Timeout_Seconds, TimeUnit.SECONDS);
    }

    public void waitForElementToBeVisible(WebElement we, int Timeout_Seconds) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Timeout_Seconds);
        wait.until(ExpectedConditions.visibilityOf(we));
    }

    public void enterText(WebElement we, String value) {
        we.click();
        we.clear();
        we.sendKeys(value);
    }
}

