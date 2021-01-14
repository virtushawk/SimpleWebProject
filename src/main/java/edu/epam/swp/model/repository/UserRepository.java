package edu.epam.swp.model.repository;

import edu.epam.swp.model.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static final UserRepository instance = new UserRepository();
    List<User> users = new ArrayList<>();

    private UserRepository() {};

    public static UserRepository getInstance() {
        return instance;
    }

    public boolean add(User user) {
        return users.add(user);
    }

    public boolean remove(User user) {
        return users.remove(user);
    }

    public User get(Long id) {
        return users.get(id.intValue());
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }
}
