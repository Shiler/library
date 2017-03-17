package com.epam.library.service;

import com.epam.library.dao.Identified;
import com.epam.library.exception.ServiceException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Yauhen_Yushkevich on 17.03.17.
 */
public interface IService<T extends Identified<PK>, PK extends Serializable> {

    T getById(PK id) throws ServiceException;

    T persist(T obj) throws ServiceException;

    void delete(T obj) throws ServiceException;

    void update(T obj) throws ServiceException;

    List<T> list() throws ServiceException;

}
