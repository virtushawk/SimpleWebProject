package edu.epam.swp.model.service.impl;

import edu.epam.swp.model.dao.UserDao;
import edu.epam.swp.model.dao.impl.UserDaoImpl;
import edu.epam.swp.model.entity.AccountRole;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.exception.DaoException;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.entity.UserStatus;
import edu.epam.swp.model.service.UserService;
import edu.epam.swp.util.PasswordGenerator;
import edu.epam.swp.util.PasswordHash;
import edu.epam.swp.util.mail.MailUtility;
import edu.epam.swp.model.validation.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private static final UserService instance = new UserServiceImpl();
    private UserDao dao = UserDaoImpl.getInstance();

    private UserServiceImpl() {}

    public static UserService getInstance() {
        return instance;
    }

    @Override
    public List<User> findAll() throws ServiceException {
        List<User> users;
        try {
            users = dao.findAll();
        } catch (DaoException e) {
            logger.error("An error occurred while finding all users",e);
            throw new ServiceException("An error occurred while finding all users",e);
        }
        return users;
    }

    @Override
    public long registerUser(String email,String username,String password) throws ServiceException {
        long id = 0;
        if ((!UserValidator.isUsername(username)) || (!UserValidator.isPassword(password)) || (!UserValidator.isEmail(email))) {
            logger.info("Invalid credentials for user");
            return id;
        }
        User user = new User.UserBuilder().withEmail(email).withUsername(username).withRole(AccountRole.INACTIVE).build();
        String encryptedPassword = PasswordHash.createHash(password);
        try {
            id = dao.create(user,encryptedPassword);
            if (id > 0) {
                MailUtility.sendConfirmMessage(user.getEmail(),id);
            }
        } catch (DaoException e) {
            logger.error("Error occurred while creating account",e);
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
                logger.error("An error occurred while finding a user",e);
                throw new ServiceException("An error occurred while finding a user",e);
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
                    String encryptedPassword = PasswordHash.createHash(password);
                    flag = dao.updatePassword(encryptedPassword,user.get().getAccountId());
                    if (flag) {
                        MailUtility.sendRestoreMessage(user.get().getEmail(),password);
                    }
                }
            } catch (DaoException e) {
                logger.error("An error occurred while restoring password",e);
                throw new ServiceException("An error occurred while restoring password",e);
            }
        }
        return flag;
    }

    @Override
    public boolean changeAvatar(String avatar, long accountId) throws ServiceException {
        boolean flag;
        try {
            flag = dao.updateAvatarById(avatar,accountId);
        } catch (DaoException e) {
            logger.error("An error occurred while updating avatar",e);
            throw new ServiceException("An error occurred while updating avatar",e);
        }
        return flag;
    }

    @Override
    public Optional<User> get(long id) throws ServiceException {
        Optional<User> user;
        try {
            user = dao.find(id);
        } catch (DaoException e) {
            logger.error("An error occurred while finding user",e);
            throw new ServiceException("An error occurred while finding user",e);
        }
        return user;
    }

    @Override
    public boolean confirmEmail(long id) throws ServiceException {
        boolean flag;
        try {
            flag = dao.confirmEmail(id);
        } catch (DaoException e) {
            logger.error("An error occurred while confirming email",e);
            throw new ServiceException("An error occurred while confirming email",e);
        }
        return flag;
    }

    @Override
    public boolean blockUser(long id) throws ServiceException {
        boolean flag;
        try {
            flag = dao.blockUser(id);
        } catch (DaoException e) {
            logger.error("An error occurred while blocking user",e);
            throw new ServiceException("An error occurred while blocking user",e);
        }
        return flag;
    }

    @Override
    public boolean unblockUser(long id) throws ServiceException {
        boolean flag;
        try {
            flag = dao.unblockUser(id);
        } catch (DaoException e) {
            logger.error("An error occurred while unblocking user",e);
            throw new ServiceException("An error occurred while unblocking user",e);
        }
        return flag;
    }

    @Override
    public boolean makeAdmin(long id) throws ServiceException {
        boolean flag;
        try {
            flag = dao.makeAdmin(id);
        } catch (DaoException e) {
            logger.error("An error occurred while making the user admin",e);
            throw new ServiceException("An error occurred while making the user admin",e);
        }
        return flag;
    }

    @Override
    public boolean changeName(String name, long accountId) throws ServiceException {
        boolean flag;
        if (!UserValidator.isName(name)){
            logger.info("Invalid name");
            return false;
        }
        try {
            flag = dao.updateName(name,accountId);
        } catch (DaoException e) {
            logger.error("An error occurred while updating name",e);
            throw new ServiceException("An error occurred while updating name",e);
        }
        return flag;
    }

    @Override
    public boolean changeEmail(String email, long accountId) throws ServiceException {
        boolean flag;
        if (!UserValidator.isEmail(email)){
            logger.info("Invalid email");
            return false;
        }
        try {
            flag = dao.updateEmail(email,accountId);
        } catch (DaoException e) {
            logger.error("An error occurred while updating email",e);
            throw new ServiceException("An error occurred while updating email",e);
        }
        return flag;
    }

    @Override
    public boolean changePassword(String password, long id) throws ServiceException {
        boolean flag;
        if (!UserValidator.isPassword(password)) {
            logger.info("Invalid password");
            return false;
        }
        String encryptedPassword = PasswordHash.createHash(password);
        try {
            flag = dao.updatePassword(encryptedPassword,id);
        } catch (DaoException e) {
            logger.error("An error occurred while updating password",e);
            throw new ServiceException("An error occurred while updating password",e);
        }
        return flag;
    }

    @Override
    public boolean changeUserStatus(long accountId, UserStatus userStatus) throws ServiceException {
        boolean flag;
        try {
            flag = dao.updateUserStatus(accountId,userStatus);
        } catch (DaoException e) {
            logger.error("An error occurred while updating user's status",e);
            throw new ServiceException("An error occurred while updating user's status",e);
        }
        return flag;
    }


}
