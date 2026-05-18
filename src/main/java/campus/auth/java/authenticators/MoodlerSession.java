package campus.auth.java.authenticators;

import campus.auth.java.AuthResponse;
import campus.auth.java.Authenticator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.http.HttpTimeoutException;
import java.util.Map;

public class MoodlerSession extends Authenticator {
    @Override
    public AuthResponse authenticate(String id, String password) {
        try {
            var response = request(
                    "https://sjulms.moodler.kr/login/index.php",
                    Map.of("username", id, "password", password)
            );

            if (response.statusCode() != 200) {
                return unknownServerError(response.statusCode());
            }

            Element infoPicture = Jsoup.parse(response.body()).selectFirst("div.user-info-picture");
            if (infoPicture == null) {
                return authFailed(false);
            }

            Element name = infoPicture.selectFirst("h4");
            Element major = infoPicture.selectFirst("p.department");
            if (name == null || major == null) {
                return unknownIssue();
            }

            return success(Map.of(
                    "name", name.text().trim(),
                    "major", major.text().trim()
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
}
