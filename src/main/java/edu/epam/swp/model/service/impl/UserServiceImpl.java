package edu.epam.swp.model.service.impl;

import edu.epam.swp.model.dao.UserDao;
import edu.epam.swp.model.dao.impl.UserDaoImpl;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.model.service.UserService;
import edu.epam.swp.model.validation.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private static final UserServiceImpl instance = new UserServiceImpl();
    private static final UserDao dao = UserDaoImpl.getInstance();

    private UserServiceImpl() {}

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean registerUser(String email,String username,String password) {
        boolean flag = false;
        UserValidator validator = new UserValidator();
        if (validator.isUsername(username) && validator.isPassword(password) && validator.isEmail(email)) {
            logger.info("registration completed, username : {}",username);
            User user = new User(email,username,password);
            dao.create(user);
            flag = true;
        }
        else {
            logger.info("Registration error username : {}",username);
        }
        return flag;
    }

    public Optional<User> findUser(String email,String password) {
        Optional<User> user = Optional.empty();
        UserValidator validator = new UserValidator();
        if (validator.isEmail(email) && validator.isPassword(password)) {
            user = dao.findUserByEmailPassword(email,password);
            logger.info("user found!");
        }
        return user;
    }
}
