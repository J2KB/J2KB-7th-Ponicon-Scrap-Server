package j2kb.ponicon.scrap.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReqularExpressionTest {

    @Test
    @DisplayName("정규표현식 이름")
    public void test1(){
        boolean result = ReqularExpression.checkName("aaaaaaaa");
        System.out.println("result = " + result);

        result = ReqularExpression.checkName("aaa");
        System.out.println("result = " + result);

        result = ReqularExpression.checkName("aaa가");
        System.out.println("result = " + result);

        result = ReqularExpression.checkName("aaa!");
        System.out.println("result = " + result);

        result = ReqularExpression.checkName("가가가");
        System.out.println("result = " + result);

        result = ReqularExpression.checkName("aaaA가");
        System.out.println("result = " + result);
    }

    @Test
    @DisplayName("정규표현식 아이디")
    public void test2(){
        boolean result = ReqularExpression.checkUsername("aaaaaaa");
        System.out.println("result = " + result);

        result = ReqularExpression.checkUsername("1111111");
        System.out.println("result = " + result);

        result = ReqularExpression.checkUsername("aaa111");
        System.out.println("result = " + result);

        result = ReqularExpression.checkUsername("111aaa");
        System.out.println("result = " + result);
    }

    @Test
    @DisplayName("정규표현식 비번")
    public void test3(){
        boolean result = ReqularExpression.checkPw("aaaaaa");
        System.out.println("result = " + result);

        result = ReqularExpression.checkPw("1111111");
        System.out.println("result = " + result);

        result = ReqularExpression.checkPw("aaa111");
        System.out.println("result = " + result);

        result = ReqularExpression.checkPw("111aaa");
        System.out.println("result = " + result);

        result = ReqularExpression.checkPw("aa11@@%#");
        System.out.println("result = " + result);
    }

}