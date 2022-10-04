package j2kb.ponicon.scrap.user;

import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.user.dto.PostJoinReq;
import j2kb.ponicon.scrap.user.dto.PostLoginReq;

import javax.servlet.http.HttpServletResponse;

public interface IUserService {

    public void join(PostJoinReq postJoinReq);
    public boolean checkUsernameDuplicate(String username);
    public User login(PostLoginReq postLoginReq, HttpServletResponse response);
    public void logout(HttpServletResponse response);

}
