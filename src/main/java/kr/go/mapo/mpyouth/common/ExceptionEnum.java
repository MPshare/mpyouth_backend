package kr.go.mapo.mpyouth.common;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ExceptionEnum {
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED,"403","권한이 없습니다."),
    BAD_CREDENTIAL_ERROR(HttpStatus.UNAUTHORIZED,"401","비밀번호 불일치입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND,"400    ","존재하지 않는 아이디입니다."),
    ALREADY_REGISTERED_USER(HttpStatus.BAD_REQUEST,"409","이미 존재하는 아이디입니다."),
    ALREADY_REGISTERED_EMAIL(HttpStatus.BAD_REQUEST,"409","이미 존재하는 이메일입니다."),
    NOT_FOUND_USER_WITH_EMAIL(HttpStatus.NOT_FOUND,"400","이메일에 해당하는 아이디가 없습니다."),
    NOT_FOUND_USER_WITH_EMAIL_OR_ADMIN_LOGIN_ID(HttpStatus.NOT_FOUND,"400","입력하신 정보가 회원정보와 일치하지 않습니다."),
    BAD_AUTH_KEY_ERROR(HttpStatus.NOT_FOUND,"400","인증키 값이 일치하지 않습니다. 인증코드 재발급 후 다시 시도해주세요");
    private final HttpStatus status;
    private String code;
    private String message;



    ExceptionEnum(HttpStatus status) {
        this.status = status;
    }


    ExceptionEnum(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    ExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}