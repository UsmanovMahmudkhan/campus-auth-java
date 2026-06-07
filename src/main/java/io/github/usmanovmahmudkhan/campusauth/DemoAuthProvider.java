package io.github.usmanovmahmudkhan.campusauth;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A safe, fully in-memory {@link AuthProvider} for examples and tests.
 *
 * <p>It verifies requests against accounts held in memory and talks to no
 * external system. A default instance is seeded with a single demo account:
 * id {@code demo-student}, password {@code demo-password}. Additional demo
 * accounts can be registered with {@link #withAccount}.
 *
 * <p>This provider is intended for demonstrations, local development, and unit
 * tests. Do not store real credentials in it.
 */
public final class DemoAuthProvider implements AuthProvider {

    /** Identifier of the default seeded demo account. */
    public static final String DEFAULT_ID = "demo-student";

    /** Password of the default seeded demo account. */
    public static final String DEFAULT_PASSWORD = "demo-password";

    private final Map<String, Account> accounts = new HashMap<>();
    private final boolean available;

    /**
     * Creates a provider seeded with the default demo account and marked
     * available.
     */
    public DemoAuthProvider() {
        this(true);
        withAccount(
                DEFAULT_ID,
                DEFAULT_PASSWORD,
                CampusMember.of(DEFAULT_ID, "Demo Student", CampusRole.STUDENT));
    }

    private DemoAuthProvider(boolean available) {
        this.available = available;
    }

    /**
     * Returns a provider that always raises {@link ProviderUnavailableException},
     * useful for exercising failure handling.
     *
     * @return an unavailable provider
     */
    public static DemoAuthProvider unavailable() {
        return new DemoAuthProvider(false);
    }

    /**
     * Registers or replaces a demo account.
     *
     * @param id       account identifier
     * @param password account password
     * @param member   the member returned on successful verification
     * @return this provider, for chaining
     * @throws NullPointerException if any argument is {@code null}
     */
    public DemoAuthProvider withAccount(String id, String password, CampusMember member) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(password, "password");
        Objects.requireNonNull(member, "member");
        accounts.put(id, new Account(password.toCharArray(), member));
        return this;
    }

    @Override
    public AuthResult authenticate(AuthRequest request) {
        Objects.requireNonNull(request, "request");
        if (!available) {
            throw new ProviderUnavailableException("demo provider is unavailable");
        }
        char[] supplied = request.password();
        try {
            Account account = accounts.get(request.id());
            if (account == null || !account.matches(supplied)) {
                return AuthResult.failed("invalid id or password");
            }
            return AuthResult.authenticated(account.member);
        } finally {
            Arrays.fill(supplied, '\0');
        }
    }

    @Override
    public String name() {
        return "demo";
    }

    private static final class Account {
        private final byte[] password;
        private final CampusMember member;

        Account(char[] password, CampusMember member) {
            this.password = toBytes(password);
            this.member = member;
        }

        boolean matches(char[] candidate) {
            return MessageDigest.isEqual(password, toBytes(candidate));
        }

        private static byte[] toBytes(char[] chars) {
            byte[] bytes = new byte[chars.length];
            for (int i = 0; i < chars.length; i++) {
                bytes[i] = (byte) chars[i];
            }
            return bytes;
        }
    }
}
