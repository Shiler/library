package com.epam.library.dao.impl;

import com.epam.library.dao.ManyToManyDao;
import com.epam.library.dao.exception.PersistException;
import com.epam.library.domain.Book;
import com.epam.library.domain.Employee;
import com.epam.library.domain.EmployeeHasBook;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Yauhen_Yushkevich on 21.03.17.
 */
public final class MySQLEmployeeBookDao implements ManyToManyDao<Employee, Book, EmployeeHasBook> {

    private final static Logger logger = LogManager.getLogger(MySQLEmployeeBookDao.class);

    private Connection connection;

    public MySQLEmployeeBookDao(Connection connection) {
        this.connection = connection;
    }

    private class PersistBook extends Book {
        public void setId(int id) {
            super.setId(id);
        }
    }

    @Override
    public List<Book> getByOwner(Employee owner) throws PersistException {
        String sql = "SELECT b.id, b.title, b.brief, b.publish_year, b.author "
                + "FROM employee e "
                + "INNER JOIN employee_book eb ON eb.employee_id = e.id "
                + "INNER JOIN book b ON b.id = eb.book_id "
                + "WHERE e.id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, owner.getId());
            ResultSet resultSet = statement.executeQuery();
            return parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    private List<Book> parseResultSet(ResultSet resultSet) throws PersistException {
        List<Book> books = new ArrayList<>();
        try {
            while (resultSet.next()) {
                PersistBook book = new PersistBook();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setBrief(resultSet.getString("brief"));
                book.setDateOfPublishing(resultSet.getInt("publish_year"));
                book.setAuthor(resultSet.getString("author"));
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            logger.error("Error while iterating result set");
            throw new PersistException();
        }
    }

    @Override
    public Map<Employee, List<Book>> getAll() throws PersistException {
        return null;
    }

}
