package j2kb.ponicon.scrap.user;

import j2kb.ponicon.scrap.category.CategoryRepository;
import j2kb.ponicon.scrap.category.CategoryServiceImpl;
import j2kb.ponicon.scrap.domain.Category;
import j2kb.ponicon.scrap.domain.Link;
import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.response.AuthorizationException;
import j2kb.ponicon.scrap.response.BaseException;
import j2kb.ponicon.scrap.response.BaseExceptionStatus;
import j2kb.ponicon.scrap.user.dto.LoginRes;
import j2kb.ponicon.scrap.user.dto.PostJoinReq;
import j2kb.ponicon.scrap.user.dto.PostLoginReq;
import j2kb.ponicon.scrap.user.dto.UserInfo;
import j2kb.ponicon.scrap.utils.ICookieService;
import j2kb.ponicon.scrap.utils.IJwtService;
import j2kb.ponicon.scrap.utils.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static j2kb.ponicon.scrap.response.BaseExceptionStatus.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService, ISocialUserService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final IJwtService jwtService;
    private final ICookieService cookieService;
    private final CategoryServiceImpl categoryService;

    /**
     * 회원가입
     * @param postJoinReq 유저에 대한 정보
     */
    @Transactional
    public Long join(PostJoinReq postJoinReq){

        String email = postJoinReq.getEmail(); // 아이디
        String pw = postJoinReq.getPassword(); // 비번
        String name = postJoinReq.getName(); // 이름

        // 아이디 중복 체크
        if(checkEmailDuplicate(email)){
            throw new BaseException(DUPULICATE_EMAIL);
        }

        // 비번 암호화
        pw = SHA256.encrypt(pw);

        // 유저 생성
        User user = new User(email, pw, name);

        // 기본 카테고리 생성
        categoryService.saveBasicCategory(user);

        // 유저 저장
        userRepository.save(user);

        return user.getId();
    }

    // 소셜 로그인 유저의 회원가입
    @Transactional
    public UserInfo joinBySocial(String username, String name){

        User user = new User(username, username, name);

        // 기본 카테고리 생성
        categoryService.saveBasicCategory(user);

        userRepository.save(user);

        UserInfo userInfo = new UserInfo(user.getId(), user.getEmail(), user.getName());

        return userInfo;
    }

    // 회원가입된 유저인지 확인
    @Override
    public Long checkUserHasJoin(String username){

        User user = userRepository.findByEmail(username);

        if(user == null){
            return -1L; // 회원가입 안됨
        }
        else {
            return user.getId(); // 회원가입 됨.
        }
    }

    /**
     * 아이디 중복 확인
     * @param email 아이디
     * @return 중복이면 true를 리턴.
     */
    public boolean checkEmailDuplicate(String email){
        User user = userRepository.findByEmail(email);

        if(user == null){
            return false; // 중복 X
        } else {
            return true; // 중복 O
        }
    }

    /**
     * 일반 로그인
     * @param postLoginReq 아이디 비밀번호가 담긴 dto
     * @param response
     * @return User
     */
    public LoginRes login(PostLoginReq postLoginReq, HttpServletResponse response){

        String email = postLoginReq.getEmail();
        String pw = postLoginReq.getPassword();
        boolean isAutoLogin = postLoginReq.getAutoLogin();

        // 유저 확인
        pw = SHA256.encrypt(pw); // 비번 암호화
        User user = userRepository.findByEmailAndPassword(email, pw);

        // 해당하는 유저가 없음.
        if(user == null){
            throw new BaseException(LOGIN_USER_NOT_EXIST);
        }

        // 토큰 발급
        String accessToken = jwtService.createAccessToken(email);
        String refreshToken = jwtService.createRefreshToken(email);

        // 쿠키 발급
        Cookie accessCookie = cookieService.createAccessCookie(accessToken, isAutoLogin);
        Cookie refreshCookie = cookieService.createRefreshCookie(refreshToken, isAutoLogin);

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);

        LoginRes loginRes = new LoginRes(user.getId());

        return loginRes;
    }

    // 로그인 된 유저인지 확인
    public LoginRes checkUserHasLogin(Cookie[] cookies){
        Cookie accessCookie = cookieService.findCookie("accessToken", cookies);
        Cookie refreshCookie = cookieService.findCookie("refreshToken", cookies);

        if(accessCookie == null && refreshCookie == null){
            throw new AuthorizationException(NOT_LOGIN_USER);
        }

        String email = null;
        String token;
        if(accessCookie != null){
            token = accessCookie.getValue();
            email = jwtService.getEmailByJwt(token);
        }
        else if(refreshCookie != null){
            token = refreshCookie.getValue();
            email = jwtService.getEmailByJwt(token);
        }

        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new AuthorizationException(USER_NOT_EXIST);
        }

        LoginRes loginRes = new LoginRes(user.getId());

        return loginRes;
    }

    // 회원탈퇴
    @Override
    @Transactional
    public boolean unregister(Long userId) {

        User user = findUserOne(userId);
        userRepository.delete(user);

        return false;
    }

    /**
     * 로그아웃
     * @param response
     */
    public void logout(HttpServletResponse response){

        // accessToken을 삭제
        Cookie accessCookie = new Cookie("accessToken", null);
        accessCookie.setMaxAge(0);
        accessCookie.setPath("/");
        response.addCookie(accessCookie);

        // refreshToken 삭제
        Cookie refreshCookie = new Cookie("refreshToken", null);
        refreshCookie.setMaxAge(0);
        refreshCookie.setPath("/");
        response.addCookie(refreshCookie);
    }

    // 유저 찾기
    public User findUserOne(Long userId){
        Optional<User> optUser = userRepository.findById(userId);
        // 해당 하는 자료가 없으면 에러 발생시키기.
        return optUser.orElseThrow(() -> new BaseException(USER_NOT_EXIST));
    }


    /* 테스트 코드 */
    public void testSave(){
        Optional<User> tempUser = userRepository.findById(6L);
        User user = tempUser.get();

        Category category = new Category("샘플 카테고리", 5, user);
        categoryRepository.save(category);
    }

    public void error(){
        throw new BaseException(BaseExceptionStatus.TEST_ERROR);
    }

    public void error2(){
        throw new RuntimeException("의도적으로 에러 발생!");
    }
    /* 테스트 코드 끝 */

}
