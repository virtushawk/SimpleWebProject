package edu.epam.swp.model.service;

import edu.epam.swp.model.entity.User;
import edu.epam.swp.model.exception.ServiceException;

import java.util.Optional;

public interface UserService {

    boolean registerUser(String email,String username,String password) throws ServiceException;
    Optional<User> findUser(String username,String password) throws ServiceException;
    boolean changeAvatar(String avatar,long id) throws ServiceException;
    Optional<User> get(long id) throws ServiceException;
}
