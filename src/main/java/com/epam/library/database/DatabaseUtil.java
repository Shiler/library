package com.epam.library.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Evgeny Yushkevich on 30.03.2017.
 */
public class DatabaseUtil {

    private final static Logger logger = LogManager.getLogger(DatabaseUtil.class);

    public static void closeStatement(PreparedStatement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error("Unable to close connection", e);
            }
        }
    }

}
