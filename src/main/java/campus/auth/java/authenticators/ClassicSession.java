package campus.auth.java.authenticators;

import campus.auth.java.AuthResponse;
import campus.auth.java.Authenticator;
import campus.auth.java.exceptions.AuthFailedException;
import campus.auth.java.exceptions.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ClassicSession extends Authenticator {
    @Override
    public AuthResponse authenticate(String id, String password) {
        try {
            HttpResponse<String> response = sessionRequest(id, password);
            if (response.statusCode() == 200) {
                return success(getUserInfo(response.body()));
            }
            return unknownServerError(response.statusCode());
        } catch (AuthFailedException e) {
            return authFailed(false, Map.of("message", "아이디 및 비밀번호가 일치하지 않습니다."));
        } catch (HttpTimeoutException e) {
            return timeout();
        } catch (Exception e) {
            return unknownIssue();
        }
    }

    private HttpResponse<String> sessionRequest(String id, String password)
            throws IOException, InterruptedException, AuthFailedException, ParseException {
        HttpClient session = createSessionClient();
        get(session, "http://classic.sejong.ac.kr/");
        HttpResponse<String> loginResponse = post(
                session,
                "https://classic.sejong.ac.kr/userLogin.do",
                Map.of("userId", id, "password", password)
        );

        Element message = Jsoup.parse(loginResponse.body()).selectFirst("p.tc");
        if (message == null) {
            return get(
                    session,
                    "https://classic.sejong.ac.kr/userCertStatus.do?menuInfoId=MAIN_02_05"
            );
        }

        if ("로그인 정보가 올바르지 않습니다.".equals(convertText(message))) {
            throw new AuthFailedException();
        }
        throw new ParseException(false);
    }

    private Map<String, Object> getUserInfo(String content) throws ParseException {
        Document document = Jsoup.parse(content);
        Elements userInfo = document.select("div.contentWrap > ul.tblA > li > dl > dd");
        if (userInfo.size() < 5) {
            throw new ParseException(false);
        }

        String major = convertText(userInfo.get(0));
        String name = convertText(userInfo.get(2));
        String grade = convertText(userInfo.get(3));
        String status = convertText(userInfo.get(4));

        Element table = document.selectFirst("table.listA");
        if (table == null) {
            throw new ParseException(false);
        }
        Elements keys = table.select("thead > tr > th");
        Elements values = table.select("tbody > tr > td");
        if (keys.size() < 5 || values.size() < 5) {
            throw new ParseException(false);
        }

        Map<String, Object> readCertification = new LinkedHashMap<>();
        for (int i = 1; i < 5; i++) {
            readCertification.put(convertText(keys.get(i)), convertText(values.get(i)));
        }

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("major", major);
        body.put("name", name);
        body.put("grade", grade);
        body.put("status", status);
        body.put("read_certification", readCertification);
        return body;
    }

    private static String convertText(Element element) {
        return element.text().trim();
    }
}
