package edu.epam.swp.model.reader;

import edu.epam.swp.exception.PropertyReaderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private static final Logger LOGGER = LogManager.getLogger(PropertyReader.class);

    public Properties readProperty(String path) throws PropertyReaderException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(path)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            LOGGER.info("Properties were read form {}", path);
            return properties;
        } catch (IOException e) {
            throw new PropertyReaderException("Unable to read properties file ",e);
        }
    }
}
