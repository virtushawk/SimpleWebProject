package edu.epam.swp.model.pool;

import edu.epam.swp.model.constant.PropertyConstant;
import edu.epam.swp.model.constant.PropertyPath;
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

    private BlockingQueue<Connection> freeConnections;
    private Queue<Connection> givenAwayConnections;
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);

    private static final int DEFAULT_POOL_SIZE = 32;

    ConnectionPool() {
        String propertyPath = PropertyPath.DATABASE_PROPERTIES;
        PropertyReader propertyReader = new PropertyReader();
        Properties properties;
        try {
            properties = propertyReader.readProperty(propertyPath);
        } catch (PropertyReaderException e) {
            throw new RuntimeException(e);
        }
        String url = properties.getProperty(PropertyConstant.DATABASE_URL);
        String driverName = properties.getProperty(PropertyConstant.DATABASE_DRIVER);
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
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
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = freeConnections.take();
            givenAwayConnections.offer(connection);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
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
