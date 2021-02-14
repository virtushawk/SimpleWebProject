package edu.epam.swp.exception;

public class PropertyReaderException extends Exception {
    public PropertyReaderException() {
    }

    public PropertyReaderException(String message) {
        super(message);
    }

    public PropertyReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertyReaderException(Throwable cause) {
        super(cause);
    }
}
