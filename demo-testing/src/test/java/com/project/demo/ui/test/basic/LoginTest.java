package com.project.demo.ui.test.basic;


import com.project.demo.ui.base.CommonUIBase;
import com.project.demo.ui.base.TestFrameworkException;
import com.project.demo.ui.security.SecurityUtils;
import com.project.demo.ui.security.user.UserProperties;
import com.project.demo.ui.test.pages.MainPageBase;
import org.assertj.core.api.SoftAssertions;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Properties;


public class LoginTest
{
    private static CommonUIBase base;


    @BeforeAll
    public static void init()
    {
        base = new CommonUIBase();
    }


    @AfterAll
    public static void closeDriver()
    {
        base.close();
    }


    @Test
    public void testLoginToApp() throws TestFrameworkException, InterruptedException, ParseException
    {
        MainPageBase mainPageBase = new MainPageBase(base);
        mainPageBase.clickLoginButton();
        Properties cred = UserProperties.loadCred();
        mainPageBase.loginForm.fillUsernameField(SecurityUtils.decrypt(cred.getProperty("username")));
        mainPageBase.loginForm.fillPasswordField(SecurityUtils.decrypt(cred.getProperty("pass")));
        mainPageBase.loginForm.clickLoginButton(Locale.ITALIAN);
        mainPageBase.closePopupDialogs();
        base.getDriver().navigate().refresh();
        SoftAssertions softAssertions = new SoftAssertions();
        mainPageBase.assertUserBalancesWithCurrency(softAssertions, "€");
        softAssertions.assertAll();
    }

}
