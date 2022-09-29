package j2kb.ponicon.scrap.user;

import j2kb.ponicon.scrap.domain.User;

import j2kb.ponicon.scrap.response.BaseResponse;
import j2kb.ponicon.scrap.user.dto.GetUsernameSameRes;
import j2kb.ponicon.scrap.user.dto.LoginRes;
import j2kb.ponicon.scrap.user.dto.PostJoinReq;
import j2kb.ponicon.scrap.user.dto.PostLoginReq;
import j2kb.ponicon.scrap.utils.RegexService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static j2kb.ponicon.scrap.response.BaseExceptionStatus.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;


    /**
     * 회원가입
     * [POST] /user/join
     * @param postJoinReq
     * @return
     */
    @PostMapping("/join")
    public BaseResponse join(@Validated @RequestBody PostJoinReq postJoinReq){

        // 요청한 값에 대한 validation 처리 필요
//        if(postJoinReq.getUsername() == null || postJoinReq.getUsername().isEmpty()){
//            return new BaseResponse(JOIN_USERNAME_EMPTY);
//        }
//        if(postJoinReq.getPassword() == null || postJoinReq.getPassword().isEmpty()){
//            return new BaseResponse(JOIN_PASSWORD_EMPTY);
//        }
//        if(postJoinReq.getName() == null || postJoinReq.getName().isEmpty()){
//            return new BaseResponse(JOIN_NAME_EMPTY);
//        }

        // 형식 확인
//        if(!RegexService.checkUsername(postJoinReq.getUsername())){
//            return new BaseResponse(JOIN_USERNAME_INVALID);
//        }
//        if(!RegexService.checkPw(postJoinReq.getPassword())){
//            return new BaseResponse(JOIN_PASSWORD_INVALID);
//        }
//        if(!RegexService.checkName(postJoinReq.getName())){
//            return new BaseResponse(JOIN_NAME_INVALID);
//        }

        userService.join(postJoinReq);

        return new BaseResponse("회원가입에 성공했습니다");
    }

    /**
     * 아이디 중복 확인
     * [GET] user/duplicate?id=
     * @param username
     * @return
     */
    @GetMapping("/duplicate")
    public BaseResponse<GetUsernameSameRes> checkUsernameDuplicate(@RequestParam(name = "id")String username){

        // id 널값 처리해야함.

        boolean isDuplicate = userService.checkUsernameDuplicate(username);

        GetUsernameSameRes res = new GetUsernameSameRes(isDuplicate);
        return new BaseResponse<GetUsernameSameRes>(res);
    }

    /**
     * 일반 로그인
     * [POST] user/login
     * @param postLoginReq
     * @param response
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<LoginRes> login(@Validated @RequestBody PostLoginReq postLoginReq, HttpServletResponse response){

//        if(postLoginReq.getUsername() == null || postLoginReq.getUsername().isEmpty()){
//            return new BaseResponse(JOIN_USERNAME_EMPTY);
//        }
//        if(postLoginReq.getPassword() == null || postLoginReq.getPassword().isEmpty()){
//            return new BaseResponse(JOIN_PASSWORD_EMPTY);
//        }
//        if(postLoginReq.getAutoLogin() == null){
//            return new BaseResponse(LOGIN_AUTOLOGIN_EMPTY);
//        }

        User user = userService.login(postLoginReq, response);

        return new BaseResponse<>(new LoginRes(user.getId()));
    }

    /**
     * 카카오 로그인 리다이렉션 url
     * [GET] user/login/kakao?code=
     * @param code
     * @param response
     * @return
     */
    @GetMapping("/login/kakao")
    public BaseResponse<LoginRes> kakaoLogin(@RequestParam(name = "code", required = false) String code, @RequestParam(name = "error", required = false) String error, HttpServletResponse response){

        if(error != null){
            System.out.println("error = " + error);

            return new BaseResponse(KAKAO_LOGIN_FAIL);
        }

        System.out.println("code = " + code);

        User user = kakaoService.login(code, response);

        return new BaseResponse<>(new LoginRes(user.getId()));
    }

    // 통합 로그아웃
    // [GET] user/logout
    @GetMapping("/logout")
    public BaseResponse logout(HttpServletResponse response){

        userService.logout(response);

        return new BaseResponse("로그아웃에 성공했습니다");
    }


    /* 테스트용 api */
    @GetMapping("/test/error")
    public BaseResponse error(){
        userService.error();

        return null;
    }

    @GetMapping("/test/error2")
    public BaseResponse error2(){
        userService.error2();

        return null;
    }

    @GetMapping("/test/category")
    public String categorySave(){
        userService.testSave();
        return "카테고리 테스트 세이브";
    }

    @GetMapping("/test/cookie")
    public void getCookies(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        for(Cookie c : cookies) {
            System.out.printf("%s: %s\n", c.getName(), c.getValue()); // 쿠키 이름과 값 가져오기
        }
    }
    /* 테스트용 api 끝 */
}
