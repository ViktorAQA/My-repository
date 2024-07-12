package com.project.demo.ui.security.user;


import com.project.demo.ui.UISettingsCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class UserProperties
{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserProperties.class);
    private static final String CRED_PROPERTIES_SOURCE = System.getProperty(UISettingsCommon.REST_PROPERTIES,
                                                                            "src/test/resources/cred.properties");

    private static Properties cred;


    public static Properties getMetaDataProperties()
    {
        LOGGER.debug("Credentials for Configurator REST are loaded from [" +
                                     CRED_PROPERTIES_SOURCE +
                                     "]. You " +
                                     "can preset that as use -D" +
                                     UISettingsCommon.REST_PROPERTIES +
                                     "={Path" +
                                     " from Content Root}");
        try (InputStream input = new FileInputStream(CRED_PROPERTIES_SOURCE))
        {
            Properties properties = new Properties();
            properties.load(input);
            return properties;
        }
        catch (IOException e)
        {
            LOGGER.error("Could NOT load metadata for REST connection!");
            throw new RuntimeException(e);
        }
    }


    public static Properties loadCred()
    {
        if (cred == null)
        {
            cred = getMetaDataProperties();
        }
        return cred;
    }
}
