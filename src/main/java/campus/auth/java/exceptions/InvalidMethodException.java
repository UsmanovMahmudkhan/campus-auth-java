package campus.auth.java.exceptions;

public class InvalidMethodException extends RuntimeException {
    public InvalidMethodException() {
        super("\"methods\" argument must be a single object that inherits from the \"Authenticator\" class or a list.");
    }
}
