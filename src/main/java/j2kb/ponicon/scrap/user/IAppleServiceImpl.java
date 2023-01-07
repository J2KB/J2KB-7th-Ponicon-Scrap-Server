package j2kb.ponicon.scrap.user;

import j2kb.ponicon.scrap.user.dto.LoginRes;
import j2kb.ponicon.scrap.user.dto.UserInfo;
import j2kb.ponicon.scrap.utils.ICookieService;
import j2kb.ponicon.scrap.utils.IJwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class IAppleServiceImpl implements IAppleService{

    private final IJwtService jwtService;
    private final ICookieService cookieService;
    private final ISocialUserService userService;

    @Override
    public LoginRes login(String userIdentifier, HttpServletResponse response) {
        // user 조회
        Long userId = userService.checkUserHasJoin(userIdentifier);
        // 토큰 발급
        String accessToken = jwtService.createAccessToken(userIdentifier);
        String refreshToken = jwtService.createRefreshToken(userIdentifier);

        // 쿠키 발급
        Cookie accessCookie = cookieService.createAccessCookie(accessToken, true);
        Cookie refreshCookie = cookieService.createRefreshCookie(refreshToken, true);
        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);

        return new LoginRes(userId);
    }

    @Override
    public void join(String userIdentifier, String name) {
        Long userId = userService.checkUserHasJoin(userIdentifier);
        // 해당하는 사용자가 없으면 자동으로 회원가입 진행
        if(userId == -1L){
            log.info("회원가입이 되지 않은 유저의 애플 로그인 발생. 회원가입 진행: idx={}", userIdentifier);
            userService.joinBySocial(userIdentifier, name);
        }
    }
}
