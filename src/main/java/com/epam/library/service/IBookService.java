package com.epam.library.service;

import com.epam.library.domain.Book;
import com.epam.library.exception.ServiceException;

import java.util.List;

/**
 * Created by Yauhen_Yushkevich on 17.03.17.
 */
public interface IBookService extends IService<Book, Integer> {

    List<Book> list() throws ServiceException;

    void renameBook(String titleOld, String titleNew) throws ServiceException;

}
