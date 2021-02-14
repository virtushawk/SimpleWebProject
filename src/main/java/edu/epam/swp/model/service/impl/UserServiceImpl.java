package edu.epam.swp.model.service.impl;

import edu.epam.swp.model.dao.UserDao;
import edu.epam.swp.model.dao.impl.UserDaoImpl;
import edu.epam.swp.model.entity.AccountRole;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.exception.DaoException;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.service.UserService;
import edu.epam.swp.model.util.PasswordGenerator;
import edu.epam.swp.model.util.PasswordHash;
import edu.epam.swp.model.util.mail.MailUtility;
import edu.epam.swp.model.validation.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
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
    public List<User> findAll() throws ServiceException {
        List<User> users;
        try {
            users = dao.findAll();
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
        return users;
    }

    @Override
    public long registerUser(String email,String username,String password) throws ServiceException {
        long id = 0;
        if (!(UserValidator.isUsername(username) && UserValidator.isPassword(password) && UserValidator.isEmail(email))) {
            logger.info("Invalid credentials : email : {}, username : {}, password : {}"
                    ,email,username, password);
            return id;
        }
        User user = new User(email,username,AccountRole.INACTIVE);
        String encryptedPassword = PasswordHash.createHash(password);
        try {
            id = dao.create(user,encryptedPassword);
            if (id > 0) {
                MailUtility.sendConfirmMessage(user.getEmail(),id);
            }
        } catch (DaoException e) {
            logger.error("Error occurred while creating account.Exception : {}, email : {},username : {}," +
                            "password : {}",e,email,username,password);
            throw new ServiceException("Error occurred while creating account",e);
        }
        return id;
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

    @Override
    public boolean restorePassword(String username) throws ServiceException {
        Optional<User> user;
        boolean flag = false;
        if (UserValidator.isUsername(username)) {
            try {
                user = dao.findUserByUsername(username);
                if(user.isPresent()) {
                    String password = PasswordGenerator.generatePassword();
                    String enctyptedPassword = PasswordHash.createHash(password);
                    flag = dao.updatePassword(enctyptedPassword,user.get().getId());
                    if (flag) {
                        MailUtility.sendRestoreMessage(user.get().getEmail(),password);
                    }
                }
            } catch (DaoException e) {
                logger.error("An error occurred when requesting a database");
                throw new ServiceException("An error occurred when requesting a database",e);
            }
        }
        return flag;
    }

    @Override
    public boolean changeAvatar(String avatar, long id) throws ServiceException {
        boolean flag;
        try {
            flag = dao.updateAvatarById(avatar,id);
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
        return flag;
    }

    @Override
    public Optional<User> get(long id) throws ServiceException {
        Optional<User> user;
        try {
            user = dao.get(id);
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database",e);
            throw new ServiceException("An error occurred when requesting a database",e);
        }
        return user;
    }

    @Override
    public boolean confirmEmail(long id) throws ServiceException {
        boolean flag;
        try {
            flag = dao.confirmEmail(id);
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
        return flag;
    }

    @Override
    public boolean blockUser(long id) throws ServiceException {
        boolean flag;
        try {
            flag = dao.blockUser(id);
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
        return flag;
    }

    @Override
    public boolean unblockUser(long id) throws ServiceException {
        boolean flag;
        try {
            flag = dao.unblockUser(id);
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
        return flag;
    }

    @Override
    public boolean makeAdmin(long id) throws ServiceException {
        boolean flag;
        try {
            flag = dao.makeAdmin(id);
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
        return flag;
    }

    @Override
    public boolean changeName(String name, long id) throws ServiceException {
        boolean flag;
        try {
            flag = dao.updateName(name,id);
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
        return flag;
    }

    @Override
    public boolean changeEmail(String email, long id) throws ServiceException {
        boolean flag;
        try {
            flag = dao.updateEmail(email,id);
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
        return flag;
    }

    @Override
    public boolean changePassword(String password, long id) throws ServiceException {
        boolean flag;
        String encryptedPassword = PasswordHash.createHash(password);
        try {
            flag = dao.updatePassword(encryptedPassword,id);
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
        return flag;
    }


}
