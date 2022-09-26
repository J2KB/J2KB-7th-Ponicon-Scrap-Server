package j2kb.ponicon.scrap.filter;

import j2kb.ponicon.scrap.utils.CookieService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class AuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        System.out.println("Authorization 필터 초기화");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Authorization 필터 시작 전");
        HttpServletRequest httpReq = (HttpServletRequest) request;
        Cookie[] cookies = httpReq.getCookies();
        Cookie accessCookie = findCookie("accessToken", cookies);

        if(accessCookie != null){
            System.out.println("accessCookie.getValue() = " + accessCookie.getValue());
        }
        else {
            System.out.println("어세스쿠키 없음");
        }

        chain.doFilter(request, response);
        System.out.println("Authorization 필터 시작 후");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        System.out.println("Authorization 필터 삭제");
    }

    // 쿠키 찾기
    private Cookie findCookie(String key, Cookie[] cookies){
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
