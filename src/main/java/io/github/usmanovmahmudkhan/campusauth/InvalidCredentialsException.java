package io.github.usmanovmahmudkhan.campusauth;

/**
 * Thrown when a verified member is requested from a result that did not
 * authenticate, or when a provider rejects well-formed credentials.
 */
public class InvalidCredentialsException extends CampusAuthException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates an exception with a message.
     *
     * @param message human-readable description
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
