package j2kb.ponicon.scrap.user;

import j2kb.ponicon.scrap.user.dto.UserInfo;

public interface ISocialUserService {

    public UserInfo joinBySocial(String username, String name);
}
