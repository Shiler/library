package com.epam.library.dao.impl;

import com.epam.library.dao.AbstractJDBCDao;
import com.epam.library.dao.DaoFactory;
import com.epam.library.dao.exception.PersistException;
import com.epam.library.domain.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Evgeny Yushkevich on 15.03.2017.
 */
public class MySqlEmployeeDao extends AbstractJDBCDao<Employee, Integer> {

    private class PersistEmployee extends Employee {
        public void setId(int id) {
            super.setId(id);
        }
    }

    public MySqlEmployeeDao(DaoFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT `id`, `name`, `date_of_birth`, `email` FROM `employee` ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO `employee` \n" +
                "(`name`, `date_of_birth`, `email`) \n" +
                "VALUES \n" +
                "(?, ?, ?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE `employee` \n" +
                "SET `name` = ?, `date_of_birth` = ?, `email` = ? \n" +
                "WHERE `id` = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM `employee` WHERE `id` = ?";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Employee object) throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setDate(2, object.getDateOfBirth());
            statement.setString(3, object.getEmail());
        } catch (SQLException e) {
            throw new PersistException();
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Employee object) throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setDate(2, object.getDateOfBirth());
            statement.setString(3, object.getEmail());
            statement.setInt(4, object.getId());
        } catch (SQLException e) {
            throw new PersistException();
        }
    }

    @Override
    protected List<Employee> parseResultSet(ResultSet resultSet) throws PersistException {
        List<Employee> result = new LinkedList<>();
        try {
            while (resultSet.next()) {
                PersistEmployee employee = new PersistEmployee();
                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("name"));
                employee.setDateOfBirth(resultSet.getDate("date_of_birth"));
                employee.setEmail(resultSet.getString("email"));
                result.add(employee);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

}
