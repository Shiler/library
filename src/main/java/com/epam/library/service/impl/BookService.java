package com.epam.library.service.impl;

import com.epam.library.dao.exception.PersistException;
import com.epam.library.dao.impl.MySQLBookDao;
import com.epam.library.dao.impl.MySQLDaoFactory;
import com.epam.library.domain.Book;
import com.epam.library.exception.ServiceException;
import com.epam.library.service.ICRUDBookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by Yauhen_Yushkevich on 16.03.17.
 */
public class BookService implements ICRUDBookService {

    private final static Logger logger = LogManager.getLogger(BookService.class);

    private MySQLBookDao bookDao;

    public BookService() {
        try {
            bookDao = (MySQLBookDao) MySQLDaoFactory.getInstance().getDao(Book.class);
        } catch (PersistException e) {
            logger.error("Unable to get DAO for BookService");
        }
    }

    public void renameBook(String titleOld, String titleNew) throws ServiceException {
        try {
            bookDao.renameBook(titleOld, titleNew);
        } catch (PersistException e) {
            logger.error("Can't rename books");
            throw new ServiceException();
        }
    }

    public static String bookListToString(List<Book> list) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Book book : list) {
            stringBuffer.append(book.getId());
            stringBuffer.append("\t");
            stringBuffer.append(book.getAuthor());
            stringBuffer.append("\t");
            stringBuffer.append(book.getDateOfPublishing());
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }

    @Override
    public Book getById(Integer id) throws ServiceException {
        try {
            return bookDao.getByPK(id);
        } catch (PersistException e) {
            throw new ServiceException();
        }
    }

    @Override
    public Book persist(Book obj) throws ServiceException {
        try {
            return bookDao.persist(obj);
        } catch (PersistException e) {
            throw new ServiceException();
        }
    }

    @Override
    public void delete(Book obj) throws ServiceException {
        try {
            bookDao.delete(obj);
        } catch (PersistException e) {
            throw new ServiceException();
        }
    }

    @Override
    public void update(Book obj) throws ServiceException {
        try {
            bookDao.update(obj);
        } catch (PersistException e) {
            throw new ServiceException();
        }
    }

    @Override
    public List<Book> list() throws ServiceException {
        List<Book> list;
        try {
            list = bookDao.getAll();
        } catch (PersistException e) {
            logger.error("Unable to get list of books");
            throw new ServiceException();
        }
        return list;
    }


}
