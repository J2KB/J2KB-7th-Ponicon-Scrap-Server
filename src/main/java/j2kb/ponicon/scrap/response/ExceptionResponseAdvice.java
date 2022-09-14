package j2kb.ponicon.scrap.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

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
        return new BaseResponse(BaseExceptionStatus.SERVER_INTENER_ERROR);
    }

    /**
     * MalformedURLException 예외처리 핸들러
     * @param e Exception
     * @return BaseResponse - 링크가 없습니다.
     */
    @ExceptionHandler(MalformedURLException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public BaseResponse malformedURLException(Exception e){
        return new BaseResponse(BaseExceptionStatus.DATA_NAME_NOT_EXIST);
    }
    /**
     * UnknownHostException 예외처리 핸들러
     * @param e Exception
     * @return BaseResponse - 링크가 잘못되었습니다.
     */
    @ExceptionHandler(UnknownHostException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public BaseResponse unknownHostException(Exception e){
        return new BaseResponse(BaseExceptionStatus.DATA_NAME_INCORRECTION);
    }
    /**
     * UnknownHostException 예외처리 핸들러
     * @param e Exception
     * @return BaseResponse - 카테고리 이름이 2~60 글자 사이
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public BaseResponse constraintViolationException(Exception e){
        return new BaseResponse(BaseExceptionStatus.CATEGORY_NAME_LENGTH);
    }
}
