package campus.auth.java;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class AuthResult {
    private final AuthResponse response;
    private final List<AuthResponse> failedResponses;

    private AuthResult(AuthResponse response, List<AuthResponse> failedResponses) {
        this.response = response;
        this.failedResponses = failedResponses == null ? List.of() : List.copyOf(failedResponses);
    }

    public static AuthResult response(AuthResponse response) {
        return new AuthResult(response, null);
    }

    public static AuthResult failedResponses(List<AuthResponse> failedResponses) {
        return new AuthResult(null, failedResponses);
    }

    public boolean hasResponse() {
        return response != null;
    }

    public AuthResponse getResponse() {
        return response;
    }

    public List<AuthResponse> getFailedResponses() {
        return Collections.unmodifiableList(failedResponses);
    }

    public String toJson() {
        if (hasResponse()) {
            return response.toJson();
        }
        return JsonWriter.write(Map.of("failed_responses", failedResponses));
    }
}
