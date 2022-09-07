package j2kb.ponicon.scrap.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@Getter
public class BaseException extends RuntimeException{

    private final BaseExceptionStatus status;

    public BaseException(BaseExceptionStatus status){
        super(status.getMessage());
        this.status = status;
    }
}
