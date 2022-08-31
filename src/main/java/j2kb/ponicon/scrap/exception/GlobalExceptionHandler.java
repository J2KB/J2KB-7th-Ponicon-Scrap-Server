package j2kb.ponicon.scrap.exception;

import j2kb.ponicon.scrap.data.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.UnknownHostException;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    private ResponseDto<String> handleArgumentException(Exception e) {
        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        // INTERNAL_SERVER_ERROR = 500 값임
    }
//    @ExceptionHandler(value = MalformedURLException.class)
//    private ResponseDto<String> malformedURLException(MalformedURLException e) {
//        return new ResponseDto<String>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
//        // INTERNAL_SERVER_ERROR = 500 값임
//    }
//
//    @ExceptionHandler(value = NullPointerException.class)
//    private ResponseDto<String> nullPointerException(NullPointerException e) {
//        return new ResponseDto<String>(HttpStatus.NOT_FOUND.value(), e.getMessage());
//        // INTERNAL_SERVER_ERROR = 500 값임
//    }
//    @ExceptionHandler(value = UnknownHostException.class)
//    private ResponseDto<String> unknownHostException(UnknownHostException e) {
//        return new ResponseDto<String>(HttpStatus.NOT_FOUND.value(), e.getMessage());
//        // INTERNAL_SERVER_ERROR = 500 값임
//    }
}