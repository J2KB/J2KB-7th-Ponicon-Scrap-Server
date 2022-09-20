package j2kb.ponicon.scrap.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegexServiceTest {

    @Test
    @DisplayName("정규표현식 이름")
    public void test1(){
        boolean result = RegexService.checkName("aaaaaaaa");
        System.out.println("result = " + result);

        result = RegexService.checkName("aaa");
        System.out.println("result = " + result);

        result = RegexService.checkName("aaa가");
        System.out.println("result = " + result);

        result = RegexService.checkName("aaa!");
        System.out.println("result = " + result);

        result = RegexService.checkName("가가가");
        System.out.println("result = " + result);

        result = RegexService.checkName("aaaA가");
        System.out.println("result = " + result);
    }

    @Test
    @DisplayName("정규표현식 아이디")
    public void test2(){
        boolean result = RegexService.checkUsername("aaaaaaa");
        System.out.println("result = " + result);

        result = RegexService.checkUsername("1111111");
        System.out.println("result = " + result);

        result = RegexService.checkUsername("aaa111");
        System.out.println("result = " + result);

        result = RegexService.checkUsername("111aaa");
        System.out.println("result = " + result);
    }

    @Test
    @DisplayName("정규표현식 비번")
    public void test3(){
        boolean result = RegexService.checkPw("aaaaaa");
        System.out.println("result = " + result);

        result = RegexService.checkPw("1111111");
        System.out.println("result = " + result);

        result = RegexService.checkPw("aaa111");
        System.out.println("result = " + result);

        result = RegexService.checkPw("111aaa");
        System.out.println("result = " + result);

        result = RegexService.checkPw("aa11@@%#");
        System.out.println("result = " + result);
    }

}