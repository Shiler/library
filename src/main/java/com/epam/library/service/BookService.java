package com.epam.library.service;

import com.epam.library.dao.DaoFactory;
import com.epam.library.dao.exception.PersistException;
import com.epam.library.dao.impl.MySqlBookDao;
import com.epam.library.dao.impl.MySqlDaoFactory;
import com.epam.library.domain.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yauhen_Yushkevich on 16.03.17.
 */
public class BookService {

    private final static Logger logger = LogManager.getLogger(BookService.class);

    private DaoFactory daoFactory;
    private MySqlBookDao bookDao;

    public BookService() {
        daoFactory = new MySqlDaoFactory();
        try {
            bookDao = (MySqlBookDao) daoFactory.getDao(MySqlBookDao.class);
        } catch (PersistException e) {
            logger.error("Unable to get DAO for BookService");
        }
    }

    public String showAll() {
        List<Book> bookList = new ArrayList<>();
        StringBuffer bookListReport = new StringBuffer();
        try {
            bookList = bookDao.getAll();

        } catch (PersistException e) {
            logger.error("DAO persistence error in BookService");
            return "Can't view books due to the errors in DAO";
        }
    }

}
