package com.project.demo.ui.test.pages;


import com.project.demo.ui.base.CommonUIBase;
import com.project.demo.ui.base.elements.InputFieldElement;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class LoginFormBase
{
    private static final String USERNAME_FIELD_ID = "login_form[username]";
    private static final String PASSWORD_FIELD_ID = "login-modal-password-input";

    private static final String BUTTON_SUBMIT_XPATH_PREFIX = "//button[@class = 'btn btn-primary btn-block modal-submit-button' and @type= 'submit' and text()= '%s']";

    private static final Map<Locale, String> BUTTON_SUBMIT_TEXT = new HashMap<>();

    static
    {
        BUTTON_SUBMIT_TEXT.put(Locale.ITALIAN, "Accedi");
        BUTTON_SUBMIT_TEXT.put(Locale.ENGLISH, "Log In");
    }

    private final CommonUIBase base;

    private final InputFieldElement inputFieldElement;


    public LoginFormBase(CommonUIBase base)
    {
        this.base = base;
        this.inputFieldElement = new InputFieldElement(base);
    }


    public void fillUsernameField(String username)
    {
        inputFieldElement.typeInText(USERNAME_FIELD_ID, username);
    }


    public void fillPasswordField(String password)
    {
        inputFieldElement.typeInText(PASSWORD_FIELD_ID, password);
    }


    public void clickLoginButton(Locale locale)
    {
        base.clickSelfWithJavaScript(base.findElement(By.xpath(String.format(BUTTON_SUBMIT_XPATH_PREFIX, BUTTON_SUBMIT_TEXT.get(locale)))));
        try
        {
            // TODO know that is not good practice need to be removed as wait element to disappear
            Thread.sleep(10000);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

}
