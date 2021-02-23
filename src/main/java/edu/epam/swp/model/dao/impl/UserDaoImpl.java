package edu.epam.swp.model.dao.impl;

import edu.epam.swp.model.dao.UserDao;
import edu.epam.swp.model.entity.AccountRole;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.exception.DaoException;
import edu.epam.swp.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private static final UserDao instance = new UserDaoImpl();
    private ConnectionPool pool = ConnectionPool.INSTANCE;
    private static final String ERROR_DUP_KEY = "23000";
    private static final String INSERT_ACCOUNT = "INSERT INTO accounts(account_id,email,username,password,role_id) " +
            "VALUES(?,?,?,?,?)";
    private static final String SELECT_ACCOUNT_BY_USERNAME_PASSWORD =
            "SELECT accounts.account_id,accounts.email,accounts.username,roles.role,accounts.avatar " +
                    "FROM accounts INNER JOIN roles ON accounts.role_id = roles.role_id " +
                    "WHERE accounts.username =? AND accounts.password =?";
    private static final String SELECT_ACCOUNT_BY_ID = "SELECT email,username," +
            "avatar,name FROM accounts WHERE account_id = ? ";
    private static final String UPDATE_AVATAR = "UPDATE accounts SET avatar = ? WHERE account_id = ?";
    private static final String UPDATE_ROLE = "UPDATE accounts SET role_id = ? WHERE account_id = ?";
    private static final String SELECT_ACCOUNT = "SELECT accounts.account_id,accounts.email,accounts.username," +
            "accounts.avatar,roles.role FROM accounts INNER JOIN roles ON accounts.role_id = roles.role_id ";
    private static final String UPDATE_NAME = "UPDATE accounts SET name = ? WHERE account_id = ?";
    private static final String UPDATE_EMAIL = "UPDATE accounts SET email = ? WHERE account_id = ?";
    private static final String UPDATE_PASSWORD = "UPDATE accounts SET password = ? WHERE account_id = ?";
    private static final String SELECT_USER_BY_USERNAME = "SELECT accounts.account_id,accounts.email,accounts.username,roles.role,accounts.avatar " +
            "FROM accounts INNER JOIN roles ON accounts.role_id = roles.role_id " +
            "WHERE accounts.username =?";


    private UserDaoImpl() {}

    public static UserDao getInstance() {
        return instance;
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ACCOUNT)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String email = resultSet.getString(2);
                String name = resultSet.getString(3);
                String avatar = resultSet.getString(4);
                AccountRole role = AccountRole.valueOf(resultSet.getString(5));
                User user = new User.UserBuilder().withEmail(email).withUsername(name).withRole(role).withAvatar(avatar)
                .withAccountId(id).build();
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("An error occurred while finding all users",e);
            throw new DaoException("An error occurred while finding all users",e);
        }
        return users;
    }

    @Override
    public Optional<User> get(long id) throws DaoException {
        Optional<User> result = Optional.empty();
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ACCOUNT_BY_ID)) {
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String email = resultSet.getString(1);
                String username = resultSet.getString(2);
                String avatar = resultSet.getString(3);
                String name = resultSet.getString(4);
                User user = new User.UserBuilder().withEmail(email).withUsername(username).withAvatar(avatar)
                        .withAccountId(id).withName(name).build();
                result = Optional.of(user);
            }
        } catch (SQLException e) {
            logger.error("An error occurred while finding user",e);
            throw new DaoException("An error occurred while finding user",e);
        }
        return result;
    }

    @Override
    public boolean create(User user) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(User user) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<User> findUserByUsernamePassword(String username, String password) throws DaoException {
        Optional<User> result = Optional.empty();
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ACCOUNT_BY_USERNAME_PASSWORD)) {
            statement.setString(1,username);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                String userEmail = resultSet.getString(2);
                String userName = resultSet.getString(3);
                AccountRole role = AccountRole.valueOf(resultSet.getString(4));
                String avatar = resultSet.getString(5);
                User user = new User.UserBuilder().withEmail(userEmail).withRole(role).withAvatar(avatar)
                        .withAccountId(id).withUsername(userName).build();
                result = Optional.of(user);
            }
        } catch (SQLException e) {
           logger.error("An error occurred while finding a user",e);
           throw new DaoException("An error occurred while finding a user",e);
        }
        return result;
    }

    @Override
    public Optional<User> findUserByUsername(String username) throws DaoException {
        Optional<User> result = Optional.empty();
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_USERNAME)) {
            statement.setString(1,username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                String userEmail = resultSet.getString(2);
                String userName = resultSet.getString(3);
                AccountRole role = AccountRole.valueOf(resultSet.getString(4));
                String avatar = resultSet.getString(5);
                User user = new User.UserBuilder().withEmail(userEmail).withUsername(userName).withRole(role)
                        .withAvatar(avatar).withAccountId(id).build();
                result = Optional.of(user);
            }
        } catch (SQLException e) {
            logger.error("An error occurred while finding user",e);
            throw new DaoException("An error occurred while finding user",e);
        }
        return result;
    }

    @Override
    public long create(User user,String password) throws DaoException {
        long id = 0;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_ACCOUNT,Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1,user.getAccountId());
            statement.setString(2,user.getEmail());
            statement.setString(3,user.getUsername());
            statement.setString(4,password);
            int roleUser = AccountRole.INACTIVE.ordinal();
            statement.setInt(5,roleUser);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            if (!(e.getSQLState().equals(ERROR_DUP_KEY))) {
                logger.error("Error occurred while creating user",e);
                throw new DaoException("Error occurred while creating user",e);
            }
        }
        return id;
    }

    @Override
    public boolean updateAvatarById(String avatar, long accountId) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_AVATAR)) {
            statement.setString(1,avatar);
            statement.setLong(2,accountId);
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("An error occurred while updating avatar",e);
            throw new DaoException("An error occurred while updating avatar",e);
        }
        return flag;
    }

    @Override
    public boolean confirmEmail(long id) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_ROLE)) {
            int roleUser = AccountRole.USER.ordinal();
            statement.setLong(1,roleUser);
            statement.setLong(2,id);
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("An error occurred while requesting a database",e);
            throw new DaoException("An error occurred while requesting a database",e);
        }
        return flag;
    }

    @Override
    public boolean blockUser(long id) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_ROLE)) {
            int roleUser = AccountRole.BLOCKED.ordinal();
            statement.setLong(1,roleUser);
            statement.setLong(2,id);
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("An error occurred while blocking user",e);
            throw new DaoException("An error occurred while blocking user",e);
        }
        return flag;
    }

    @Override
    public boolean unblockUser(long id) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_ROLE)) {
            int roleUser = AccountRole.USER.ordinal();
            statement.setLong(1,roleUser);
            statement.setLong(2,id);
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("An error occurred while unblocking user",e);
            throw new DaoException("An error occurred while unblocking user",e);
        }
        return flag;
    }

    @Override
    public boolean makeAdmin(long id) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_ROLE)) {
            int roleUser = AccountRole.ADMIN.ordinal();
            statement.setLong(1,roleUser);
            statement.setLong(2,id);
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("An error occurred while making the user admin",e);
            throw new DaoException("An error occurred while making the user admin",e);
        }
        return flag;
    }

    @Override
    public boolean updateName(String name,long accountId) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_NAME)) {
            statement.setString(1,name);
            statement.setLong(2,accountId);
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("An error occurred while updating name",e);
            throw new DaoException("An error occurred while updating name",e);
        }
        return flag;
    }

    @Override
    public boolean updateEmail(String email, long id) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_EMAIL)) {
            statement.setString(1,email);
            statement.setLong(2,id);
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("An error occurred while updating email",e);
            throw new DaoException("An error occurred while updating email",e);
        }
        return flag;
    }

    @Override
    public boolean updatePassword(String password, long id) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_PASSWORD)) {
            statement.setString(1,password);
            statement.setLong(2,id);
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("An error occurred while updating password",e);
            throw new DaoException("An error occurred while updating password",e);
        }
        return flag;
    }

}
