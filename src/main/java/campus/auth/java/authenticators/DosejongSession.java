package campus.auth.java.authenticators;

import campus.auth.java.AuthResponse;
import campus.auth.java.Authenticator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.util.Map;

public class DosejongSession extends Authenticator {
    @Override
    public AuthResponse authenticate(String id, String password) {
        try {
            HttpResponse<String> response = sessionRequest(id, password);
            if (response.statusCode() != 200) {
                return unknownServerError(response.statusCode());
            }

            Elements info = Jsoup.parse(response.body()).select("div.info");
            if (info.isEmpty()) {
                return authFailed(false);
            }

            Element name = info.get(0).selectFirst("b");
            Element major = info.get(0).selectFirst("small");
            if (name == null || major == null) {
                return unknownIssue();
            }

            String[] majorParts = major.text().trim().split(" ");
            if (majorParts.length < 2) {
                return unknownIssue();
            }

            return success(Map.of(
                    "name", name.text().trim(),
                    "major", majorParts[1]
            ));
        } catch (HttpTimeoutException e) {
            return timeout();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return unknownIssue(Map.of("message", e.getMessage()), 200);
        } catch (IOException e) {
            return unknownServerError(Map.of("message", e.getMessage()), 500);
        }
    }

    private HttpResponse<String> sessionRequest(String id, String password)
            throws IOException, InterruptedException {
        HttpClient session = createSessionClient();
        post(
                session,
                "https://do.sejong.ac.kr/ko/process/member/login",
                Map.of("email", id, "password", password)
        );
        return get(session, "https://do.sejong.ac.kr/");
    }
}
