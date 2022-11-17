package j2kb.ponicon.scrap.user;

import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.user.dto.LoginRes;
import j2kb.ponicon.scrap.user.dto.UserInfo;

import javax.servlet.http.HttpServletResponse;

public interface IKakaoService {

    /**
     * 카카오 로그인
     * @param KakaoAccessToken
     * @param response
     * @return LoginRes
     */
    public LoginRes login(String KakaoAccessToken, HttpServletResponse response);
}
