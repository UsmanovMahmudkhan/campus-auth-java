package io.github.usmanovmahmudkhan.campusauth;

/**
 * Thrown when an {@link AuthProvider} cannot service a request, for example
 * because a backing service is unreachable or disabled.
 */
public class ProviderUnavailableException extends CampusAuthException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates an exception with a message.
     *
     * @param message human-readable description
     */
    public ProviderUnavailableException(String message) {
        super(message);
    }

    /**
     * Creates an exception with a message and cause.
     *
     * @param message human-readable description
     * @param cause   underlying cause
     */
    public ProviderUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
