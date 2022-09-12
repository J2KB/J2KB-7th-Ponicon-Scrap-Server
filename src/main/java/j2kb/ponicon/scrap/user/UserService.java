package j2kb.ponicon.scrap.user;

import j2kb.ponicon.scrap.category.CategoryRepository;
import j2kb.ponicon.scrap.domain.Category;
import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.response.BaseException;
import j2kb.ponicon.scrap.response.BaseExceptionStatus;
import j2kb.ponicon.scrap.user.dto.PostJoinReq;
import j2kb.ponicon.scrap.user.dto.PostLoginReq;
import j2kb.ponicon.scrap.utils.CookieService;
import j2kb.ponicon.scrap.utils.JwtService;
import j2kb.ponicon.scrap.utils.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static j2kb.ponicon.scrap.response.BaseExceptionStatus.DUPULICATE_USERNAME;
import static j2kb.ponicon.scrap.response.BaseExceptionStatus.LOGIN_USER_NOT_EXIST;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final JwtService jwtService;
    private final CookieService cookieService;

    // 회원가입
    @Transactional
    public void join(PostJoinReq postJoinReq){

        String username = postJoinReq.getUsername();
        String pw = postJoinReq.getPassword();
        String name = postJoinReq.getName();

        // 아이디 중복 체크
        if(checkUsernameDuplicate(username)){
            throw new BaseException(DUPULICATE_USERNAME);
        }

        // 비번 암호화
        pw = SHA256.encrypt(pw);

        // 유저 생성
        User user = new User(username, pw, name);

        // 기본 카테고리 생성
        saveBasicCategory(user);

        // 유저 저장
        userRepository.save(user);
    }

    // 기본 카테고리 저장
    @Transactional
    private void saveBasicCategory(User user){
        List<String> categoryNames = new ArrayList<>(List.of("분류되지 않은 자료"));

        for(int i=0; i<categoryNames.size(); i++){
            new Category(categoryNames.get(i), i, user);
            //따로 카테고리 저장 안하더라도 Cascade 설정 해둬서 자동으로 insert 됨.
        }
    }

    // 아이디 중복 체크
    public boolean checkUsernameDuplicate(String username){
        User user = userRepository.findByUsername(username);

        if(user == null){
            return false; // 중복 X
        } else {
            return true; // 중복 O
        }
    }

    // 로그인
    public void login(PostLoginReq postLoginReq, HttpServletResponse response){

        String username = postLoginReq.getUsername();
        String pw = postLoginReq.getPassword();
        boolean isAutoLogin = postLoginReq.isAutoLogin();

        // 유저 확인
        pw = SHA256.encrypt(pw); // 비번 암호화
        User user = userRepository.findByUsernameAndPassword(username, pw);

        if(user == null){
            throw new BaseException(LOGIN_USER_NOT_EXIST);
        }

        // 토큰 발급
        String accessToken = jwtService.createAccessToken(username);
        String refreshToken = jwtService.createRefreshToken(username);

        // 쿠키 발급
        Cookie accessCookie = cookieService.createAccessCookie(accessToken, isAutoLogin);
        Cookie refreshCookie = cookieService.createRefreshCookie(refreshToken, isAutoLogin);

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
    }

    public void kakaoLogin(String code){

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
