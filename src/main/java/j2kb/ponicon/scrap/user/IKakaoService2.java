package j2kb.ponicon.scrap.user;

import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.user.dto.LoginRes;
import j2kb.ponicon.scrap.user.dto.UserInfo;

import javax.servlet.http.HttpServletResponse;

public interface IKakaoService2 {

    public LoginRes login(String KakaoAccessToken, HttpServletResponse response);
}
