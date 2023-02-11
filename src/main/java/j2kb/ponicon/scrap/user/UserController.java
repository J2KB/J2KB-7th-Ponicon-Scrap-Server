package j2kb.ponicon.scrap.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import j2kb.ponicon.scrap.domain.User;

import j2kb.ponicon.scrap.response.BaseResponse;
import j2kb.ponicon.scrap.response.validationSequence.ValidationSequence;
import j2kb.ponicon.scrap.user.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static j2kb.ponicon.scrap.response.BaseExceptionStatus.*;
@Api(tags = "회원과 관련된 API")
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final IUserService userService;
    private final IKakaoServiceDutyOnServer kakaoService;
    private final IKakaoService kakaoService2;
    private final IAppleService appleService;

    /**
     * 회원가입
     * [POST] /user/join
     * @param postJoinReq
     * @return
     */
    @ApiOperation(value = "회원가입", notes = "/user/join")
    @PostMapping("/user/join")
    public BaseResponse join(@Validated(ValidationSequence.class) @RequestBody PostJoinReq postJoinReq){

        log.info("회원가입 시도: 아이디={}", postJoinReq.getEmail());

        userService.join(postJoinReq);

        log.info("회원가입 성공: 아이디={}", postJoinReq.getEmail());
        return new BaseResponse("회원가입에 성공했습니다");
    }

    /**
     * 아이디 중복 확인
     * [GET] /user/duplicate?id=
     * @param email
     * @return GetEmailSameRes
     */
    @ApiOperation(value = "아이디 중복 확인", notes = "[GET] user/duplicate?id=")
    @GetMapping("/user/duplicate")
    public BaseResponse<GetEmailSameRes> checkEmailDuplicate(@ApiParam(value = "User의 email 값", example = "test0303") @RequestParam(name = "id")String email){

        // id 널값 처리해야함.

        boolean isDuplicate = userService.checkIdDuplicate(email);

        log.info("아이디 중복 확인: 아이디={}, 중복여부={}", email, isDuplicate);
        GetEmailSameRes res = new GetEmailSameRes(isDuplicate);
        return new BaseResponse<GetEmailSameRes>(res);
    }

    /**
     * 일반 로그인
     * [POST] /user/login
     * @param postLoginReq
     * @param response
     * @return LoginRes
     */
    @ApiOperation(value = "일반 로그인", notes = "user/login")
    @PostMapping("/user/login")
    public BaseResponse<LoginRes> login(@Validated(ValidationSequence.class) @RequestBody PostLoginReq postLoginReq, HttpServletResponse response){

        log.info("로그인 시도: {}", postLoginReq.getEmail());
        LoginRes loginRes = userService.login(postLoginReq, response);

        log.info("로그인 성공: 아이디={}, idx={}", postLoginReq.getEmail(), loginRes.getId());
        return new BaseResponse<>(loginRes);
    }

    /**
     * 카카오 로그인 버전2
     * [POST] /user/login/kakao/v2
     * 클라이언트측으로부터 access토큰 받아옴.
     * @param postKakaoLoign2Req
     * @param response
     * @return LoginRes
     */
    @PostMapping("/user/login/kakao/v2")
    public BaseResponse<LoginRes> kakaoLogin2(@Validated(ValidationSequence.class) @RequestBody PostKakaoLoignReq postKakaoLoign2Req, HttpServletResponse response){

        log.info("카카오 로그인 시도");
        LoginRes loginRes = kakaoService2.login(postKakaoLoign2Req.getAccessToken(), response);

        log.info("카카오 로그인 성공: idx={}", loginRes.getId());
        return new BaseResponse<>(loginRes);
    }
    /**
     * 애플 로그인
     * [POST] /user/login/apple
     * @param postAppleLoginReq
     * @param response
     * @return LoginRes
     */
    @PostMapping("/user/login/apple")
    public BaseResponse<LoginRes> AppleLogin(@RequestBody PostAppleLoginReq postAppleLoginReq, HttpServletResponse response){

        log.info("애플 로그인 시도");
        if (!postAppleLoginReq.getEmail().isEmpty() || !postAppleLoginReq.getName().isEmpty()) {
            log.info("애플 로그인 가입 시도");
            appleService.join(postAppleLoginReq.getUserIdentifier(), postAppleLoginReq.getName());
            log.info("애플 로그인 가입 성공");
        }
        LoginRes loginRes = appleService.login(postAppleLoginReq.getUserIdentifier(), response);
        log.info("애플 로그인 성공: idx={}", loginRes.getId());
        return new BaseResponse<>(loginRes);
    }
    /**
     * 통합 로그아웃
     * [GET] /user/logout
     * @param response
     * @return
     */
    @ApiOperation(value = "통합 로그아웃", notes = "user/logout")
    @GetMapping("/user/logout")
    public BaseResponse logout(HttpServletResponse response){

        userService.logout(response);

        log.info("로그아웃 성공");
        return new BaseResponse("로그아웃에 성공했습니다");
    }


    /**
     * 로그인 된 유저인지 확인
     * [GET] /user/login
     * @param request
     * @return LoginRes
     */
    @GetMapping("/user/login")
    public BaseResponse<LoginRes> checkIsLogin(HttpServletRequest request){

        Cookie[] cookies = request.getCookies();

        LoginRes loginRes = userService.checkUserHasLogin(cookies);

        return new BaseResponse<>("로그인 된 유저입니다", loginRes);
    }

    /**
     * 회원탈퇴
     * [DELETE] /user/{user_id}
     * @param userId
     * @return
     */
    @DeleteMapping("/auth/user/{user_id}")
    public BaseResponse unreisterUser(@PathVariable("user_id") Long userId){

        log.info("회원탈퇴 시도: idx={}", userId);
        userService.unregister(userId);

        log.info("회원탈퇴 성공: idx={}", userId);
        return new BaseResponse("회원탈퇴가 완료되었습니다");
    }

    /**
     * 카카오 로그인 리다이렉션 url
     * [GET] user/login/kakao?code=
     * @param code
     * @param response
     * @return LoginRes
     */
    @ApiOperation(value = "카카오 로그인 리다이렉션 url", notes = "user/login/kakao?code=")
    @GetMapping("/user/login/kakao")
    public BaseResponse<LoginRes> kakaoLogin(@RequestParam(name = "code", required = false) String code, @RequestParam(name = "error", required = false) String error, HttpServletResponse response){

        if(error != null){
            log.error("error ={} " + error);

            return new BaseResponse(KAKAO_LOGIN_FAIL);
        }
        log.info("code = {}" , code);

        User user = kakaoService.login(code, response);

        return new BaseResponse<>(new LoginRes(user.getId()));
    }


    /* 테스트용 api */
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
