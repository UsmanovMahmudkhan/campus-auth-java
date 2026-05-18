package campus.auth.java;

import campus.auth.java.exceptions.InvalidMethodException;

import java.util.ArrayList;
import java.util.List;

public final class AuthService {
    private AuthService() {
    }

    public static AuthResult authenticate(String id, String password) {
        return authenticate(id, password, AuthMethod.MANUAL);
    }

    public static AuthResponse authenticate(String id, String password, AuthMethod method) {
        if (method == null) {
            throw new InvalidMethodException();
        }
        return method.create().authenticate(id, password);
    }

    public static AuthResult authenticate(String id, String password, List<AuthMethod> methods) {
        if (methods == null || methods.isEmpty() || methods.stream().anyMatch(method -> method == null)) {
            throw new InvalidMethodException();
        }
        List<AuthResponse> failedResponses = new ArrayList<>();
        for (AuthMethod method : methods) {
            AuthResponse response = method.create().authenticate(id, password);
            if (response.getIsAuth() != null) {
                return AuthResult.response(response);
            }
            failedResponses.add(response);
        }
        return AuthResult.failedResponses(failedResponses);
    }
}
