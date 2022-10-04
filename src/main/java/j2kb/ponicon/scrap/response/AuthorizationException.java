package j2kb.ponicon.scrap.response;

import lombok.Getter;

@Getter
public class AuthorizationException extends RuntimeException{

    private final BaseExceptionStatus status;

    public AuthorizationException(BaseExceptionStatus status){
        super(status.getMessage());
        this.status = status;
    }
}
