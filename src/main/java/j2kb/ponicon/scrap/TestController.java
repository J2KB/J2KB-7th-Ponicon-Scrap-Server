package j2kb.ponicon.scrap;

import lombok.Getter;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "merge test pull request test hs pull request test 스크랩 테스트 성공";
    }

    @GetMapping("/auth")
    public String authTest(){
        return "인증 필터 테스트";
    }


    @GetMapping("/cicd")
    public String authTest2(){
        return "ci/cd 테스트1000";
    }

    @GetMapping("/time")
    public String timeTest(){
        return LocalDateTime.now().toString();
    }
}
