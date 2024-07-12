package com.project.demo.ui.base;


import com.project.demo.ui.UISettingsCommon;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;


public class CommonUIBase
                extends WebDriverTestBase
{

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUIBase.class);


    public CommonUIBase()
    {
        if (StringUtils.equalsIgnoreCase(BROWSER_TYPE, UISettingsCommon.FIREFOX_BROWSER_TYPE_VALUE))
            try
            {
               throw new TestFrameworkException("Missing implementation of Firefox driver!");
            }
            catch (TestFrameworkException e)
            {
                throw new RuntimeException(e);
            }
        else if (StringUtils.equalsIgnoreCase(BROWSER_TYPE, UISettingsCommon.EDGE_BROWSER_TYPE_VALUE))
        {
            try
            {
                startDriverEdge();
            }
            catch (TestFrameworkException e)
            {
                throw new RuntimeException(e);
            }
        }
        else
        {
            LOGGER.error("Unsupported browser type!");
        }
    }


    public WebElement findElement(By by)
    {
        return driver.findElement(by);
    }


    public WebElement findElementById(String id)
    {
        return driver.findElement(By.id(id));
    }


    public WebElement fluentWait(By by, long timeOutAfter, long pollingEvery)
    {
        Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(timeOutAfter))
                                                       .pollingEvery(Duration.ofSeconds(pollingEvery))
                                                       .ignoring(NoSuchElementException.class);

        return wait.until(driver -> driver.findElement(by));
    }


    public WebElement fluentWait(WebElement webElement, long timeOutAfter, long pollingEvery)
    {
        Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(timeOutAfter))
                                                       .pollingEvery(Duration.ofSeconds(pollingEvery))
                                                       .ignoring(NoSuchElementException.class);

        return wait.until(driver -> webElement);
    }


    public void mouseOverElementBy(By by)
    {
        Actions action = new Actions(driver);
        action.moveToElement(fluentWait(by, 30, 5)).perform();
    }


    public void mouseOverElement(WebElement element)
    {
        Actions action = new Actions(driver);
        action.moveToElement(fluentWait(element, 30, 5)).perform();
    }


    public void mouseOverElementAndClick(WebElement element)
    {
        Actions action = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor)driver;
        action.moveToElement(fluentWait(element, 30, 5)).perform();
        js.executeScript("arguments[0].setAttribute('target', '_self'); ", element);
        js.executeScript("arguments[0].click();", element);
    }


    public void clickSelfWithJavaScript(WebElement element)
    {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].setAttribute('target', '_self');", element);
        js.executeScript("arguments[0].click();", element);
    }


    public WebElement findElementExplicitWaitToBeClickable(By by, long timeOutAfter, boolean suppressLog)
    {
        try
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutAfter));
            return wait.until(ExpectedConditions.elementToBeClickable(by));
        }
        catch (NoSuchElementException | TimeoutException e)
        {
            if (!suppressLog)
            {
                LOGGER.error("Cannot find WebElement By :" + e.getLocalizedMessage());
            }
            return null;
        }
    }


    @SuppressWarnings("UnusedReturnValue")
    public WebElement findElementExplicitWaitToBeClickable(WebElement element, long timeOutAfter, boolean suppressLog)
    {
        try
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutAfter));
            return wait.until(ExpectedConditions.elementToBeClickable(element));
        }
        catch (NoSuchElementException | TimeoutException e)
        {
            if (!suppressLog)
            {
                LOGGER.error("Cannot find WebElement By :" + e.getLocalizedMessage());
            }
            return null;
        }
    }


    public WebElement findElementExplicitWaitToBeClickable(By by)
    {
        return findElementExplicitWaitToBeClickable(by, UISettingsCommon.EXPLICIT_WAIT_TIMEOUT, false);
    }


    public List<WebElement> findElements(By by)
    {
        return driver.findElements(by);
    }


}
