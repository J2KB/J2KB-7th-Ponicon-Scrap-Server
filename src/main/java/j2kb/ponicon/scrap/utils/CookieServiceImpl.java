package j2kb.ponicon.scrap.utils;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

import static j2kb.ponicon.scrap.utils.JwtData.ACCESS_COOKIE_EXPIRE_SECOND;
import static j2kb.ponicon.scrap.utils.JwtData.REFRESH_COOKIE_EXPIRE_SECOND;

/**
 * 쿠키 관련 서비스 코드
 */
@Service
public class CookieServiceImpl implements ICookieService{

    /**
     * 리프레시토큰 쿠키 만들기
     * @return Cookie
     */
    public Cookie createRefreshCookie(String refreshToken, boolean autoLogin){

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setPath("/"); // 모든 경로에서 접근 가능하도록
//        cookie.setSecure(true);
//        cookie.setHttpOnly(true);

        if(autoLogin){
            cookie.setMaxAge(REFRESH_COOKIE_EXPIRE_SECOND);
            // 이렇게 maxAge 설정 안하면, 앱 끌시 쿠키 삭제됨.
        }
        else {
            cookie.setMaxAge(0);
            // 자동로그인이 아니면 refreshh쿠키 안만듦.
        }

        System.out.println("cookie.getMaxAge() = " + cookie.getMaxAge());

        return cookie;
    }

    /**
     * 엑세스토큰 쿠키 만들기
     * @param accessToken 엑스스토큰
     * @return Cookie
     */
    public Cookie createAccessCookie(String accessToken, boolean autoLogin){

        Cookie cookie = new Cookie("accessToken", accessToken);
        cookie.setPath("/"); // 모든 경로에서 접근 가능하도록
//        cookie.setSecure(true);
//        cookie.setHttpOnly(true);
        if(autoLogin){
            cookie.setMaxAge(ACCESS_COOKIE_EXPIRE_SECOND);
            // 이렇게 maxAge 설정 안하면, 앱 끌시 쿠키 삭제됨.
        }

        return cookie;
    }

    // 쿠키 찾기
    public Cookie findCookie(String key, Cookie[] cookies){
        if(cookies == null){
            return null;
        }

        for(Cookie cookie : cookies){
            if(cookie.getName().equals(key)){
                return cookie;
            }
        }

        return null;
    }
}
