package j2kb.ponicon.scrap.user;

import j2kb.ponicon.scrap.user.dto.LoginRes;

import javax.servlet.http.HttpServletResponse;

public interface IAppleService {
    /**
     * 애플 로그인
     * @param email
     * @param response
     * @return LoginRes
     */
    public LoginRes login(String email, HttpServletResponse response);
    /**
     * 애플 가입
     * @param userIdentifier
     * @param name
     * @return LoginRes
     */
    public void join(String userIdentifier, String name);
}
