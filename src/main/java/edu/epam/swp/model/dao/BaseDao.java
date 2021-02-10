package edu.epam.swp.model.dao;

import edu.epam.swp.model.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {

    List<T> findAll() throws DaoException;
    Optional<T> get(long id) throws DaoException;
    boolean create(T t) throws DaoException;
    boolean update(T t) throws DaoException;
    boolean delete(long id) throws DaoException;
}
