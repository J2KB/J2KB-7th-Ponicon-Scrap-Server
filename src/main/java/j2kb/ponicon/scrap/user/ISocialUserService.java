package j2kb.ponicon.scrap.user;

import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.user.dto.LoginRes;

public interface ISocialUserService {

    public LoginRes joinBySocial(String username, String name);
}
