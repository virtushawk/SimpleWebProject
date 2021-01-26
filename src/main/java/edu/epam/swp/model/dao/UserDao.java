package edu.epam.swp.model.dao;

import edu.epam.swp.model.entity.User;
import edu.epam.swp.model.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<User>{

    List<User> findAll() throws DaoException;
    Optional<User> findUserByUsernamePassword(String username, String password) throws DaoException;
    boolean create(User user,String password) throws DaoException;
}
