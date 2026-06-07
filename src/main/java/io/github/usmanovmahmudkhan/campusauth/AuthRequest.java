package io.github.usmanovmahmudkhan.campusauth;

import java.util.Arrays;

/**
 * An immutable authentication request carrying a member id and a secret.
 *
 * <p>The secret is held as a {@code char[]} so it can be cleared with
 * {@link #clear()} once verification finishes. {@link #toString()} never
 * exposes the secret.
 */
public final class AuthRequest {

    private final String id;
    private final char[] password;

    private AuthRequest(String id, char[] password) {
        this.id = id;
        this.password = password;
    }

    /**
     * Creates a request from an id and a password.
     *
     * @param id       non-blank member identifier
     * @param password non-blank secret
     * @return a new {@code AuthRequest}
     * @throws IllegalArgumentException if {@code id} or {@code password} is null or blank
     */
    public static AuthRequest of(String id, String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("password must not be null or blank");
        }
        return of(id, password.toCharArray());
    }

    /**
     * Creates a request from an id and a password supplied as a character array.
     *
     * <p>The array is copied; the caller may clear its own copy after this call.
     *
     * @param id       non-blank member identifier
     * @param password non-empty secret
     * @return a new {@code AuthRequest}
     * @throws IllegalArgumentException if {@code id} is null or blank, or {@code password} is null or empty
     */
    public static AuthRequest of(String id, char[] password) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id must not be null or blank");
        }
        if (password == null || password.length == 0) {
            throw new IllegalArgumentException("password must not be null or blank");
        }
        return new AuthRequest(id, Arrays.copyOf(password, password.length));
    }

    /** Returns the member identifier. */
    public String id() {
        return id;
    }

    /**
     * Returns a copy of the secret.
     *
     * <p>The caller owns the returned array and should clear it when done.
     *
     * @return a defensive copy of the password characters
     */
    public char[] password() {
        return Arrays.copyOf(password, password.length);
    }

    /**
     * Overwrites the stored secret with zero characters.
     *
     * <p>After this call {@link #password()} returns an all-zero array.
     */
    public void clear() {
        Arrays.fill(password, '\0');
    }

    @Override
    public String toString() {
        return "AuthRequest{id='" + id + "', password=***}";
    }
}
