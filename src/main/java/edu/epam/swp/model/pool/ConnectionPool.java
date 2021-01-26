package edu.epam.swp.model.pool;

import edu.epam.swp.model.exception.PropertyReaderException;
import edu.epam.swp.model.reader.PropertyReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public enum ConnectionPool {
    INSTANCE;

    private final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private BlockingQueue<Connection> freeConnections;
    private Queue<Connection> givenAwayConnections;
    private static final int DEFAULT_POOL_SIZE = 32;
    public static final String DATABASE_PROPERTIES = "property/database.properties";

    ConnectionPool() {
        PropertyReader propertyReader = new PropertyReader();
        Properties properties;
        try {
            properties = propertyReader.readProperty(DATABASE_PROPERTIES);
        } catch (PropertyReaderException e) {
            logger.error("Error occurred while reading the property file",e);
            throw new RuntimeException("Error occurred while reading the property file",e);
        }
        String url = properties.getProperty(PropertyName.DATABASE_URL);
        String driverName = properties.getProperty(PropertyName.DATABASE_DRIVER);
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            logger.error("Unable to register DB driver",e);
            throw new RuntimeException("Unable to register DB driver!", e);
        }
        freeConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        givenAwayConnections = new ArrayDeque<>();
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                Connection connection = DriverManager.getConnection(url,properties);
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.add(proxyConnection);
            } catch (SQLException e) {
                logger.error("Error occurred while creating connection",e);
            }
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = freeConnections.take();
            givenAwayConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.error("Interrupted Exception occurred",e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        givenAwayConnections.remove(connection);
        freeConnections.offer(connection);
    }

    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.take().close();
            } catch (SQLException e) {
                logger.error("Error occurred while destroying connection",e);
            } catch (InterruptedException e) {
                logger.error("Interrupted Exception occurred",e);
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error("Error occurred while deregisterDrivers ",e);
            }
        });
    }
}
