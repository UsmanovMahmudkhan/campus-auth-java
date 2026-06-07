package io.github.usmanovmahmudkhan.campusauth;

import java.util.Objects;

/**
 * Entry point for verifying campus members through an {@link AuthProvider}.
 *
 * <p>Typical usage:
 * <pre>{@code
 * CampusAuthClient client = CampusAuthClient.withProvider(new DemoAuthProvider());
 * AuthResult result = client.verify(AuthRequest.of("demo-student", "demo-password"));
 * if (result.isAuthenticated()) {
 *     System.out.println(result.member().id());
 * }
 * }</pre>
 */
public final class CampusAuthClient {

    private final AuthProvider provider;

    private CampusAuthClient(AuthProvider provider) {
        this.provider = Objects.requireNonNull(provider, "provider");
    }

    /**
     * Creates a client backed by the given provider.
     *
     * @param provider the provider to delegate verification to
     * @return a new client
     * @throws NullPointerException if {@code provider} is {@code null}
     */
    public static CampusAuthClient withProvider(AuthProvider provider) {
        return new CampusAuthClient(provider);
    }

    /**
     * Verifies a request and returns the outcome.
     *
     * <p>Incorrect-but-well-formed credentials produce a failed
     * {@link AuthResult}, not an exception. A provider that cannot service the
     * request raises {@link ProviderUnavailableException}.
     *
     * @param request the request to verify
     * @return the verification outcome
     * @throws NullPointerException         if {@code request} is {@code null}
     * @throws ProviderUnavailableException if the provider cannot service the request
     */
    public AuthResult verify(AuthRequest request) {
        Objects.requireNonNull(request, "request");
        AuthResult result = provider.authenticate(request);
        return result == null
                ? AuthResult.failed("provider returned no result")
                : result;
    }

    /** Returns the underlying provider. */
    public AuthProvider provider() {
        return provider;
    }
}
