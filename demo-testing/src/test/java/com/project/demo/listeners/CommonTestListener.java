package com.project.demo.listeners;


import ch.qos.logback.classic.Level;
import com.project.demo.ui.UISettingsCommon;
import org.apache.commons.lang3.StringUtils;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * Only for better logging does not make any special and hidden things
 */
public class CommonTestListener
                implements TestExecutionListener
{

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonTestListener.class);

    private static final String LOGGER_LEVEL = System.getProperty(UISettingsCommon.LOGGER_LEVEL, "INFO");
    private static AtomicInteger atomicIntegerCount;


    @Override
    public void testPlanExecutionStarted(TestPlan testPlan)
    {

        LOGGER.info("-----------------------------------------------------------");
        LOGGER.info("Your UI tests plan is started! I wish you good luck!");
        LOGGER.info("-----------------------------------------------------------");
        LoggerUtility.setLogLevelOfClasses(getLoggerLevel(), "com.project.demo.ui");
    }


    @Override
    public void executionStarted(TestIdentifier testIdentifier)
    {
        if (testIdentifier.isTest())
        {
            LOGGER.info("-----------------------------------------------------------");
            LOGGER.info("Test: [" + testIdentifier.getDisplayName() + "] started! Count [" + getTestNumberCounter() + "]");
            LOGGER.info("-----------------------------------------------------------");
        }
    }


    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult)
    {
        if (testIdentifier.isTest())
        {
            LOGGER.info("-----------------------------------------------------------");
            LOGGER.info("Test: [" + testIdentifier.getDisplayName() + "] Finished! Result " + testExecutionResult.getStatus());
            LOGGER.info("-----------------------------------------------------------");
        }
    }


    private static int getTestNumberCounter()
    {
        if (atomicIntegerCount == null)
        {
            atomicIntegerCount = new AtomicInteger(0);
        }
        atomicIntegerCount.set(atomicIntegerCount.intValue() + 1);
        return atomicIntegerCount.get();
    }


    private Level getLoggerLevel()
    {
        Level level;
        if (StringUtils.equalsIgnoreCase(LOGGER_LEVEL, "INFO"))
        {
            level = Level.INFO;
        }
        else if (StringUtils.equalsIgnoreCase(LOGGER_LEVEL, "OFF"))
        {
            level = Level.OFF;
        }
        else if (StringUtils.equalsIgnoreCase(LOGGER_LEVEL, "ALL"))
        {
            level = Level.ALL;
        }
        else if (StringUtils.equalsIgnoreCase(LOGGER_LEVEL, "WARN"))
        {
            level = Level.WARN;
        }
        else if (StringUtils.equalsIgnoreCase(LOGGER_LEVEL, "ERROR"))
        {
            level = Level.ERROR;
        }
        else
        {
            level = Level.DEBUG;
        }
        LOGGER.info("Logger info level is set to: [" + level.levelStr + "]");
        return level;
    }
}
