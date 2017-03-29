package com.epam.library.dao;

import com.epam.library.dao.exception.PersistException;
import com.epam.library.domain.Identified;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Yauhen_Yushkevich on 21.03.17.
 */
public interface ManyToManyDao<A extends Identified, B extends Identified, T extends Identified> {


    //List<B> persist(Identified aObj) throws PersistException;

    List<B> getByOwner(A owner) throws PersistException;

    Map<A, List<B>> getAll() throws PersistException;

    //void delete(T pair) throws PersistException;

    //void update(T object) throws PersistException;

}
