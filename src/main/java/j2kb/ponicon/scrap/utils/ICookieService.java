package j2kb.ponicon.scrap.utils;

import javax.servlet.http.Cookie;

public interface ICookieService {

    public Cookie createRefreshCookie(String refreshToken, boolean autoLogin);
    public Cookie createAccessCookie(String accessToken, boolean autoLogin);
    public Cookie findCookie(String key, Cookie[] cookies);
}
