package edu.epam.swp.model.dao;

import edu.epam.swp.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * BaseDao interface with default methods.
 * @param <T> Class type
 * @author romab
 */
public interface BaseDao<T> {

    /**
     * Default method to find All objects.
     * @return List of objects.
     * @throws DaoException if SQLException was thrown.
     */
    List<T> findAll() throws DaoException;

    /**
     * Default method to find the object by id.
     * @param id object id.
     * @return Optional of object.
     * @throws DaoException if SQLException was thrown.
     */
    Optional<T> get(long id) throws DaoException;

    /**
     * Default method to create the object.
     * @param t Object to create.
     * @return true - if the object was created successfully,
     * false - if the has not been created.
     * @throws DaoException if SQLException was thrown.
     */
    boolean create(T t) throws DaoException;

    /**
     * Default method to update object.
     * @param t Object to update.
     * @return true - if the object was updated successfully,
     * false - if the object has not been updated.
     * @throws DaoException if SQLException was thrown.
     */
    boolean update(T t) throws DaoException;

    /**
     * Default method to delete the object by id.
     * @param id Object id.
     * @return true - if the object was deleted successfully,
     * false - if the object has not been deleted.
     * @throws DaoException if SQLException was thrown.
     */
    boolean delete(long id) throws DaoException;
}
