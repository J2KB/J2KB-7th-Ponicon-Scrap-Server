package j2kb.ponicon.scrap.response;

import lombok.Getter;

@Getter
public enum BaseExceptionStatus {

    TEST_ERROR(40401, "실패했어요"),
    SERVER_INTENER_ERROR(2002, "서버 내부적인 에러"),

    // 유저 관련은 3000번 에러
    DUPULICATE_USERNAME(3001, "아이디가 중복됩니다"),
    FAIL_ENCRYPT_PASSWORD(3002, "비밀번호 암호화에 실패했습니다"),
    LOGIN_USER_NOT_EXIST(3003, "해당하는 아이디 또는 비밀번호가 없습니다"),
    NOT_LOGIN_USER(3004,"로그인 하지 않은 유저입니다"),
    USER_NOT_EXIST(3005,"해당하는 유저가 존재하지 않습니다"),

    KAKAO_GET_TOKEN_FAIL(3101, "예상치 못한 이유로 카카오 토큰 받기에 실패했습니다"),
    KAKAO_GET_USER_INFO_FAIL(3102, "예상치 못한 이유로 카카오 사용자 정보 가져오기에 실패했습니다"),
    KAKAO_LOGIN_FAIL(3103,"예상치 못한 이유로 카카오 로그인에 실패했습니다"),

    CATEGORY_NAME_NULL(4444, "카테고리 이름을 입력해주세요"),
    CATEGORY_NAME_LENGTH(4444, "카테고리 이름이 2~60 글자 사이"),
    DATA_NAME_INCORRECTION(44444, "링크가 잘못되었습니다."),
    MYPAGE_USER_NOT_FOUND(44444, "해당 사용자를 찾을 수 없습니다."),

    // 쿠키, 토큰, 인증 관련은 5000번 에러
    ACCESS_COOKIE_EMPTY(5001, "어세스 쿠키가 없습니다"),
    JWT_TOKEN_EXPIRE(5002, "JWT 토큰 만료되었습니다"),
    JWT_TOKEN_INVALID(5003,"잘못된 JWT 토큰입니다"),
    UNAUTHORIZED_USER_ACCESS(5004, "인증되지 않은 유저의 접근입니다")
    ;

    private final int code;
    private final String message;

    private BaseExceptionStatus(int code, String msg){
        this.code = code;
        this.message = msg;
    }
}
