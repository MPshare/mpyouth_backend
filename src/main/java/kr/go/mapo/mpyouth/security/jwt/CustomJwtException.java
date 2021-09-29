package kr.go.mapo.mpyouth.security.jwt;

import kr.go.mapo.mpyouth.common.ExceptionEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.rmi.AccessException;

@Getter
public class CustomJwtException extends AccessException {
    private ExceptionEnum exceptionEnum;
    public CustomJwtException(ExceptionEnum exceptionEnum){
        super(exceptionEnum.getMessage());
        this.exceptionEnum = exceptionEnum;
    }
}
