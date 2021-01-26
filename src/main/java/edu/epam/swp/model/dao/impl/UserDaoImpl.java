package edu.epam.swp.model.dao.impl;

import edu.epam.swp.model.dao.UserDao;
import edu.epam.swp.model.entity.AccountRole;
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
    public static final String INSERT_ACCOUNT = "INSERT INTO accounts(account_id,email,username,password,role_id) " +
            "VALUES(?,?,?,?,?)";
    public static final String SELECT_ACCOUNT_BY_USERNAME_PASSWORD =
            "SELECT accounts.account_id,accounts.email,accounts.username,roles.role " +
                    "FROM accounts INNER JOIN roles ON accounts.role_id = roles.role_id " +
                    "WHERE accounts.username =? AND accounts.password =?";

    private UserDaoImpl() {}

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<User> findAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public User get(String id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<User> findUserByUsernamePassword(String username, String password) throws DaoException {
        try(Connection connection = pool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_ACCOUNT_BY_USERNAME_PASSWORD);
            statement.setString(1,username);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String id = resultSet.getString(1);
                String userEmail = resultSet.getString(2);
                String userName = resultSet.getString(3);
                AccountRole role = AccountRole.valueOf(resultSet.getString(4));
                User user = new User(userEmail,userName,role);
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
        try(Connection connection = pool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_ACCOUNT);
            statement.setString(1,user.getId());
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
}
