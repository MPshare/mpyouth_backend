package kr.go.mapo.mpyouth.common;

import kr.go.mapo.mpyouth.api.ApiStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ApiExceptionEntity {
    private ApiStatus success;
    private String errorCode;
    private String errorMessage;

    @Builder
    public ApiExceptionEntity(ApiStatus success, String errorCode, String errorMessage){
        this.success = success;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}