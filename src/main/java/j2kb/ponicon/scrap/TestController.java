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
    
    @GetMapping("/cookie")
    public String cookieTest(@CookieValue("refreshToken")Cookie cookie, HttpServletRequest request){
        System.out.println("cookie.getValue() = " + cookie.getValue());
        System.out.println("엥? cookie.getMaxAge() = " + cookie.getMaxAge());
        System.out.println("cookie.isHttpOnly() = " + cookie.isHttpOnly());
        System.out.println("cookie.getName() = " + cookie.getName());
        System.out.println("cookie.getPath() = " + cookie.getPath());
        System.out.println("cookie.getSecure() = " + cookie.getSecure());

        return "쿠키 테스트";
    }

    @GetMapping("/cookie2")
    public String cookieTest2(HttpServletRequest request){

        Cookie[] cookies = request.getCookies();
        for(Cookie c : cookies){
            System.out.println("c.getName() = " + c.getName());
            System.out.println("c.getMaxAge() = " + c.getMaxAge());
        }

        return "쿠키 테스트";
    }

    @GetMapping("/cookie3")
    public String cookieTest3(HttpServletResponse response){

        org.springframework.boot.web.server.Cookie cookie = new org.springframework.boot.web.server.Cookie();
        cookie.setName("springCookie");
        cookie.setMaxAge(Duration.ofMinutes(30));

        return "쿠키 테스트";
    }

//    @GetMapping("/cookie4")
//    public String cookieTest4(HttpServletResponse response, HttpServletRequest request){
//    }
}
