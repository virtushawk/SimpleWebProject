package edu.epam.swp.exception;

/**
 * PropertyReaderException occurs when IOException was thrown in PropertyReader class.
 * @see edu.epam.swp.model.reader.PropertyReader
 * @author romab
 */
public class PropertyReaderException extends Exception {

    /**
     * Constructor with no arguments.
     */
    public PropertyReaderException() {
    }

    /**
     * Constructor with a string message.
     * @param message String containing the exception message.
     */
    public PropertyReaderException(String message) {
        super(message);
    }

    /**
     * Constructor with a String message and Throwable object.
     * @param message String containing the exception message.
     * @param cause Throwable object.
     */
    public PropertyReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with a String message and Throwable object.
     * @param cause Throwable object.
     */
    public PropertyReaderException(Throwable cause) {
        super(cause);
    }
}
