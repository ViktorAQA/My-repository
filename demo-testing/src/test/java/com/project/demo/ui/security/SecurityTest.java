package com.project.demo.ui.security;


import com.project.demo.ui.base.TestFrameworkException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("no need to be executed")
public class SecurityTest
{
    @Test
    void testMainLogic() throws TestFrameworkException
    {
        // Login with credentials (tu_viktor / Pass112#)
        String plainText = "tu_viktor";
        String plainPass = "Pass112#";
        Assertions.assertEquals(plainText, SecurityUtils.decrypt(SecurityUtils.encrypt(plainText)));
        System.out.println("user: " + SecurityUtils.encrypt(plainText));
        System.out.println("pass: " + SecurityUtils.encrypt(plainPass));
    }
}
