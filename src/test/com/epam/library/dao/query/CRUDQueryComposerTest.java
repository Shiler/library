package com.epam.library.dao.query;

import com.epam.library.dao.impl.MySQLBookDao;
import com.epam.library.dao.query.exception.MissingPropertyException;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Objects;

import static org.junit.Assert.assertTrue;

/**
 * Created by Evgeny Yushkevich on 30.03.2017.
 */
public class CRUDQueryComposerTest {

    private CRUDQueryComposer crudQueryComposer;

    @Before
    public void setUp() throws FileNotFoundException {
        crudQueryComposer = new CRUDQueryComposer(MySQLBookDao.class);
    }

    @Test
    public void testComposeCreateQuery() throws MissingPropertyException {
        String expected = "INSERT INTO `book` (`title`, `brief`, `publish_year`, `author`) VALUES (?, ?, ?, ?);";
        String query = crudQueryComposer.composeCreateQuery();
        assertTrue(Objects.equals(query, expected));
    }

    @Test
    public void testComposeReadQuery() throws MissingPropertyException {
        String expected = "SELECT `id`, `title`, `brief`, `publish_year`, `author` FROM `book`;";
        String query = crudQueryComposer.composeReadQuery();
        assertTrue(Objects.equals(query, expected));
    }

    @Test
    public void testComposeUpdateQuery() throws MissingPropertyException {
        String expected = "UPDATE `book` SET `title` = ?, `brief` = ?, `publish_year` = ?, `author` = ? WHERE `id` = ?;";
        String query = crudQueryComposer.composeUpdateQuery();
        assertTrue(Objects.equals(query, expected));
    }

    @Test
    public void testComposeDeleteQuery() throws MissingPropertyException {
        String expected = "DELETE FROM `book` WHERE `id` = ?;";
        String query = crudQueryComposer.composeDeleteQuery();
        assertTrue(Objects.equals(query, expected));
    }

}
