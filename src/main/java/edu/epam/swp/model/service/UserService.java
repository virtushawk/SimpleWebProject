package edu.epam.swp.model.service;

import edu.epam.swp.model.entity.User;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.entity.UserStatus;

import java.util.List;
import java.util.Optional;

/**
 * UserService interface contains method to work with User object.
 * @author romab
 * @see User
 */
public interface UserService {

    /**
     * Finds all users.
     * @return List of users.
     * @throws ServiceException If DaoException was thrown.
     */
    List<User> findAll() throws ServiceException;

    /**
     * Registers user.
     * @param email String containing the email.
     * @param username String containing the username.
     * @param password String containing the password.
     * @return Id of user. If user was not created returns 0.
     * @throws ServiceException If DaoException was thrown.
     */
    long registerUser(String email,String username,String password) throws ServiceException;

    /**
     * Finds user.
     * @param username String containing the username.
     * @param password String containing the password.
     * @return Optional of user.
     * @throws ServiceException If DaoException was thrown.
     */
    Optional<User> findUser(String username,String password) throws ServiceException;

    /**
     * Finds user.
     * @param accountId User's id.
     * @return Optional of user.
     * @throws ServiceException If DaoException was thrown.
     */
    Optional<User> findUser(long accountId) throws ServiceException;

    /**
     * Restores password.
     * @param username String containing the username.
     * @return True if password was restored, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean restorePassword(String username) throws ServiceException;

    /**
     * Changes user's avatar.
     * @param avatar String containing the path to the image.
     * @param accountId User's id.
     * @return True if avatar was updated, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean changeAvatar(String avatar,long accountId) throws ServiceException;

    /**
     * Confirms email.
     * @param confirmationKey String containing the confirmation key.
     * @return True if email was confirmed, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean confirmEmail(String confirmationKey) throws ServiceException;

    /**
     * Blocks user.
     * @param accountId User's id.
     * @return True if user was blocked, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean blockUser(long accountId) throws ServiceException;

    /**
     * Unblocks user.
     * @param accountId User's id.
     * @return True if user was unblocked, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean unblockUser(long accountId) throws ServiceException;

    /**
     * Gives user admin privileges.
     * @param accountId User's id.
     * @return True if user became admin, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean makeAdmin(long accountId) throws ServiceException;

    /**
     * Changes user's name.
     * @param name String containing the name.
     * @param accountId User's id.
     * @return True if name was updated, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean changeName(String name,long accountId) throws ServiceException;

    /**
     * Changes user's email.
     * @param email String containing the email.
     * @param accountId User's id.
     * @return True if email was updated, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean changeEmail(String email,long accountId) throws ServiceException;

    /**
     * Changes password.
     * @param password String containing the password.
     * @param accountId User's id.
     * @return True if password was updated, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean changePassword(String password,long accountId) throws ServiceException;

    /**
     * Changes user's status.
     * @param accountId User's id.
     * @param userStatus User's status.
     * @return True if status was updated, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean changeUserStatus(long accountId,UserStatus userStatus) throws ServiceException;
}
