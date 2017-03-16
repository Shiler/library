package com.epam.library.dao;

import com.epam.library.dao.exception.PersistException;
import com.epam.library.dao.impl.MySqlDaoFactory;
import com.epam.library.domain.Book;
import com.epam.library.domain.Employee;
import org.junit.After;
import org.junit.Before;
import org.junit.runners.Parameterized;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Evgeny Yushkevich on 23.11.2016.
 */

public class MySqlDaoTest extends GenericDaoTest<Connection> {

    public MySqlDaoTest(Class clazz, Identified<Integer> notPersistedDto) {
        super(clazz, notPersistedDto);
    }

    private Connection connection;

    private GenericDao dao;

    private static final MySqlDaoFactory factory = new MySqlDaoFactory();

    @Parameterized.Parameters
    public static Collection getParameters() {
        return Arrays.asList(new Object[][]{
                {Employee.class, new Employee()},
                {Book.class, new Book()}
        });
    }

    @Before
    public void setUp() throws PersistException, SQLException {
        connection = factory.getContext();
        connection.setAutoCommit(false);
        dao = factory.getDao(connection, daoClass);
    }

    @After
    public void tearDown() throws SQLException {
        context().rollback();
        context().close();
    }

    @Override
    public GenericDao dao() {
        return dao;
    }

    @Override
    public Connection context() {
        return connection;
    }

}
