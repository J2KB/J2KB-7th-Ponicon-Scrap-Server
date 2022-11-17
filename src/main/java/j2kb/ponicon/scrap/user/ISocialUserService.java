package j2kb.ponicon.scrap.user;

import j2kb.ponicon.scrap.user.dto.UserInfo;

public interface ISocialUserService {

    public UserInfo joinBySocial(String username, String name);

    /**
     * 가입된 유저인지 확인
     * @param username
     * @return 유저 인덱스를 반환. 만약, 가입되지 않은 유저면 -1을 반환
     */
    public Long checkUserHasJoin(String username);
}
