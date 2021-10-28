package kr.go.mapo.mpyouth.common;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ExceptionEnum {
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "400"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    ACCESS_DENIED_EXCEPTION(HttpStatus.FORBIDDEN, "403", "권한이 없습니다."),
    BAD_CREDENTIAL_ERROR(HttpStatus.UNAUTHORIZED, "401", "비밀번호 불일치입니다."),
    CURRENT_BAD_CREDENTIAL_ERROR(HttpStatus.UNAUTHORIZED, "401", "현재 비밀번호가 일치하지 않습니다."),
    NOT_FOUND_USER(HttpStatus.UNAUTHORIZED, "401", "존재하지 않는 아이디입니다."),
    ALREADY_REGISTERED_USER(HttpStatus.CONFLICT, "409", "이미 존재하는 아이디입니다."),
    ALREADY_REGISTERED_EMAIL(HttpStatus.CONFLICT, "409", "이미 존재하는 이메일입니다."),
    NOT_FOUND_USER_WITH_EMAIL(HttpStatus.UNAUTHORIZED, "401", "이메일에 해당하는 아이디가 없습니다."),
    NOT_FOUND_USER_WITH_EMAIL_OR_ADMIN_LOGIN_ID(HttpStatus.UNAUTHORIZED, "401", "입력하신 정보가 회원정보와 일치하지 않습니다."),
    BAD_AUTH_KEY_ERROR(HttpStatus.UNAUTHORIZED, "401", "인증키 값이 일치하지 않습니다. 인증코드 재발급 후 다시 시도해주세요"),
    NOT_FOUND_ORGANIZATION_WITH_ORGANIZATION_ID(HttpStatus.NOT_FOUND, "400", "해당되는 기관이 존재하지 않습니다."),
    BEFORE_CREDENTIAL_AND_AFTER_CREDENTIAL_SAME_ERROR(HttpStatus.BAD_REQUEST, "400", "현재 비밀번호와 변경하려하는 비밀번호가 같습니다."),
    NEW_CREDENTIAL_AND_CHECK_NEW_CREDENTIAL_SAME_ERROR(HttpStatus.BAD_REQUEST, "400", "새 비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "401", "액세스 토큰이 만료되었습니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "401", "리프레쉬 토큰이 만료되었습니다."),
    INVALID_JWT_SIGNATURE(HttpStatus.UNAUTHORIZED, "401", "유효하지 않은 토큰 SIGNATURE입니다."),
    INVALID_JWT(HttpStatus.UNAUTHORIZED, "401", "유효하지 않은 토큰입니다."),
    NULL_JWT(HttpStatus.BAD_REQUEST, "400", "토큰 값이 널입니다."),
    NULL_EMAIL(HttpStatus.BAD_REQUEST, "400", "이메일 값이 널입니다."),
    UN_SUPPORTED_JWT(HttpStatus.UNAUTHORIZED, "401", "지원되지 않는 토큰입니다."),
    LOGOUT_USER(HttpStatus.UNAUTHORIZED, "401", "로그아웃 한 유저입니다."),
    NOT_FOUND_URL(HttpStatus.NOT_FOUND, "404", "잘못된 URL요청입니다."),
    NOT_FOUND_ENTITY(HttpStatus.NOT_FOUND, "404", "조건에 맞는 엔티티가 없습니다."),
    BIND_ERROR(HttpStatus.BAD_REQUEST, "400", "타입이 일치하지 않습니다."),
    CONSTRAINT_VIOLATION(HttpStatus.BAD_REQUEST, "400", "제약 조건에 위배되는 값을 입력하였습니다."),
    SQL_INTEGRITY_CONSTRAINT_VIOLATION(HttpStatus.BAD_REQUEST, "400", "SQL 제약 조건을 위배되는 값을 입력하였습니다.");

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