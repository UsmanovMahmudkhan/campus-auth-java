package campus.auth.java.exceptions;

public class ParseException extends Exception {
    private final boolean expected;

    public ParseException(boolean expected) {
        super(expected ? "Failed parse user resourse." : "Unknown Website Structure.");
        this.expected = expected;
    }

    public boolean isExpected() {
        return expected;
    }
}
