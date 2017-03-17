package com.epam.library.dao.impl;

import com.epam.library.dao.AbstractJDBCDao;
import com.epam.library.dao.DaoFactory;
import com.epam.library.dao.exception.PersistException;
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
public class MySqlBookDao extends AbstractJDBCDao<Book, Integer> {

    private class PersistBook extends Book {
        public void setId(int id) {
            super.setId(id);
        }
    }


    public MySqlBookDao(DaoFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT `id`, `title`, `brief`, `publish_year`, `author` FROM `book` ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO `book` \n" +
                "(`title`, `brief`, `publish_year`, `author`) \n" +
                "VALUES \n" +
                "(?, ?, ?, ?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE `book` \n" +
                "SET `title` = ?, `brief` = ?, `publish_year` = ?, `author` = ? \n" +
                "WHERE `id` = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM `book` WHERE `id` = ?";
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
        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.setString(1, titleNew);
            statement.setString(2, titleOld);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistException();
        }

    }

}
