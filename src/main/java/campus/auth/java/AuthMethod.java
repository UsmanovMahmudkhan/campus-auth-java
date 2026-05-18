package campus.auth.java;

import campus.auth.java.authenticators.ClassicSession;
import campus.auth.java.authenticators.DosejongSession;
import campus.auth.java.authenticators.MoodlerSession;
import campus.auth.java.authenticators.PortalSSOToken;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

public enum AuthMethod {
    PORTAL_SSO_TOKEN("PortalSSOToken", PortalSSOToken::new),
    CLASSIC_SESSION("ClassicSession", ClassicSession::new),
    MOODLER_SESSION("MoodlerSession", MoodlerSession::new),
    DOSEJONG_SESSION("DosejongSession", DosejongSession::new);

    public static final List<AuthMethod> MANUAL = List.of(
            PORTAL_SSO_TOKEN,
            CLASSIC_SESSION,
            MOODLER_SESSION,
            DOSEJONG_SESSION
    );

    private final String methodName;
    private final Supplier<Authenticator> supplier;

    AuthMethod(String methodName, Supplier<Authenticator> supplier) {
        this.methodName = methodName;
        this.supplier = supplier;
    }

    public String getMethodName() {
        return methodName;
    }

    public Authenticator create() {
        return supplier.get();
    }

    public static AuthMethod fromName(String name) {
        String normalized = name.replace("-", "")
                .replace("_", "")
                .toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(method -> method.methodName.replace("_", "").toLowerCase(Locale.ROOT).equals(normalized)
                        || method.name().replace("_", "").toLowerCase(Locale.ROOT).equals(normalized))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown method: " + name));
    }
}
