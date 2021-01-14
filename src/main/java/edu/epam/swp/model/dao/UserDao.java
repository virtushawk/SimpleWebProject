package edu.epam.swp.model.dao;

import edu.epam.swp.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao{

    List<User> findAll();
    Optional<User> findUserByEmailPassword(String email,String password);
    void create(User user);
    void delete(User user);
}
