package edu.epam.swp.model.dao;

import edu.epam.swp.model.entity.User;
import edu.epam.swp.exception.DaoException;

import java.util.Optional;

public interface UserDao extends BaseDao<User>{

    Optional<User> findUserByUsernamePassword(String username, String password) throws DaoException;
    Optional<User> findUserByUsername(String username) throws DaoException;
    long create(User user,String password) throws DaoException;
    boolean updateAvatarById(String avatar,long id) throws DaoException;
    boolean confirmEmail(long id) throws DaoException;
    boolean blockUser(long id) throws DaoException;
    boolean unblockUser(long id) throws DaoException;
    boolean makeAdmin(long id) throws DaoException;
    boolean updateName(String name,long id) throws DaoException;
    boolean updateEmail(String email,long id) throws DaoException;
    boolean updatePassword(String password,long id) throws DaoException;
}
