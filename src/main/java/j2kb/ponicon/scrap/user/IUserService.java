package j2kb.ponicon.scrap.user;

import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.user.dto.LoginRes;
import j2kb.ponicon.scrap.user.dto.PostJoinReq;
import j2kb.ponicon.scrap.user.dto.PostLoginReq;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public interface IUserService {

    public Long join(PostJoinReq postJoinReq);
    public LoginRes login(PostLoginReq postLoginReq, HttpServletResponse response);
    public User joinBySocial(String username, String name);
    public boolean checkEmailDuplicate(String email);
    public void logout(HttpServletResponse response);
    public LoginRes checkUserHasLogin(Cookie[] cookies);

}
