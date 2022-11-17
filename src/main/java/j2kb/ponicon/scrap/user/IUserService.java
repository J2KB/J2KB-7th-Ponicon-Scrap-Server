package j2kb.ponicon.scrap.user;

import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.user.dto.LoginRes;
import j2kb.ponicon.scrap.user.dto.PostJoinReq;
import j2kb.ponicon.scrap.user.dto.PostLoginReq;
import j2kb.ponicon.scrap.user.dto.UserInfo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public interface IUserService {

    public Long join(PostJoinReq postJoinReq);
    public LoginRes login(PostLoginReq postLoginReq, HttpServletResponse response);
    public boolean checkEmailDuplicate(String email);
    public void logout(HttpServletResponse response);
    public LoginRes checkUserHasLogin(Cookie[] cookies);
    public boolean unregister(Long userId);
}
