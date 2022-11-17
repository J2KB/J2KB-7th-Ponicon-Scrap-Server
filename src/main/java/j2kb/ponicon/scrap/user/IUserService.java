package j2kb.ponicon.scrap.user;

import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.user.dto.LoginRes;
import j2kb.ponicon.scrap.user.dto.PostJoinReq;
import j2kb.ponicon.scrap.user.dto.PostLoginReq;
import j2kb.ponicon.scrap.user.dto.UserInfo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public interface IUserService {

    /**
     * 회원가입
     * @param postJoinReq
     * @return 회원가입된 유저의 idx
     */
    public Long join(PostJoinReq postJoinReq);

    /**
     * 로그인
     * @param postLoginReq
     * @param response
     * @return LoginRes
     */
    public LoginRes login(PostLoginReq postLoginReq, HttpServletResponse response);

    /**
     * 아이디 중복 확인
     * @param id
     * @return
     */
    public boolean checkIdDuplicate(String id);

    /**
     * 로그아웃
     * @param response
     */
    public void logout(HttpServletResponse response);

    /**
     * 로그인 된 유저인지 확인
     * @param cookies 쿠키 목록
     * @return LoginRes
     */
    public LoginRes checkUserHasLogin(Cookie[] cookies);

    /**
     * 회원 탈퇴
     * @param userId
     * @return
     */
    public boolean unregister(Long userId);
}
