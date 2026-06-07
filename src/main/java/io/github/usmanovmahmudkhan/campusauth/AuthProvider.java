package io.github.usmanovmahmudkhan.campusauth;

/**
 * Strategy that verifies an {@link AuthRequest} against some member directory.
 *
 * <p>The library ships one safe, in-memory implementation,
 * {@link DemoAuthProvider}. Any provider that integrates a real system must be
 * supplied by the application that owns and is authorized to use that system;
 * such integrations are out of scope for this package.
 */
@FunctionalInterface
public interface AuthProvider {

    /**
     * Verifies a request.
     *
     * <p>Implementations should return {@link AuthResult#failed(String)} for
     * well-formed but incorrect credentials, and throw
     * {@link ProviderUnavailableException} when the provider cannot service the
     * request at all. Implementations must never log the request secret.
     *
     * @param request the request to verify
     * @return the verification outcome
     * @throws ProviderUnavailableException if the provider cannot service the request
     */
    AuthResult authenticate(AuthRequest request);

    /**
     * Returns a short, stable name for this provider, used by the CLI and logs.
     *
     * @return the provider name
     */
    default String name() {
        return getClass().getSimpleName();
    }
}
