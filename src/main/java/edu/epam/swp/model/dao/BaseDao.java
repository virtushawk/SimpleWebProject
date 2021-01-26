package edu.epam.swp.model.dao;

import edu.epam.swp.model.exception.DaoException;

import java.util.List;

public interface BaseDao<T> {

    List<T> findAll() throws DaoException;
    T get(String id) throws DaoException;
}
