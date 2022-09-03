package j2kb.ponicon.scrap.response;

import lombok.Getter;

@Getter
public enum BaseExceptionStatus {

    TEST_ERROR(40401, "실패했어요"),
    SERVER_INTENER_ERROR(40001, "서버 내부적인 에러");

    private final int code;
    private final String message;

    private BaseExceptionStatus(int code, String msg){
        this.code = code;
        this.message = msg;
    }
}
