package com.project.demo.ui.test.pages;


import com.project.demo.ui.security.user.UserProperties;
import com.project.demo.ui.base.TestFrameworkException;
import com.project.demo.ui.security.SecurityUtils;
import com.project.demo.ui.base.CommonUIBase;
import io.restassured.path.json.JsonPath;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.api.SoftAssertions;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v122.network.Network;
import org.openqa.selenium.devtools.v122.network.model.Headers;
import org.openqa.selenium.devtools.v122.network.model.Response;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;


public class MainPageBase
{
    private static final String LOGIN_BUTTON_XPATH_SELECTOR = "//button[contains (@class, 'login-button header-menu-button')]";
    private static final String DIALOG_CLOSE_BUTTON_XPATH = "//*[@class= 'modal-content']//button//span[text()=' × ']";
    private final CommonUIBase base;

    public LoginFormBase loginForm;


    public MainPageBase(CommonUIBase commonUIBase)
    {
        base = commonUIBase;
        loginForm = new LoginFormBase(base);
    }


    public void clickLoginButton()
    {
        base.fluentWait(By.xpath(LOGIN_BUTTON_XPATH_SELECTOR), 10, 3).click();
    }


    public void closePopupDialogs()
    {
        List<WebElement> list = base.findElements(By.xpath(DIALOG_CLOSE_BUTTON_XPATH));
        for (WebElement element : list)
        {
            element.isDisplayed();
        }
    }

    public  List<WebElement> getBalancesElements()
    {
        return  base.findElements(By.xpath("//*[@class = 'cl-header-user-balance user-balances-service-item d-flex']//li"));
    }


    public WebElement getBalanceByText(String text)
    {
        List<WebElement> list = getBalancesElements();
        for (WebElement webElement : list)
        {
            if (StringUtils.equalsIgnoreCase(webElement.findElement(By.className("user-balance-item-label")).getText(), text))
            {
                try
                {
                    return webElement.findElement(By.className("user-balance-item-amount"));
                }
                catch (NoSuchElementException e){
                    throw new NoSuchElementException("Missing element [item-amount] with text [" + text + "]");
                }
            }
        }
        throw new NoSuchElementException("Missing general element with text [" + text + "]");
    }
    //
    public void assertUserBalancesWithCurrency(SoftAssertions softAssertions, String currencySymbol) throws ParseException
    {
        for (Pair<Response, String> response : base.getResponses())
        {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject)parser.parse(response.getRight());
            if (!StringUtils.equalsIgnoreCase("error", json.get("status").toString()))
            {
                for (int i = 1; i <= 3; i++)
                {
                    LinkedHashMap<String, String> linkedHashMap = JsonPath.from(response.getRight()).get("data." + i + ".info");
                    String creditType = linkedHashMap.get("creditType");
                    softAssertions.assertThat(getBalanceByText(creditType).getText())
                                  .as("credit type " + creditType + " is wrong")
                                  .isEqualTo(prepareExpectedValueAmount(linkedHashMap.get("amount"), currencySymbol));
                }
            }

        }
    }


    private static String prepareExpectedValueAmount(String raw, String currencySymbol)
    {
        return StringUtils.substring(RegExUtils.replaceAll(RegExUtils.replaceAll(raw, currencySymbol, ""), "\\u00A0", ""), 1);
    }
}
