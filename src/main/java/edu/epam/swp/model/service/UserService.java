package edu.epam.swp.model.service;

import edu.epam.swp.model.entity.User;

import java.util.Optional;

public interface UserService {

    boolean registerUser(String email,String username,String password);
    Optional<User> findUser(String email,String password);
}
