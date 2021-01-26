package edu.epam.swp.model.service.impl;

import com.mysql.cj.protocol.PacketSentTimeHolder;
import edu.epam.swp.model.dao.UserDao;
import edu.epam.swp.model.dao.impl.UserDaoImpl;
import edu.epam.swp.model.entity.AccountRole;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.model.exception.DaoException;
import edu.epam.swp.model.exception.ServiceException;
import edu.epam.swp.model.service.UserService;
import edu.epam.swp.model.util.PasswordHash;
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
    public boolean registerUser(String email,String username,String password) throws ServiceException {
        boolean flag;
        if (!(UserValidator.isUsername(username) && UserValidator.isPassword(password) && UserValidator.isEmail(email))) {
            logger.info("Invalid credentials : email : {}, username : {}, password : {}"
                    ,email,username, password);
            return false;
        }
        User user = new User(email,username,AccountRole.USER);
        String encryptedPassword = PasswordHash.createHash(password);
        try {
            flag = dao.create(user,encryptedPassword);
        } catch (DaoException e) {
            logger.error("Error occurred while creating account.Exception : {}, email : {},username : {}," +
                            "password : {}",e,email,username,password);
            throw new ServiceException("Error occurred while creating account",e);
        }
        return flag;
    }

    public Optional<User> findUser(String username,String password) throws ServiceException {
        Optional<User> user = Optional.empty();
        if (UserValidator.isUsername(username) && UserValidator.isPassword(password)) {
            try {
                String encryptedPassword = PasswordHash.createHash(password);
                user = dao.findUserByUsernamePassword(username,encryptedPassword);
            } catch (DaoException e) {
                logger.error("An error occurred when requesting a database");
                throw new ServiceException("An error occurred when requesting a database",e);
            }
        }
        return user;
    }
}
