package com.epam.library.dao.impl;

import com.epam.library.dao.AbstractJDBCDao;
import com.epam.library.dao.DaoFactory;
import com.epam.library.dao.exception.PersistException;
import com.epam.library.database.DatabaseUtil;
import com.epam.library.database.StandaloneConnectionPool;
import com.epam.library.domain.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Evgeny Yushkevich on 15.03.2017.
 */
public final class MySQLBookDao extends AbstractJDBCDao<Book, Integer> {

    private class PersistBook extends Book {
        public void setId(int id) {
            super.setId(id);
        }
    }

    public MySQLBookDao(DaoFactory parentFactory) {
        super(parentFactory);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Book object) throws PersistException {
        try {
            statement.setString(1, object.getTitle());
            statement.setString(2, object.getBrief());
            statement.setInt(3, object.getDateOfPublishing());
            statement.setString(4, object.getAuthor());
        } catch (SQLException e) {
            throw new PersistException();
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Book object) throws PersistException {
        try {
            statement.setString(1, object.getTitle());
            statement.setString(2, object.getBrief());
            statement.setInt(3, object.getDateOfPublishing());
            statement.setString(4, object.getAuthor());
            statement.setInt(5, object.getId());
        } catch (SQLException e) {
            throw new PersistException();
        }
    }

    @Override
    protected List<Book> parseResultSet(ResultSet resultSet) throws PersistException {
        List<Book> result = new LinkedList<>();
        try {
            while (resultSet.next()) {
                PersistBook book = new PersistBook();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setBrief(resultSet.getString("brief"));
                book.setDateOfPublishing(resultSet.getInt("publish_year"));
                book.setAuthor(resultSet.getString("author"));
                result.add(book);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    public void renameBook(String titleOld, String titleNew) throws PersistException {
        String sql = "UPDATE `book` SET `title` = ? WHERE `title` = ?;";
        PreparedStatement statement = null;
        try (Connection connection = StandaloneConnectionPool.takeConnection()) {
            statement = connection.prepareStatement(sql);
            statement.setString(1, titleNew);
            statement.setString(2, titleOld);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistException();
        } finally {
            DatabaseUtil.closeStatement(statement);
        }

    }

}
