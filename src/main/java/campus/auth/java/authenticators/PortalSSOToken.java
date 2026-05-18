package campus.auth.java.authenticators;

import campus.auth.java.AuthResponse;
import campus.auth.java.Authenticator;

import java.io.IOException;
import java.net.http.HttpTimeoutException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PortalSSOToken extends Authenticator {
    private static final Pattern RESULT_PATTERN = Pattern.compile("var result = '.*';");

    @Override
    public AuthResponse authenticate(String id, String password) {
        headers.put("Referer", "https://portal.sejong.ac.kr");
        try {
            var response = request(
                    "https://portal.sejong.ac.kr/jsp/login/login_action.jsp",
                    Map.of(
                            "mainLogin", "Y",
                            "rtUrl", "blackboard.sejong.ac.kr",
                            "id", id,
                            "password", password
                    )
            );

            if (response.statusCode() != 200) {
                return unknownServerError(response.statusCode());
            }

            String cookies = response.headers().allValues("Set-Cookie").stream()
                    .collect(Collectors.joining(";"));
            boolean ssoTokenExists = cookies.contains("ssotoken");
            String result = getResponseResult(response.body());

            if (ssoTokenExists) {
                return "OK".equals(result) ? success() : unknownIssue();
            }

            if ("erridpwd".equals(result) || "Error".equals(result)) {
                return authFailed(
                        false,
                        Map.of("message", "아이디 및 비밀번호가 일치하지 않습니다."),
                        200,
                        result
                );
            }
            if ("pwsNeedChg".equals(result)) {
                return authFailed(
                        false,
                        Map.of("message", "일정 횟수 이상 패스워드를 잘못 입력하여 계정이 잠겼습니다."),
                        200,
                        result
                );
            }
            if ("invalidDt".equals(result)) {
                return authFailed(
                        null,
                        Map.of("message", "접근 가능한 기간이 아닙니다."),
                        200,
                        result
                );
            }
            if ("invalid".equals(result)) {
                return authFailed(
                        false,
                        Map.of("message", "제한된 아이디입니다."),
                        200,
                        result
                );
            }
            if (result != null && !result.isBlank()) {
                return unknownServerError(200);
            }
            return unknownIssue();
        } catch (HttpTimeoutException e) {
            return timeout();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return unknownIssue(Map.of("message", e.getMessage()), 200);
        } catch (IOException e) {
            return unknownServerError(Map.of("message", e.getMessage()), 500);
        }
    }

    static String getResponseResult(String responseText) {
        Matcher matcher = RESULT_PATTERN.matcher(responseText);
        if (!matcher.find()) {
            return null;
        }
        String matched = matcher.group();
        return matched.substring(14, matched.length() - 2);
    }
}
