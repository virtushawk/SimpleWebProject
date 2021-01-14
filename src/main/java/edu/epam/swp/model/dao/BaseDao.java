package edu.epam.swp.model.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface BaseDao {

    Logger logger = LogManager.getLogger(BaseDao.class);

    default void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error("Error occurred while closing statement", e);
            }
        }
    }

    default void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Error occurred while closing connection", e);
            }
        }
    }

}
