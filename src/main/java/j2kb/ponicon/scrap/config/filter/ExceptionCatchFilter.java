package j2kb.ponicon.scrap.config.filter;

import com.google.gson.Gson;
import j2kb.ponicon.scrap.response.AuthorizationException;
import j2kb.ponicon.scrap.response.BaseExceptionStatus;
import j2kb.ponicon.scrap.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static j2kb.ponicon.scrap.response.BaseExceptionStatus.DO_NOT_LOGIN_USER;

/**
 * 인증 예외처리 필터
 * AuthorizationException 예외를 잡아 응답값 처리해줌
 */
@Slf4j
public class ExceptionCatchFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        System.out.println("예외캐치필터 초기화");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (AuthorizationException e){
//            e.printStackTrace();
            setBaseResponse((HttpServletResponse) response, e.getStatus());
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    // 응답 설정
    private void setBaseResponse(HttpServletResponse response, BaseExceptionStatus eStatus) throws IOException{
//        response.setContentType("charset=UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept");


        response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401 응답코드로 설정

        log.info("로그인 예외처리: {}", eStatus.getMessage());
        BaseResponse baseResponse = new BaseResponse(DO_NOT_LOGIN_USER);
        String json = new Gson().toJson(baseResponse);
//        System.out.println("json = " + json);
        response.getWriter().write(json);
    }
}
