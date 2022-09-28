package j2kb.ponicon.scrap.filter;

import io.jsonwebtoken.*;
import j2kb.ponicon.scrap.response.AuthorizationException;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static j2kb.ponicon.scrap.response.BaseExceptionStatus.*;
import static j2kb.ponicon.scrap.utils.JwtData.*;


public class AuthorizationFilter implements Filter {

    public class AuthorizationService{
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

        // 엑세스토큰 생성
        public String createAccessToken(String username){
            Date now = new Date();

            return Jwts.builder()
                    .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //헤더 타입 지정. Header.TYPE="type", Header.JWT_TYPE="jwt"
                    .setIssuer("hana-umc.shop") //토큰 발급자 설정 (iss)
                    .setIssuedAt(now) //발급 시간 설정 (iat) Date 타입만 추가 가능.
                    .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_MILLIS)) //만료시간 설정 (exp). Date 타입만 추가 가능.
                    .setSubject(username) //비공개 클래임을 설정할 수 있음. key-value
                    .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY) //해싱 알고리즘과 시크릿 key 설정
                    .compact(); //jwt 토큰 생성
        }

        /**
         * 엑세스토큰 쿠키 만들기
         * @param accessToken 엑스스토큰
         * @return Cookie
         */
        public Cookie createAccessCookie(String accessToken){

            Cookie cookie = new Cookie("accessToken", accessToken);
            cookie.setPath("/"); // 모든 경로에서 접근 가능하도록
//        cookie.setSecure(true);
//        cookie.setHttpOnly(true);

            return cookie;
        }

        // username으로 access토큰 재발급받고 쿠키에 set하기
        public void reissueAccessTokenAndSetCookie(String refreshToken, HttpServletResponse response){
            Jws<Claims> claims = authorizationService.validationJwt(refreshToken);
            String username = claims.getBody().getSubject();

            String reAccessToken = authorizationService.createAccessToken(username);
            Cookie reAccessCookie = authorizationService.createAccessCookie(reAccessToken);

            response.addCookie(reAccessCookie);
        }

    }

    // 싱글톤 패턴 적용 필요
    AuthorizationService authorizationService;

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
        Cookie[] cookies = httpReq.getCookies();

        Cookie accessCookie = authorizationService.findCookie("accessToken", cookies);
        Cookie refreshCookie = authorizationService.findCookie("refreshToken", cookies);

        String accessToken, refreshToken;

        if(accessCookie != null && refreshCookie == null){
            accessToken = accessCookie.getValue();

            authorizationService.validationJwt(accessToken);
        }
        else if(accessCookie != null && refreshCookie != null){
            accessToken = accessCookie.getValue();

            try {
                authorizationService.validationJwt(accessToken);
            }  catch (AuthorizationException e){
                // access토큰 시간 만료
                // 그러면 refresh토큰으로 access토큰 재발급
                if(e.getStatus() == JWT_TOKEN_EXPIRE){
                    refreshToken = refreshCookie.getValue();
                    authorizationService.reissueAccessTokenAndSetCookie(refreshToken, (HttpServletResponse) response);
                }

                throw e;
            }
        }
        else if(accessCookie == null && refreshCookie != null){
            refreshToken = refreshCookie.getValue();
            authorizationService.reissueAccessTokenAndSetCookie(refreshToken, (HttpServletResponse) response);
        }
        else if(accessCookie == null && refreshCookie == null){
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
