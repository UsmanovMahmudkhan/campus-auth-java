package campus.auth.java;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class AuthResponse {
    private final boolean success;
    private final Boolean isAuth;
    private final Integer statusCode;
    private final String code;
    private final Map<String, Object> body;
    private final String authenticator;

    public AuthResponse(
            boolean success,
            Boolean isAuth,
            Integer statusCode,
            String code,
            Map<String, Object> body,
            String authenticator
    ) {
        this.success = success;
        this.isAuth = isAuth;
        this.statusCode = statusCode;
        this.code = Objects.requireNonNull(code, "code");
        this.body = Collections.unmodifiableMap(new LinkedHashMap<>(body == null ? Map.of() : body));
        this.authenticator = Objects.requireNonNull(authenticator, "authenticator");
    }

    public boolean isSuccess() {
        return success;
    }

    public Boolean getIsAuth() {
        return isAuth;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getCode() {
        return code;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public String getAuthenticator() {
        return authenticator;
    }

    public String toJson() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", success);
        response.put("is_auth", isAuth);
        response.put("status_code", statusCode);
        response.put("code", code);
        response.put("body", body);
        response.put("authenticator", authenticator);
        return JsonWriter.write(response);
    }

    @Override
    public String toString() {
        return "AuthResponse{"
                + "success=" + success
                + ", isAuth=" + isAuth
                + ", statusCode=" + statusCode
                + ", code='" + code + '\''
                + ", body=" + body
                + ", authenticator='" + authenticator + '\''
                + '}';
    }
}
