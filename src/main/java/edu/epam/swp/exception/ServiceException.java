package edu.epam.swp.exception;

/**
 * ServiceException occurs if DaoException was thrown in service methods.
 * @see DaoException
 * @author romab
 */
public class ServiceException extends Exception {

    /**
     * Constructor with no arguments.
     */
    public ServiceException() {}

    /**
     * Constructor with a string message.
     * @param message String containing the exception message.
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Constructor with a String message and Throwable object.
     * @param message String containing the exception message.
     * @param cause Throwable object.
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with a String message and Throwable object.
     * @param cause Throwable object.
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
