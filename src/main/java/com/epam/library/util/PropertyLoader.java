package com.epam.library.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Yauhen_Yushkevich on 21.03.17.
 */
public class PropertyLoader {

    private final static Logger logger = LogManager.getLogger(PropertyLoader.class);

    public static synchronized Properties loadProperties(String filename) throws FileNotFoundException {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(filename)));
            return properties;
        } catch (IOException e) {
            logger.error("Properties are not loaded");
            throw new FileNotFoundException();
        }
    }

}
