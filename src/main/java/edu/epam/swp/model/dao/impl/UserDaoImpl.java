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
    private static final UserDaoImpl instance = new UserDaoImpl();
    private static final ConnectionPool pool = ConnectionPool.INSTANCE;
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

    public static UserDaoImpl getInstance() {
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
                User user = new User(email,name,role,avatar);
                user.setId(id);
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("An error occurred when requesting a database",e);
            throw new DaoException("An error occurred when requesting a database",e);
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
                User user = new User(email,username,avatar);
                user.setId(id);
                user.setName(name);
                result = Optional.of(user);
            }
        } catch (SQLException e) {
            logger.error("An error occurred when requesting a database",e);
            throw new DaoException("An error occurred when requesting a database",e);
        }
        return result;
    }

    @Override
    public boolean create(User user) throws DaoException {
        return false;
    }

    @Override
    public boolean update(User user) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(long id) throws DaoException {
        return false;
    }

    @Override
    public Optional<User> findUserByUsernamePassword(String username, String password) throws DaoException {
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
                User user = new User(userEmail,userName,role,avatar);
                user.setId(id);
                return Optional.of(user);
            }
        } catch (SQLException e) {
           logger.error("An error occurred when requesting a database",e);
           throw new DaoException("An error occurred when requesting a database",e);
        }
        Optional<User> result = Optional.empty();
        return result;
    }

    @Override
    public Optional<User> findUserByUsername(String username) throws DaoException {
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
                User user = new User(userEmail,userName,role,avatar);
                user.setId(id);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            logger.error("An error occurred when requesting a database",e);
            throw new DaoException("An error occurred when requesting a database",e);
        }
        Optional<User> result = Optional.empty();
        return result;
    }

    @Override
    public long create(User user,String password) throws DaoException {
        long id = 0;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_ACCOUNT,Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1,user.getId());
            statement.setString(2,user.getEmail());
            statement.setString(3,user.getUsername());
            statement.setString(4,password);
            int roleUser = 3;
            statement.setInt(5,roleUser); // 3 = INACTIVE
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()) {
                id = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            if (!(e.getSQLState().equals(ERROR_DUP_KEY)))
            {
                logger.error("Error occurred while creating user. Exception :  {}. User : {}",e,user);
                throw new DaoException("Error occurred while creating user",e);
            }
        }
        return id;
    }

    @Override
    public boolean updateAvatarById(String avatar, long id) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_AVATAR)) {
            statement.setString(1,avatar);
            statement.setLong(2,id);
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("An error occurred while requesting a database",e);
            throw new DaoException("An error occurred while requesting a database",e);
        }
        return flag;
    }

    @Override
    public boolean confirmEmail(long id) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_ROLE)) {
            int roleUser = 1; // 1 = USER
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
            int roleUser = 3; // 3 = INACTIVE
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
    public boolean unblockUser(long id) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_ROLE)) {
            int roleUser = 1; // 1 = USER
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
    public boolean makeAdmin(long id) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_ROLE)) {
            int roleUser = 2; // 2 = ADMIN
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
    public boolean updateName(String name,long id) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_NAME)) {
            statement.setString(1,name);
            statement.setLong(2,id);
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("An error occurred while requesting a database",e);
            throw new DaoException("An error occurred while requesting a database",e);
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
            logger.error("An error occurred while requesting a database",e);
            throw new DaoException("An error occurred while requesting a database",e);
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
            logger.error("An error occurred while requesting a database",e);
            throw new DaoException("An error occurred while requesting a database",e);
        }
        return flag;
    }

}
