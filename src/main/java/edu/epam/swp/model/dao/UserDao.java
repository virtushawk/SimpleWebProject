package edu.epam.swp.model.dao;

import edu.epam.swp.model.entity.User;
import edu.epam.swp.model.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<User>{

    Optional<User> findUserByUsernamePassword(String username, String password) throws DaoException;
    long create(User user,String password) throws DaoException;
    boolean updateAvatarById(String avatar,long id) throws DaoException;
    boolean confirmEmail(long id) throws DaoException;
    boolean blockUser(long id) throws DaoException;
    boolean unblockUser(long id) throws DaoException;
    boolean makeAdmin(long id) throws DaoException;
}
