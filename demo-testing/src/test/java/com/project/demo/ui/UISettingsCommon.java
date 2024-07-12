package com.project.demo.ui;


import com.project.demo.ui.base.TestFrameworkException;
import org.apache.commons.lang3.StringUtils;


public class UISettingsCommon
{
    public static final String BROWSER_TYPE_PROPERTY = "browserType";
    public static final String FIREFOX_BROWSER_TYPE_VALUE = "FIREFOX";
    public static final String EDGE_BROWSER_TYPE_VALUE = "EDGE";
    public static final String LOGGER_LEVEL = "loggerLevel";
    public static final String REST_PROPERTIES = "restAuthenticationConfigProperties";
    public static long EXPLICIT_WAIT_TIMEOUT = 20;

    public static final String REMOTE_EXECUTION = "remoteExecution";

    public static final String BUILD_ON_REMOTE_DIRECTORY = "buildDirectory";

    /**
     * Very essential property which define is the test is executed in Dev Azure or local machine as your. This is defined with system
     * property '-DremoteExecution=true or false'
     *
     * @return true or false in pipeline is it defined as maven VM property
     */
    public static boolean isRemoteExecution()
    {
        return StringUtils.equals("true", System.getProperty(REMOTE_EXECUTION, "false"));
    }


    public static String getRemoteBuildRepositoryLocalPath() throws TestFrameworkException
    {
        String buildDirectory = System.getProperty(BUILD_ON_REMOTE_DIRECTORY);
        if (StringUtils.isEmpty(buildDirectory))
        {
            throw new TestFrameworkException(
                            "System property [buildDirectory] is missing. The property is used for Dev Azure builds and You cannot run [runTestDataImport] on local side!");
        } return buildDirectory;
    }

}
