package kr.go.mapo.mpyouth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "프로그램이 없습니다.")
public class NotFoundProgramException extends RuntimeException {
}
