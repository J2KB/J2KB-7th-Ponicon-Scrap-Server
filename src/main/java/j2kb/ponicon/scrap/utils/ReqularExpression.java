package j2kb.ponicon.scrap.utils;

import java.util.regex.Pattern;

public class ReqularExpression {

    public static boolean checkName(String name){

        String pattern = "^(?=.*[a-zA-Z가-힣])[A-Za-z가-힣]{1,30}$";

        if(Pattern.matches(pattern, name)){
            return true;
        }
        return false;
    }

    public static boolean checkUsername(String username){

        String pattern = "^(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{5,15}$";

        if(Pattern.matches(pattern, username)){
            return true;
        }
        return false;
    }

    public static boolean checkPw(String pw){

        String pattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!?@#$%&*]{5,15}$";

        if(Pattern.matches(pattern, pw)){
            return true;
        }
        return false;
    }
}
