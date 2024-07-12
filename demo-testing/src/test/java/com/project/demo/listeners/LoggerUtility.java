package com.project.demo.listeners;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class LoggerUtility
{
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LoggerUtility.class);

    public static void setLogLevelOfClasses(Level level, String... classesPath)
    {
        Set<String> loggers = new HashSet<>(List.of(classesPath));
        for (String log : loggers)
        {
            Logger logger = (Logger)LoggerFactory.getLogger(log);
            logger.setLevel(level);
            logger.setAdditive(true);
        }
        LOGGER.info("[setLogLevelOfClasses] Logger level is set to [" + level+ "]");
    }
}
