package com.epam.library.dao.impl;

import com.epam.library.dao.DaoFactory;
import com.epam.library.dao.GenericDao;
import com.epam.library.dao.exception.PersistException;
import com.epam.library.domain.Book;
import com.epam.library.domain.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Evgeny Yushkevich on 22.11.2016.
 */
public class MySqlDaoFactory implements DaoFactory<Connection> {

    private String user = "root";
    private String password = "root";
    private String url = "jdbc:mysql://localhost:3306/library";
    private String driver = "com.mysql.cj.jdbc.Driver";
    private Map<Class, DaoCreator> creators;

    @Deprecated
    public Connection getContext() throws PersistException {
        Connection connection;
        try {
            Properties info = new Properties();
            info.setProperty("user", user);
            info.setProperty("password", password);
            info.setProperty("useSSL", "true");
            info.setProperty("serverSslCert", "classpath:server.crt");
            info.setProperty("useLegacyDatetimeCode", "false");
            info.setProperty("serverTimezone", "Europe/Moscow");
            info.setProperty("useUnicode", "true");
            info.setProperty("characterEncoding", "utf8");
            connection = DriverManager.getConnection(url, info);
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return connection;
    }

    @Override
    public GenericDao getDao(Class dtoClass) throws PersistException {
        DaoCreator creator = creators.get(dtoClass);
        if (creator == null) {
            throw new PersistException("Dao object for " + dtoClass + " not found.");
        }
        return creator.create(getContext());
    }

    public MySqlDaoFactory() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        creators = new HashMap<>();
        creators.put(Book.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlBookDao(MySqlDaoFactory.this, connection);
            }
        });
        creators.put(Employee.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlEmployeeDao(MySqlDaoFactory.this, connection);
            }
        });
    }

}