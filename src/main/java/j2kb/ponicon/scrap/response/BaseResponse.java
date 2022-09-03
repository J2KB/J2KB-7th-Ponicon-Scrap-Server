package j2kb.ponicon.scrap.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 이 3개의 어노테이션이 필요한지 알아볼 필요가 있음.
@Getter
@AllArgsConstructor
@JsonPropertyOrder({"code", "message", "result"})
public class BaseResponse<T> {

    private int code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 기본 생성자 막아둠
    private BaseResponse(){}

    public BaseResponse(T result){
        this.code = 20000;
        this.message = "요청에 성공했습니다";
        this.result = result;
    }

    public BaseResponse(String msg){
        this.code = 20000;
        this.message = msg;
    }

    public BaseResponse(BaseExceptionStatus e){
        this.code = e.getCode();
        this.message = e.getMessage();
    }
}
