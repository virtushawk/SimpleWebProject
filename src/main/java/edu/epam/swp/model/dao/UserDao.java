package edu.epam.swp.model.dao;

import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.exception.DaoException;
import edu.epam.swp.model.entity.UserStatus;

import java.util.Optional;

/**
 * UserDao interface contains methods to work with User object.
 * Extends {@link BaseDao}
 * @see User
 * @author romab
 */
public interface UserDao extends BaseDao<User>{

    /**
     * Finds user by username and password.
     * @param username String containing the username.
     * @param password String containing the password.
     * @return Optional of user.
     * @throws DaoException if SQLException was thrown.
     */
    Optional<User> findUserByUsernamePassword(String username, String password) throws DaoException;

    /**
     * Finds user by username.
     * @param username String containing the username.
     * @return Optional of user.
     * @throws DaoException if SQLException was thrown.
     */
    Optional<User> findUserByUsername(String username) throws DaoException;

    /**
     * Creates user.
     * @param user User object.
     * @param password String containing the password.
     * @return Id of User. If user was not created returns 0.
     * @throws DaoException if SQException was thrown.
     */
    long create(User user,String password) throws DaoException;

    /**
     * Updates user's avatar.
     * @param avatar String containing path to the image.
     * @param accountId User's id.
     * @return true if avatar was updated successfully, otherwise false.
     * @throws DaoException If SQLException was thrown.
     */
    boolean updateAvatarById(String avatar,long accountId) throws DaoException;

    /**
     * Confirms email.
     * @param accountId User's id.
     * @return true if email was confirmed, otherwise false.
     * @throws DaoException If SQLException was thrown.
     */
    boolean confirmEmail(long accountId) throws DaoException;

    /**
     * Blocks user.
     * @param accountId User's id.
     * @return true if user was blocked, otherwise false.
     * @throws DaoException If SQLException was thrown.
     */
    boolean blockUser(long accountId) throws DaoException;

    /**
     * Unblocks user.
     * @param accountId User's id.
     * @return True if user was unblocked, otherwise false.
     * @throws DaoException If SQLException was thrown.
     */
    boolean unblockUser(long accountId) throws DaoException;

    /**
     * Gives user admin privileges.
     * @param accountId User's id.
     * @return True if user became admin, otherwise false.
     * @throws DaoException If SQLException was thrown.
     */
    boolean makeAdmin(long accountId) throws DaoException;

    /**
     * Takes away admin privileges
     * @param accountId User's id.
     * @return True if user not an admin anymore, otherwise false.
     * @throws DaoException If SQLException was thrown.
     */
    boolean removeAdmin(long accountId) throws DaoException;

    /**
     * Updates user's name.
     * @param name String containing the name.
     * @param accountId User's id.
     * @return true if name was updated, otherwise false.
     * @throws DaoException If SQLException was thrown.
     */
    boolean updateName(String name,long accountId) throws DaoException;

    /**
     * Updates user's email.
     * @param email String containing the email.
     * @param accountId User's id.
     * @return true if email was updated, otherwise false.
     * @throws DaoException If SQLException was thrown.
     */
    boolean updateEmail(String email,long accountId) throws DaoException;

    /**
     * Updates user's password.
     * @param password String containing the password.
     * @param accountId User's id.
     * @return True if password was updated, otherwise false.
     * @throws DaoException If SQLException was thrown.
     */
    boolean updatePassword(String password,long accountId) throws DaoException;

    /**
     * Updates user's status.
     * @param accountId User's id.
     * @param userStatus UserStatus Object.
     * @return true if status was updated, otherwise false.
     * @throws DaoException If SQLException was thrown.
     */
    boolean updateUserStatus(long accountId,UserStatus userStatus) throws DaoException;
}
