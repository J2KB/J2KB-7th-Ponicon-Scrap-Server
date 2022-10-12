package j2kb.ponicon.scrap.user;

import j2kb.ponicon.scrap.domain.User;

import javax.servlet.http.HttpServletResponse;

public interface IKakaoService2 {

    public User login(String KakaoAccessToken, HttpServletResponse response);
}
