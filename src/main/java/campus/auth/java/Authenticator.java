package campus.auth.java;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Authenticator {
    protected final Map<String, String> headers;
    protected final int timeoutSeconds;
    protected final HttpClient httpClient;

    protected Authenticator() {
        this(Config.TIMEOUT_SECONDS);
    }

    protected Authenticator(int timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
        this.headers = new LinkedHashMap<>();
        this.headers.put("User-Agent", Config.USER_AGENT);
        this.httpClient = createClient(null);
    }

    public abstract AuthResponse authenticate(String id, String password);

    protected HttpResponse<String> request(String url, Map<String, String> data)
            throws IOException, InterruptedException {
        return post(httpClient, url, data);
    }

    protected HttpResponse<String> post(HttpClient client, String url, Map<String, String> data)
            throws IOException, InterruptedException {
        HttpRequest request = requestBuilder(url)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(formEncode(data)))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    }

    protected HttpResponse<String> get(HttpClient client, String url)
            throws IOException, InterruptedException {
        HttpRequest request = requestBuilder(url).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    }

    protected HttpClient createSessionClient() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        return createClient(cookieManager);
    }

    private HttpClient createClient(CookieManager cookieManager) {
        HttpClient.Builder builder = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(timeoutSeconds))
                .followRedirects(HttpClient.Redirect.NORMAL);
        if (cookieManager != null) {
            builder.cookieHandler(cookieManager);
        }
        return builder.build();
    }

    private HttpRequest.Builder requestBuilder(String url) {
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(timeoutSeconds));
        headers.forEach(builder::header);
        return builder;
    }

    private static String formEncode(Map<String, String> data) {
        return data.entrySet().stream()
                .map(entry -> encode(entry.getKey()) + "=" + encode(entry.getValue()))
                .collect(Collectors.joining("&"));
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    protected AuthResponse success() {
        return success(Map.of(), 200);
    }

    protected AuthResponse success(Map<String, Object> body) {
        return success(body, 200);
    }

    protected AuthResponse success(Map<String, Object> body, int statusCode) {
        return new AuthResponse(true, true, statusCode, "success", body, getClass().getSimpleName());
    }

    protected AuthResponse authFailed() {
        return authFailed(null, null, 200, "");
    }

    protected AuthResponse authFailed(Boolean isAuth) {
        return authFailed(isAuth, null, 200, "");
    }

    protected AuthResponse authFailed(Boolean isAuth, Map<String, Object> body) {
        return authFailed(isAuth, body, 200, "");
    }

    protected AuthResponse authFailed(Boolean isAuth, Map<String, Object> body, int statusCode, String prefixCode) {
        Map<String, Object> responseBody = body == null
                ? Map.of("message", "계정 정보가 잘못되었거나, 인증 포맷 자체에 문제가 있습니다.")
                : body;
        String code = prefixCode == null || prefixCode.isBlank() ? "auth_failed" : prefixCode + "_auth_failed";
        return new AuthResponse(true, isAuth, statusCode, code, responseBody, getClass().getSimpleName());
    }

    protected AuthResponse unknownIssue() {
        return unknownIssue(null, 200);
    }

    protected AuthResponse unknownIssue(Map<String, Object> body, int statusCode) {
        Map<String, Object> responseBody = body == null
                ? Map.of("message", "모듈이 예상한 포맷과 다릅니다. 관리자에게 문의해주세요![https://github.com/UsmanovMahmudkhan/campus-auth-java/issues]")
                : body;
        return new AuthResponse(true, null, statusCode, "unknown_issue", responseBody, getClass().getSimpleName());
    }

    protected AuthResponse unknownServerError(int statusCode) {
        return unknownServerError(null, statusCode);
    }

    protected AuthResponse unknownServerError(Map<String, Object> body, int statusCode) {
        Map<String, Object> responseBody = body == null
                ? Map.of("message", "인증 서버가 정상적인 결과가 반환하지 않아, 결과를 조회할 수 없습니다.")
                : body;
        return new AuthResponse(false, null, statusCode, "unknown_server_error", responseBody, getClass().getSimpleName());
    }

    protected AuthResponse timeout() {
        return new AuthResponse(
                false,
                null,
                null,
                "timeout",
                Map.of("message", "Timeout Exception Occured."),
                getClass().getSimpleName()
        );
    }
}
