package j2kb.ponicon.scrap.user;

import j2kb.ponicon.scrap.user.dto.GetUsernameSameRes;
import j2kb.ponicon.scrap.user.dto.PostJoinReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public String join(@RequestBody PostJoinReq postJoinReq){
        return "회원가입에 성공했습니다";
    }

    @GetMapping("/id")
    public GetUsernameSameRes checkUsernameDuplicate(@RequestBody GetUsernameSameRes getUsernameSameRes){
        return null;
    }

    @PostMapping("/login")
    public String login(@RequestBody PostJoinReq postJoinReq, HttpServletResponse response){
        return "로그인에 성공했습니다";
    }

    @PostMapping("/login/kakao")
    public String loginKakao(){
        return "카카오 로그인에 성공했습니다";
    }

    @GetMapping("/logout")
    public String logout(){
        return "로그아웃에 성공했습니다";
    }
}
