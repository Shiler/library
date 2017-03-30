package com.epam.library.dao;

import com.epam.library.dao.exception.PersistException;

/**
 * Defines a Factory interface for DAO objects
 * specified by <code>Class</code>
 *
 */
public interface DaoFactory {

    /**
     * Subsidiary interface for building DAO's
     *
     */
    interface DaoCreator {
        GenericDao create();
    }


    /**
     * @param dtoClass Corresponding model <code>Class</code>
     * @return DAO object for {@code dtoClass} model
     * @throws PersistException
     */
    GenericDao getDao(Class dtoClass) throws PersistException;

}