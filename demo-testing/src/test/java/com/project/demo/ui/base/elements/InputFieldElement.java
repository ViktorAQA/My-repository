package com.project.demo.ui.base.elements;


import com.project.demo.ui.base.CommonUIBase;
import org.apache.commons.lang3.StringUtils;


public class InputFieldElement
{
    private final CommonUIBase base;


    public InputFieldElement(CommonUIBase base)
    {
        this.base = base;
    }


    public void typeInText(String elementId, String text)
    {
        if (StringUtils.isEmpty(elementId))
        {
            throw new IllegalArgumentException("Argument elementId cannot be NULL!");
        }
        if (StringUtils.isEmpty(text))
        {
            return;
        }
        base.findElementById(elementId).sendKeys(text);
    }
}
