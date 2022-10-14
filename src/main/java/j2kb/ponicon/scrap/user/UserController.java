package j2kb.ponicon.scrap.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import j2kb.ponicon.scrap.domain.User;

import j2kb.ponicon.scrap.response.BaseResponse;
import j2kb.ponicon.scrap.response.validationSequence.ValidationSequence;
import j2kb.ponicon.scrap.user.dto.*;
import j2kb.ponicon.scrap.utils.ICookieService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static j2kb.ponicon.scrap.response.BaseExceptionStatus.*;
@Api(tags = "회원과 관련된 API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final IKakaoService kakaoService;
    private final IKakaoService2 kakaoService2;

    /**
     * 회원가입
     * [POST] /user/join
     * @param postJoinReq
     * @return
     */
    @ApiOperation(value = "회원가입", notes = "/user/join")
    @PostMapping("/join")
    public BaseResponse join(@Validated(ValidationSequence.class) @RequestBody PostJoinReq postJoinReq){

        userService.join(postJoinReq);

        return new BaseResponse("회원가입에 성공했습니다");
    }

    /**
     * 아이디 중복 확인
     * [GET] user/duplicate?id=
     * @param username
     * @return
     */
    @ApiOperation(value = "아이디 중복 확인", notes = "[GET] user/duplicate?id=")
    @GetMapping("/duplicate")
    public BaseResponse<GetUsernameSameRes> checkUsernameDuplicate(@ApiParam(value = "User의 username 값", example = "test0303") @RequestParam(name = "id")String username){

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
    @ApiOperation(value = "일반 로그인", notes = "user/login")
    @PostMapping("/login")
    public BaseResponse<LoginRes> login(@Validated(ValidationSequence.class) @RequestBody PostLoginReq postLoginReq, HttpServletResponse response){

        User user = userService.login(postLoginReq, response);

        return new BaseResponse<>(new LoginRes(user.getId()));
    }

    // 통합 로그아웃
    // [GET] user/logout

    @ApiOperation(value = "통합 로그아웃", notes = "user/logout")
    @GetMapping("/logout")
    public BaseResponse logout(HttpServletResponse response){

        userService.logout(response);

        return new BaseResponse("로그아웃에 성공했습니다");
    }
    // 카카오 로그인 버전 2

    @PostMapping("login/kakao/v2")
    public BaseResponse<LoginRes> kakaoLogin2(@Validated(ValidationSequence.class) @RequestBody PostKakaoLoign2Req postKakaoLoign2Req, HttpServletResponse response){

        User user = kakaoService2.login(postKakaoLoign2Req.getAccessToken(), response);

        LoginRes loginRes = new LoginRes(user.getId());

        return new BaseResponse<>(loginRes);
    }

    // 로그인 된 유저인지 확인
    @GetMapping("/login")
    public BaseResponse<LoginRes> checkIsLogin(HttpServletRequest request){

        Cookie[] cookies = request.getCookies();

        User user = userService.checkUserHasLogin(cookies);

        return new BaseResponse<>(new LoginRes(user.getId()));
    }

    /**
     * 카카오 로그인 리다이렉션 url
     * [GET] user/login/kakao?code=
     * @param code
     * @param response
     * @return
     */
    @ApiOperation(value = "카카오 로그인 리다이렉션 url", notes = "user/login/kakao?code=")
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


//    /* 테스트용 api */
//    @GetMapping("/test/error")
//    public BaseResponse error(){
//        userService.error();
//
//        return null;
//    }
//
//    @GetMapping("/test/error2")
//    public BaseResponse error2(){
//        userService.error2();
//
//        return null;
//    }
//
//    @GetMapping("/test/category")
//    public String categorySave(){
//        userService.testSave();
//        return "카테고리 테스트 세이브";
//    }

    @GetMapping("/test/cookie")
    public void getCookies(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        for(Cookie c : cookies) {
            System.out.printf("%s: %s\n", c.getName(), c.getValue()); // 쿠키 이름과 값 가져오기
        }
    }
    /* 테스트용 api 끝 */
}
