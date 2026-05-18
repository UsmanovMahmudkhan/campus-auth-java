package campus.auth.java.exceptions;

public class AuthFailedException extends Exception {
    public AuthFailedException() {
        super("Authentication Failed.");
    }
}
