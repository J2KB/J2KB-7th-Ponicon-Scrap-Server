package j2kb.ponicon.scrap.user;

import j2kb.ponicon.scrap.response.BaseException;
import j2kb.ponicon.scrap.response.BaseExceptionStatus;
import j2kb.ponicon.scrap.response.BaseResponse;
import j2kb.ponicon.scrap.user.dto.GetUsernameSameRes;
import j2kb.ponicon.scrap.user.dto.PostJoinReq;
import j2kb.ponicon.scrap.user.dto.PostLoginReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;

    @PostMapping("/join")
    public BaseResponse join(@RequestBody PostJoinReq postJoinReq){

        // 요청한 값에 대한 validation 처리 필요

        userService.join(postJoinReq);

        return new BaseResponse("회원가입에 성공했습니다");
    }

    @GetMapping("/duplicate")
    public BaseResponse<GetUsernameSameRes> checkUsernameDuplicate(@RequestParam(name = "id")String username){

        // id 널값 처리해야함.

        boolean isDuplicate = userService.checkUsernameDuplicate(username);

        GetUsernameSameRes res = new GetUsernameSameRes(isDuplicate);
        return new BaseResponse<GetUsernameSameRes>(res);
    }

    @PostMapping("/login")
    public BaseResponse login(@RequestBody PostLoginReq postLoginReq, HttpServletResponse response){

        userService.login(postLoginReq, response);

        return new BaseResponse("로그인에 성공했습니다");
    }

    @GetMapping("/login/kakao")
    public String kakaoLogin(@RequestParam(name = "code") String code){
        System.out.println("code = " + code);

        kakaoService.login(code);
        return "카카오 로그인에 성공했습니다";
    }

//    @GetMapping("/test")
//    public String test(){
//
//    }

    @GetMapping("/logout")
    public String logout(){
        return "로그아웃에 성공했습니다";
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
    /* 테스트용 api 끝 */
}
