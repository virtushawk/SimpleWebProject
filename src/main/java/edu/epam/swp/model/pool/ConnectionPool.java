package edu.epam.swp.model.pool;

import edu.epam.swp.exception.PropertyReaderException;
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

/**
 * The enum Connection pool.
 * @author romab
 */
public enum ConnectionPool {

    /**
     * Instance connection pool.
     */
    INSTANCE;

    private final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private BlockingQueue<ProxyConnection> freeConnections;
    private Queue<ProxyConnection> givenAwayConnections;
    private static final int DEFAULT_POOL_SIZE = 32;
    private static final String DATABASE_PROPERTIES = "property/database.properties";
    private static final String DATABASE_URL = "url";
    private static final String DATABASE_DRIVER = "driverClassName";

    ConnectionPool() {
        PropertyReader propertyReader = new PropertyReader();
        Properties properties;
        try {
            properties = propertyReader.readProperty(DATABASE_PROPERTIES);
        } catch (PropertyReaderException e) {
            logger.error("Error occurred while reading the property file",e);
            throw new RuntimeException("Error occurred while reading the property file",e);
        }
        String url = properties.getProperty(DATABASE_URL);
        String driverName = properties.getProperty(DATABASE_DRIVER);
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
                logger.fatal("Error occurred while creating connection",e);
                throw new RuntimeException("Error occurred while creating connection",e);
            }
        }
    }

    /**
     * Gets connection.
     * @return the connection
     */
    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            givenAwayConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.error("Interrupted Exception occurred",e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    /**
     * Releases connection.
     * @param connection the connection
     */
    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection) {
            givenAwayConnections.remove(connection);
            freeConnections.offer((ProxyConnection) connection);
        } else {
            logger.error("Connection is not a proxy connection!");
        }
    }

    /**
     * Destroys pool.
     */
    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
            } catch (InterruptedException e) {
                logger.error("Interrupted Exception occurred",e);
            }
        }
        deregisterDrivers();
    }

    /**
     * Deregister drivers
     */
    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error("Error occurred while deregister drivers ",e);
            }
        });
    }
}
