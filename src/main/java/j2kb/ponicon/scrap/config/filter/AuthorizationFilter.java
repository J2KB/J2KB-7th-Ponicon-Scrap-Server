package j2kb.ponicon.scrap.config.filter;

import io.jsonwebtoken.*;
import j2kb.ponicon.scrap.response.AuthorizationException;
import j2kb.ponicon.scrap.utils.ICookieService;
import j2kb.ponicon.scrap.utils.IJwtService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static j2kb.ponicon.scrap.response.BaseExceptionStatus.*;
import static j2kb.ponicon.scrap.utils.JwtData.*;


public class AuthorizationFilter implements Filter {

    private class AuthorizationService{
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

        // jwt 유효 확인
        public Jws<Claims> validationJwt(String jwtToken){
            Jws<Claims> claims;
            try{
                claims = Jwts.parser()
                        .setSigningKey(JWT_SECRET_KEY)
                        .parseClaimsJws(jwtToken);

                return claims;

            } catch (ExpiredJwtException e){ //시간 만료
                throw new AuthorizationException(JWT_TOKEN_EXPIRE);
            } catch (Exception ignored) {
                throw new AuthorizationException(JWT_TOKEN_INVALID);
            }
        }


        // refresh토큰으로 access토큰 재발급받고 쿠키에 set하기
        public void reissueAccessTokenAndSetCookie(String refreshToken, boolean autoLogin, HttpServletResponse response){
            Jws<Claims> claims = authorizationService.validationJwt(refreshToken);
            String username = claims.getBody().getSubject();

//            String reAccessToken = authorizationService.createAccessToken(username);
            String reAccessToken = jwtService.createAccessToken(username);
            Cookie reAccessCookie = cookieService.createAccessCookie(reAccessToken, autoLogin);

            response.addCookie(reAccessCookie);
        }

    }

    // 싱글톤 패턴 적용 필요
    private AuthorizationService authorizationService;
    private final IJwtService jwtService;
    private final ICookieService cookieService;

    public AuthorizationFilter(IJwtService jwtService, ICookieService cookieService) {
        this.jwtService = jwtService;
        this.cookieService = cookieService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        System.out.println("Authorization 필터 초기화");
        authorizationService = new AuthorizationService();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Authorization 필터 시작 전");
        HttpServletRequest httpReq = (HttpServletRequest) request;
        // 전체 쿠키 목록 가져오기
        Cookie[] cookies = httpReq.getCookies();

        Cookie accessCookie = authorizationService.findCookie("accessToken", cookies);
        Cookie refreshCookie = authorizationService.findCookie("refreshToken", cookies);

        String accessToken, refreshToken;

        // access쿠키는 있으나, refresh쿠키는 없는경우
        if(accessCookie != null && refreshCookie == null){
            accessToken = accessCookie.getValue();

            authorizationService.validationJwt(accessToken);
        }
        // access쿠키, refresh쿠키 둘다 있는경우
        else if(accessCookie != null && refreshCookie != null){
            accessToken = accessCookie.getValue();

            try {
                authorizationService.validationJwt(accessToken);
            }  catch (AuthorizationException e){
                // access토큰 시간 만료
                // 그러면 refresh토큰으로 access토큰 재발급
                if(e.getStatus() == JWT_TOKEN_EXPIRE){
                    refreshToken = refreshCookie.getValue();
                    boolean autoLogin = (refreshCookie.getMaxAge() == -1) ? false : true; // maxage == -1 이면, 자동로그인 x임.
                    authorizationService.reissueAccessTokenAndSetCookie(refreshToken, autoLogin, (HttpServletResponse) response);
                }

                throw e;
            }
        }
        // access쿠키는 없으나 refresh쿠키는 있는경우
        else if(accessCookie == null && refreshCookie != null){
            refreshToken = refreshCookie.getValue();
            boolean autoLogin = (refreshCookie.getMaxAge() == -1) ? false : true;

            authorizationService.reissueAccessTokenAndSetCookie(refreshToken, autoLogin, (HttpServletResponse) response);
        }
        // 아무것도 없는 경우
        else {
            throw new AuthorizationException(UNAUTHORIZED_USER_ACCESS);
        }

        chain.doFilter(request, response);
        System.out.println("Authorization 필터 시작 후");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        System.out.println("Authorization 필터 삭제");
    }
}
