package com.epam.library.dao.impl;

import com.epam.library.dao.DaoFactory;
import com.epam.library.dao.GenericDao;
import com.epam.library.dao.exception.PersistException;
import com.epam.library.database.StandaloneConnectionPool;
import com.epam.library.domain.Book;
import com.epam.library.domain.Employee;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Evgeny Yushkevich on 22.11.2016.
 */
public class MySqlDaoFactory implements DaoFactory<Connection> {

    private Map<Class, DaoCreator> creators;

    public Connection getContext() throws PersistException {
        try {
            return StandaloneConnectionPool.takeConnection();
        } catch (SQLException e) {
            throw new PersistException();
        }
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
        creators = new HashMap<>();
        creators.put(Book.class, connection -> new MySqlBookDao(MySqlDaoFactory.this, (Connection) connection));
        creators.put(Employee.class, connection -> new MySqlEmployeeDao(MySqlDaoFactory.this, (Connection) connection));
    }

}