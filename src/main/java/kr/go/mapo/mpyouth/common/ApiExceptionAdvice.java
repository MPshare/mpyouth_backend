package kr.go.mapo.mpyouth.common;


import kr.go.mapo.mpyouth.api.ApiStatus;
import kr.go.mapo.mpyouth.payload.response.CustomApiResponse;
import kr.go.mapo.mpyouth.security.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionAdvice {

    private final JwtUtils jwtUtils;


    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final ApiException e) {
        //e.printStackTrace();

        log.error("ApiException");

        return ResponseEntity
                .status(e.getError().getStatus())
                .body(ApiExceptionEntity.builder()
                        .success(ApiStatus.FAIL)
                        .errorCode(e.getError().getCode())
                        .errorMessage(e.getError().getMessage())
                        .build());
    }


    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, RuntimeException e) {
        e.printStackTrace();
        log.error("message : {}, localizedMessage : {}", e.getMessage(), e.getLocalizedMessage());

        return ResponseEntity
                .status(ExceptionEnum.RUNTIME_EXCEPTION.getStatus())
                .body(ApiExceptionEntity.builder()
                        .success(ApiStatus.FAIL)
                        .errorCode(ExceptionEnum.RUNTIME_EXCEPTION.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(
            HttpServletRequest request, final AccessDeniedException e
    ) {

        log.error("AccessDeniedException");
        return ResponseEntity
                .status(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getStatus())
                .body(ApiExceptionEntity.builder()
                        .success(ApiStatus.FAIL)
                        .errorCode(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getCode())
                        .errorMessage(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getMessage())
                        .build());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final Exception e) {

        log.error("Exception");

        return ResponseEntity
                .status(ExceptionEnum.INTERNAL_SERVER_ERROR.getStatus())
                .body(ApiExceptionEntity.builder()
                        .success(ApiStatus.FAIL)
                        .errorCode(ExceptionEnum.INTERNAL_SERVER_ERROR.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionEntity> processValidationError(MethodArgumentNotValidException exception) {
        log.error("MethodArgumentNotValidException");

        BindingResult bindingResult = exception.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
            builder.append(". ");
        }


        return ResponseEntity
                .status(ExceptionEnum.RUNTIME_EXCEPTION.getStatus())
                .body(ApiExceptionEntity.builder()
                        .success(ApiStatus.FAIL)
                        .errorCode(HttpStatus.BAD_REQUEST.toString())
                        .errorMessage(builder.toString())
                        .build());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request) {
        log.error("NoHandlerFoundException");

        return ResponseEntity
                .status(ExceptionEnum.NOT_FOUND_URL.getStatus())
                .body(ApiExceptionEntity.builder()
                        .success(ApiStatus.FAIL)
                        .errorCode(ExceptionEnum.NOT_FOUND_URL.getCode())
                        .errorMessage(ExceptionEnum.NOT_FOUND_URL.getMessage())
                        .build());
    }


    @ExceptionHandler(BindException.class)
    public ResponseEntity<CustomApiResponse<?>> bindExceptionHandling(BindException e, HttpServletRequest request) {
        log.error("BindException");

        if (e == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        CustomApiResponse<?> response = CustomApiResponse.builder()
                .success(ApiStatus.FAIL)
                .message(
                        ExceptionEnum.BIND_ERROR.getMessage() + " : " +
                        Arrays.toString(e.getFieldErrors()
                                .stream()
                                .map(fieldError -> fieldError.getField() + fieldError.getDefaultMessage())
                                .toArray())
                )
                .build();

        return new ResponseEntity<>(response, ExceptionEnum.BIND_ERROR.getStatus());

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomApiResponse<?>> constraintViolationHandle(ConstraintViolationException e) {
        log.error("ConstraintViolationException");

        return new ResponseEntity<>(CustomApiResponse.builder()
                .success(ApiStatus.FAIL)
                .message(ExceptionEnum.CONSTRAINT_VIOLATION.getMessage() + " : " +
                        Arrays.toString(e.getConstraintViolations().stream()
                                .map(constraintViolation
                                        -> "[" + constraintViolation.getPropertyPath() + "](은)는 "
                                        + constraintViolation.getMessage() + " ").toArray())
                )
                .build(), ExceptionEnum.CONSTRAINT_VIOLATION.getStatus());
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<CustomApiResponse<?>> testApiAdvice(SQLIntegrityConstraintViolationException e) {
        log.error("SQLIntegrityConstraintViolationException");
        CustomApiResponse<?> response = CustomApiResponse.builder()
                .success(ApiStatus.FAIL)
                .message(ExceptionEnum.SQL_INTEGRITY_CONSTRAINT_VIOLATION.getMessage())
                .build();
        return new ResponseEntity<>(response, ExceptionEnum.SQL_INTEGRITY_CONSTRAINT_VIOLATION.getStatus());
    }

    @ExceptionHandler({NoResultException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, NoResultException e) {
        log.error("NoResultException");

        return ResponseEntity
                .status(ExceptionEnum.NOT_FOUND_ENTITY.getStatus())
                .body(ApiExceptionEntity.builder()
                        .success(ApiStatus.FAIL)
                        .errorCode(ExceptionEnum.NOT_FOUND_ENTITY.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }

}