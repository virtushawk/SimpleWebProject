package edu.epam.swp.model.dao.impl;

import edu.epam.swp.model.constant.StatementConstant;
import edu.epam.swp.model.dao.UserDao;
import edu.epam.swp.model.entity.AccountRole;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.model.exception.DaoException;
import edu.epam.swp.model.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final UserDaoImpl instance = new UserDaoImpl();
    private static final ConnectionPool pool = ConnectionPool.INSTANCE;

    private UserDaoImpl() {}

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<User> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<User> findUserByUsernamePassword(String username, String password) throws DaoException {
        try(Connection connection = pool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(StatementConstant.SELECT_ACCOUNT_BY_USERNAME_PASSWORD);
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
            PreparedStatement statement = connection.prepareStatement(StatementConstant.INSERT_ACCOUNT);
            statement.setString(1,user.getId());
            statement.setString(2,user.getEmail());
            statement.setString(3,user.getUsername());
            statement.setString(4,password);
            int roleUser = 1;
            statement.setInt(5,roleUser);
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
           logger.error("Error occurred while creating user. Exception :  {}. User : {}",e,user);
           throw new DaoException("Error occurred while creating user",e);
        }
        return flag;
    }

    @Override
    public void delete(User user) throws DaoException {

    }
}
