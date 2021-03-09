package edu.epam.swp.exception;

/**
 * DaoException occurs if the SQLException was thrown in dao methods;
 * @author romab
 */
public class DaoException extends Exception {

    /**
     * Constructor with no arguments.
     */
    public DaoException() {}

    /**
     * Constructor with a string message.
     * @param message String containing the exception message.
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * Constructor with a String message and Throwable object.
     * @param message String containing the exception message.
     * @param cause Throwable object.
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with a String message and Throwable object.
     * @param cause Throwable object.
     */
    public DaoException(Throwable cause) {
        super(cause);
    }
}
