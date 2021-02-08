package edu.epam.swp.model.dao.impl;

import edu.epam.swp.model.dao.UserDao;
import edu.epam.swp.model.entity.AccountRole;
import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.model.exception.DaoException;
import edu.epam.swp.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
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
            "avatar FROM accounts WHERE account_id = ? ";
    private static final String UPDATE_AVATAR = "UPDATE accounts SET avatar = ? WHERE account_id = ?";


    private UserDaoImpl() {}

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<User> findAll() throws DaoException {
        throw new UnsupportedOperationException();
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
                String name = resultSet.getString(2);
                String avatar = resultSet.getString(3);
                User user = new User(email,name,avatar);
                user.setId(id);
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
    public boolean create(User user,String password) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_ACCOUNT)) {
            statement.setLong(1,user.getId());
            statement.setString(2,user.getEmail());
            statement.setString(3,user.getUsername());
            statement.setString(4,password);
            int roleUser = 1;
            statement.setInt(5,roleUser);
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            if (!(e.getSQLState().equals(ERROR_DUP_KEY)))
            {
                logger.error("Error occurred while creating user. Exception :  {}. User : {}",e,user);
                throw new DaoException("Error occurred while creating user",e);
            }
            flag = false;
        }
        return flag;
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

}
