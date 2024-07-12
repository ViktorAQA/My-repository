package com.project.demo.ui.base;


import com.project.demo.ui.UISettingsCommon;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v122.network.Network;
import org.openqa.selenium.devtools.v122.network.model.RequestId;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.logging.Logs;
import org.slf4j.Logger;
import org.openqa.selenium.devtools.v122.network.model.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import org.slf4j.LoggerFactory;


public class WebDriverTestBase
{
    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverTestBase.class);

    protected WebDriver driver;

    private CopyOnWriteArrayList<Pair<Response, String>> responses = new CopyOnWriteArrayList<>();

    protected Logs logs;

    public static final String BROWSER_TYPE = System.getProperty(UISettingsCommon.BROWSER_TYPE_PROPERTY, UISettingsCommon.EDGE_BROWSER_TYPE_VALUE);


    public void startDriverEdge() throws TestFrameworkException
    {
        LOGGER.info("Starting Browser [Edge] based in Chromium...");
        String buildRepoPath;
        if (UISettingsCommon.isRemoteExecution())
        {
            buildRepoPath = UISettingsCommon.getRemoteBuildRepositoryLocalPath();
            System.setProperty("webdriver.edge.driver",  buildRepoPath + "\\msedgedriver.exe");
        }

        else
        {
            System.setProperty("webdriver.edge.driver", "C:\\Tools\\msedgedriver.exe");
            buildRepoPath = "C:\\Tools";
        }
//        enableNetworking();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--no-sandbox");
//        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--inprivate");
        driver = new EdgeDriver(options);
        // Ensure the user-data-dir exists and is writable
        if (UISettingsCommon.isRemoteExecution())
        {
            options.addArguments("--user-data-dir="+ buildRepoPath);
        }
        DevTools devTools = ((EdgeDriver) driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.clearBrowserCache());
        devTools.send(Network.setCacheDisabled(true));
        final RequestId[] requestIds = new RequestId[1];
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.of(100000000)));

        devTools.addListener(Network.responseReceived(), responseReceived ->
        {
            if(StringUtils.containsIgnoreCase(responseReceived.getResponse().getUrl(), "operation/getMemberBalance"))
            {
                requestIds[0] = responseReceived.getRequestId();
                String responseBody = devTools.send(Network.getResponseBody(requestIds[0])).getBody();
                responses.add(Pair.of(responseReceived.getResponse(), responseBody));
            }
        });

        driver.get("https://babibet.com.test-delasport.com");
        driver.manage().window().maximize();

    }

    public WebDriver getDriver()
    {
        return driver;
    }


    public void setDriver(WebDriver driver)
    {
        this.driver = driver;
    }


    public void close()
    {
        getDriver().close();
        LOGGER.info("Driver was closed successfully.");
        getDriver().quit();
        LOGGER.info("Driver was quit.");
    }

    private void enableNetworking(){
        LoggingPreferences preferences = new LoggingPreferences();
        preferences.enable(LogType.PERFORMANCE, Level.ALL);

        EdgeOptions options = new EdgeOptions();
//        options.setCapability("goog:loggingPrefs", preferences);
//        options.addArguments();
        options.addArguments("--no-sandbox");
        //        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--inprivate");
        options.addArguments("networkConnectionEnabled", "true");
        System.setProperty("webdriver.chrome.driver", "chrome_driver_path");

        driver = new EdgeDriver(options);
        driver.manage().window().maximize();

        this.driver.get("https://babibet.com.test-delasport.com");

        this.logs = this.driver.manage().logs();
//        for (LogEntry entry : logs) {
//            JSONParser parser = new JSONParser();
//            JSONObject jsonObject = null;
//            try {
//                jsonObject = (JSONObject) parser.parse(entry.getMessage());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            JSONObject messageObject = (JSONObject) jsonObject.get("message");
//            System.out.println(messageObject.toJSONString());
//            // You can do the required processing to messageObject
//        }
    }


    public List<Pair<Response, String>> getResponses()
    {
        return responses;
    }
}
