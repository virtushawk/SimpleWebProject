package edu.epam.swp.model.reader;

import edu.epam.swp.exception.PropertyReaderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * PropertyReader class is used to read properties.
 * @author romab
 */
public class PropertyReader {

    private static final Logger logger = LogManager.getLogger(PropertyReader.class);

    /**
     * Reads properties.
     * @param path String containing the path to the properties.
     * @return Properties object.
     * @throws PropertyReaderException If IOException was thrown.
     */
    public Properties readProperty(String path) throws PropertyReaderException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        try (InputStream inputStream = classLoader.getResourceAsStream(path)) {
            properties.load(inputStream);
            logger.info("Properties were read form {}", path);
        } catch (IOException e) {
            logger.info("Unable to read properties file",e);
            throw new PropertyReaderException("Unable to read properties file",e);
        }
        return properties;
    }
}
