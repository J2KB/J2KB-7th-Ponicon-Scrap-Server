package j2kb.ponicon.scrap.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionResponseAdvice {

    /**
     * BaseException 예외처리 핸들러
     * @param e BaseException
     * @return BaseResponse
     */
    @ExceptionHandler(BaseException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST) //응답코드: 400 에러로 일단 통일
    public BaseResponse handlerBaseException(BaseException e){
        return new BaseResponse(e.getStatus());
    }

    /**
     * Exception 예외처리 핸들러
     * @param e Exception
     * @return BaseResponse - 서버 내부에서 에러 발생
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public BaseResponse handlerException(Exception e){
        e.printStackTrace();
        return new BaseResponse(BaseExceptionStatus.SERVER_INTENER_ERROR);
    }
}
