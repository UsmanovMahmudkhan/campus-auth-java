package campus.auth.java;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.util.Map;

public final class LegacyAuth {
    private LegacyAuth() {
    }

    public static Map<String, Object> dosejongApi(String id, String password) {
        Authenticator requester = new SimpleRequester();
        HttpClient session = requester.createSessionClient();
        try {
            requester.post(
                    session,
                    "https://do.sejong.ac.kr/ko/process/member/login",
                    Map.of("email", id, "password", password)
            );
            HttpResponse<String> response = requester.get(session, "https://do.sejong.ac.kr/");
            Elements info = Jsoup.parse(response.body()).select("div.info");
            if (info.isEmpty()) {
                return Map.of("result", false);
            }

            Element name = info.get(0).selectFirst("b");
            Element major = info.get(0).selectFirst("small");
            if (name == null || major == null) {
                return Map.of("result", false);
            }

            String[] majorParts = major.text().trim().split(" ");
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("result", true);
            result.put("name", name.text().trim());
            result.put("id", id);
            result.put("major", majorParts.length > 1 ? majorParts[1] : "");
            return result;
        } catch (IOException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            return Map.of("result", false);
        }
    }

    public static Map<String, Object> uisApi(String id, String password) {
        Authenticator requester = new SimpleRequester();
        requester.headers.put("Referer", "https://portal.sejong.ac.kr");
        HttpClient session = requester.createSessionClient();
        try {
            requester.post(
                    session,
                    "https://portal.sejong.ac.kr/jsp/login/login_action.jsp",
                    Map.of("id", id, "password", password, "rtUrl", "")
            );
            HttpResponse<String> response = requester.get(session, "https://portal.sejong.ac.kr/main.jsp");
            Element nameElement = Jsoup.parse(response.body()).selectFirst("div.info0 > div");
            if (nameElement == null) {
                return Map.of("result", false);
            }

            String name = nameElement.text().split("\\(")[0];
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("result", true);
            result.put("name", name);
            result.put("id", id);
            result.put("major", "none");
            return result;
        } catch (IOException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            return Map.of("result", false);
        }
    }

    public static Map<String, Object> sjlmsApi(String id, String password) {
        Authenticator requester = new SimpleRequester();
        HttpClient session = requester.createSessionClient();
        try {
            HttpResponse<String> response = requester.post(
                    session,
                    "http://sjulms.moodler.kr/login/index.php",
                    Map.of("username", id, "password", password, "rememberusername", "1")
            );
            Element name = Jsoup.parse(response.body()).selectFirst("h4");
            if (name == null) {
                return Map.of("result", false);
            }
            Element major = Jsoup.parse(response.body()).selectFirst("p.department");
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("result", true);
            result.put("name", name.text());
            result.put("id", id);
            result.put("major", major == null ? "" : major.text());
            return result;
        } catch (IOException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            return Map.of("result", false);
        }
    }

    private static final class SimpleRequester extends Authenticator {
        @Override
        public AuthResponse authenticate(String id, String password) {
            throw new UnsupportedOperationException("Legacy helper only");
        }
    }
}
