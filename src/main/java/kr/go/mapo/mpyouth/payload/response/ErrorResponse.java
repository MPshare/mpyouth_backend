package kr.go.mapo.mpyouth.payload.response;

import kr.go.mapo.mpyouth.common.ExceptionEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorResponse {
    private HttpStatus status;
    private String errorMessage;
//    private String code;

    public ErrorResponse(ExceptionEnum exceptionEnum){
        this.status = exceptionEnum.getStatus();
        this.errorMessage = exceptionEnum.getMessage();
//        this.code = errorCode.getErrorCode();
    }
}