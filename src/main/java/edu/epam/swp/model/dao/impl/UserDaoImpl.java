package edu.epam.swp.model.dao.impl;

import edu.epam.swp.model.constant.StatementConstant;
import edu.epam.swp.model.dao.UserDao;
import edu.epam.swp.model.entity.User;
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
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findUserByEmailPassword(String email,String password) {
        try(Connection connection = pool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(StatementConstant.SELECT_USER_BY_EMAIL_PASSWORD);
            statement.setString(1,email);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getInt(1);
                String userEmail = resultSet.getString(2);
                String username = resultSet.getString(3);
                String userPassword = resultSet.getString(4);
                User user = new User(userEmail,username,userPassword);
                user.setId(id);
                return Optional.of(user);
            }
        } catch (SQLException e) {
           logger.error("SQLException",e);
        }
        Optional<User> result = Optional.empty();
        return result;
    }

    @Override
    public void create(User user) {
        try(Connection connection = pool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(StatementConstant.INSERT_USER);
            statement.setInt(1,user.getId().intValue());
            statement.setString(2,user.getEmail());
            statement.setString(3,user.getUsername());
            statement.setString(4,user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
           logger.error("SQLException ",e);
        }
    }

    @Override
    public void delete(User user) {

    }
}
