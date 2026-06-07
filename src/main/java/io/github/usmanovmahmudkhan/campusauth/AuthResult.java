package io.github.usmanovmahmudkhan.campusauth;

import java.util.Objects;
import java.util.Optional;

/**
 * The outcome of an authentication attempt.
 *
 * <p>A result is either authenticated, carrying a {@link CampusMember}, or
 * failed, carrying a human-readable reason. Callers should branch on
 * {@link #isAuthenticated()} before reading the member.
 */
public final class AuthResult {

    private final boolean authenticated;
    private final CampusMember member;
    private final String reason;

    private AuthResult(boolean authenticated, CampusMember member, String reason) {
        this.authenticated = authenticated;
        this.member = member;
        this.reason = reason;
    }

    /**
     * Creates a successful result.
     *
     * @param member the verified member
     * @return an authenticated result
     * @throws NullPointerException if {@code member} is {@code null}
     */
    public static AuthResult authenticated(CampusMember member) {
        return new AuthResult(true, Objects.requireNonNull(member, "member"), "");
    }

    /**
     * Creates a failed result.
     *
     * @param reason a non-sensitive explanation of why verification failed
     * @return a failed result
     */
    public static AuthResult failed(String reason) {
        return new AuthResult(false, null, reason == null ? "" : reason);
    }

    /** Returns {@code true} if the request authenticated successfully. */
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * Returns the verified member.
     *
     * @return the verified member
     * @throws InvalidCredentialsException if this result is not authenticated
     */
    public CampusMember member() {
        if (!authenticated) {
            throw new InvalidCredentialsException(
                    reason.isEmpty() ? "request did not authenticate" : reason);
        }
        return member;
    }

    /** Returns the verified member if present. */
    public Optional<CampusMember> memberIfPresent() {
        return Optional.ofNullable(member);
    }

    /** Returns the failure reason, or an empty string when authenticated. */
    public String reason() {
        return reason;
    }

    @Override
    public String toString() {
        return authenticated
                ? "AuthResult{authenticated=true, member=" + member + '}'
                : "AuthResult{authenticated=false, reason='" + reason + "'}";
    }
}
