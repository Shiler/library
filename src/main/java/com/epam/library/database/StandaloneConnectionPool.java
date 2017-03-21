package com.epam.library.database;

import com.epam.library.util.PropertyLoader;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Yauhen_Yushkevich on 21.03.17.
 */
public class StandaloneConnectionPool {

    private final static Logger logger = LogManager.getLogger(StandaloneConnectionPool.class);

    private final static ComboPooledDataSource COMBO_POOLED_DATA_SOURCE;

    static {
        COMBO_POOLED_DATA_SOURCE = new ComboPooledDataSource();
        initPool();
    }

    public static Connection takeConnection() throws SQLException {
        return COMBO_POOLED_DATA_SOURCE.getConnection();
    }

    public void close() {
        COMBO_POOLED_DATA_SOURCE.close();
    }

    private static void initPool() {
        try {
            Properties dbProperties = PropertyLoader.loadProperties("src/main/resources/connection-pool.properties");
            COMBO_POOLED_DATA_SOURCE.setDriverClass(dbProperties.getProperty("db.driverClass"));
            COMBO_POOLED_DATA_SOURCE.setJdbcUrl(dbProperties.getProperty("db.jdbcUrl"));
            COMBO_POOLED_DATA_SOURCE.setUser(dbProperties.getProperty("db.user"));
            COMBO_POOLED_DATA_SOURCE.setPassword(dbProperties.getProperty("db.password"));
            COMBO_POOLED_DATA_SOURCE.setMinPoolSize(Integer.valueOf(dbProperties.getProperty("db.minPoolSize")));
            COMBO_POOLED_DATA_SOURCE.setAcquireIncrement(Integer.valueOf(dbProperties.getProperty("db.acquireIncrement")));
            COMBO_POOLED_DATA_SOURCE.setMaxPoolSize(Integer.valueOf(dbProperties.getProperty("db.maxPoolSize")));
        } catch (FileNotFoundException e) {
            logger.error(e);
        } catch (PropertyVetoException e) {
            logger.error("Invalid property name or property does not exist");
        }

    }

}
