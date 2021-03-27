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

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The implementation of {@link UserService}. Contains methods to work with User object.
 * @author romab
 * @see User
 */
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private static final UserService instance = new UserServiceImpl();
    private UserDao dao = UserDaoImpl.getInstance();
    private static HashMap<String,Long> confirmationMap = new HashMap<>();

    private UserServiceImpl() {}

    /**
     * Gets instance.
     * @return the instance
     */
    public static UserService getInstance() {
        return instance;
    }

    /**
     * Finds all users.
     * @return List of users.
     * @throws ServiceException If DaoException was thrown.
     */
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

    /**
     * Registers user.
     * @param email String containing the email.
     * @param username String containing the username.
     * @param password String containing the password.
     * @return Id of user. If user was not created returns 0.
     * @throws ServiceException If DaoException was thrown.
     */
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
                String confirmationKey = UUID.randomUUID().toString();
                MailUtility.sendConfirmMessage(user.getEmail(),confirmationKey);
                confirmationMap.put(confirmationKey,id);
            }
        } catch (DaoException e) {
            logger.error("Error occurred while creating account",e);
            throw new ServiceException("Error occurred while creating account",e);
        }
        return id;
    }

    /**
     * Finds user.
     * @param username String containing the username.
     * @param password String containing the password.
     * @return Optional of user.
     * @throws ServiceException If DaoException was thrown.
     */
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

    /**
     * Restores password.
     * @param username String containing the username.
     * @return True if password was restored, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
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

    /**
     * Changes user's avatar.
     * @param avatar String containing the path to the image.
     * @param accountId User's id.
     * @return True if avatar was updated, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
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

    /**
     * Finds user.
     * @param accountId User's id.
     * @return Optional of user.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public Optional<User> findUser(long accountId) throws ServiceException {
        Optional<User> user;
        try {
            user = dao.find(accountId);
        } catch (DaoException e) {
            logger.error("An error occurred while finding user",e);
            throw new ServiceException("An error occurred while finding user",e);
        }
        return user;
    }

    /**
     * Confirms email.
     * @param confirmationKey String containing the confirmation key
     * @return True if email was confirmed, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public boolean confirmEmail(String confirmationKey) throws ServiceException {
        boolean flag = false;
        try {
            if (confirmationMap.containsKey(confirmationKey)) {
                long accountId = confirmationMap.get(confirmationKey);
                flag = dao.confirmEmail(accountId);
                confirmationMap.remove(confirmationKey);
            }
        } catch (DaoException e) {
            logger.error("An error occurred while confirming email",e);
            throw new ServiceException("An error occurred while confirming email",e);
        }
        return flag;
    }

    /**
     * Blocks user.
     * @param accountId User's id.
     * @return True if user was blocked, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public boolean blockUser(long accountId) throws ServiceException {
        boolean flag;
        try {
            flag = dao.blockUser(accountId);
        } catch (DaoException e) {
            logger.error("An error occurred while blocking user",e);
            throw new ServiceException("An error occurred while blocking user",e);
        }
        return flag;
    }

    /**
     * Unblocks user.
     * @param accountId User's id.
     * @return True if user was unblocked, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public boolean unblockUser(long accountId) throws ServiceException {
        boolean flag;
        try {
            flag = dao.unblockUser(accountId);
        } catch (DaoException e) {
            logger.error("An error occurred while unblocking user",e);
            throw new ServiceException("An error occurred while unblocking user",e);
        }
        return flag;
    }

    /**
     * Gives user admin privileges.
     * @param accountId User's id.
     * @return True if user became admin, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public boolean makeAdmin(long accountId) throws ServiceException {
        boolean flag;
        try {
            flag = dao.makeAdmin(accountId);
        } catch (DaoException e) {
            logger.error("An error occurred while making the user admin",e);
            throw new ServiceException("An error occurred while making the user admin",e);
        }
        return flag;
    }

    /**
     * Takes away admin privileges
     * @param accountId User's id.
     * @return True if user not an admin anymore, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public boolean removeAdmin(long accountId) throws ServiceException {
        boolean flag;
        try {
            flag = dao.removeAdmin(accountId);
        } catch (DaoException e) {
            logger.error("An error occurred while removing the admin",e);
            throw new ServiceException("An error occurred while removing the admin",e);
        }
        return flag;
    }

    /**
     * Changes user's name.
     * @param name String containing the name.
     * @param accountId User's id.
     * @return True if name was updated, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
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

    /**
     * Changes user's email.
     * @param email String containing the email.
     * @param accountId User's id.
     * @return True if email was updated, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
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

    /**
     * Changes password.
     * @param password String containing the password.
     * @param accountId User's id.
     * @return True if password was updated, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public boolean changePassword(String password, long accountId) throws ServiceException {
        boolean flag;
        if (!UserValidator.isPassword(password)) {
            logger.info("Invalid password");
            return false;
        }
        String encryptedPassword = PasswordHash.createHash(password);
        try {
            flag = dao.updatePassword(encryptedPassword,accountId);
        } catch (DaoException e) {
            logger.error("An error occurred while updating password",e);
            throw new ServiceException("An error occurred while updating password",e);
        }
        return flag;
    }

    /**
     * Changes user's status.
     * @param accountId User's id.
     * @param userStatus User's status.
     * @return True if status was updated, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
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
