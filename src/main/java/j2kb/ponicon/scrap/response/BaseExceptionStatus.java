package j2kb.ponicon.scrap.response;

import lombok.Getter;

@Getter
public enum BaseExceptionStatus {

    TEST_ERROR(40401, "실패했어요"),
    SERVER_INTENER_ERROR(40001, "서버 내부적인 에러"),

    FAIL_ENCRYPT_PASSWORD(40000, "비밀번호 암호화에 실패했습니다"),
    DUPULICATE_USERNAME(4000, "아이디가 중복됩니다"),
    LOGIN_USER_NOT_EXIST(33, "해당하는 아이디 또는 비밀번호가 없습니다"),
    KAKAO_GET_TOKEN_FAIL(33, "예상치 못한 이유로 카카오 토큰 받기에 실패했습니다"),
    KAKAO_GET_USER_INFO_FAIL(33, "예상치 못한 이유로 카카오 사용자 정보 가져오기에 실패했습니다"),
    KAKAO_LOGIN_FAIL(33,"예상치 못한 이유로 카카오 로그인에 실패했습니다"),
    JOIN_USERNAME_EMPTY(33, "아이디가 없습니다"),
    JOIN_PASSWORD_EMPTY(33, "비밀번호가 없습니다"),
    JOIN_NAME_EMPTY(33, "이름이 없습니다"),
    LOGIN_AUTOLOGIN_EMPTY(33,"자동 로그인값이 체크되지 않았습니다"),
    JOIN_USERNAME_INVALID(33, "아이디 형식이 일치하지 않습니다"),
    JOIN_PASSWORD_INVALID(33,"비밀번호 형식이 일치하지 않습니다"),
    JOIN_NAME_INVALID(33,"이름의 형식이 일치하지 않습니다")
    ;

    private final int code;
    private final String message;

    private BaseExceptionStatus(int code, String msg){
        this.code = code;
        this.message = msg;
    }
}
