package j2kb.ponicon.scrap.utils;

import javax.servlet.http.Cookie;

public interface ICookieService {

    /**
     * refresh쿠키 생성
     * @param refreshToken
     * @param autoLogin
     * @return
     */
    public Cookie createRefreshCookie(String refreshToken, boolean autoLogin);

    /**
     * access쿠키 생성
     * @param accessToken
     * @param autoLogin
     * @return
     */
    public Cookie createAccessCookie(String accessToken, boolean autoLogin);

    /**
     * 쿠키 찾기
     * @param key
     * @param cookies
     * @return
     */
    public Cookie findCookie(String key, Cookie[] cookies);
}
