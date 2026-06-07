package io.github.usmanovmahmudkhan.campusauth;

/**
 * Base unchecked exception for all errors raised by this library.
 */
public class CampusAuthException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates an exception with a message.
     *
     * @param message human-readable description
     */
    public CampusAuthException(String message) {
        super(message);
    }

    /**
     * Creates an exception with a message and cause.
     *
     * @param message human-readable description
     * @param cause   underlying cause
     */
    public CampusAuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
