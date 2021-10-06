package kr.go.mapo.mpyouth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "프로그램이 없습니다.")
public class NotFoundProgramException extends RuntimeException {
    public NotFoundProgramException() {
        super();
    }

    public NotFoundProgramException(String message) {
        super(message);
    }

    public NotFoundProgramException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundProgramException(Throwable cause) {
        super(cause);
    }

    protected NotFoundProgramException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
