package kr.go.mapo.mpyouth.common;


import kr.go.mapo.mpyouth.api.ApiStatus;
import kr.go.mapo.mpyouth.exception.NotFoundDonationException;
import kr.go.mapo.mpyouth.exception.NotFoundProgramException;
import kr.go.mapo.mpyouth.payload.response.CustomApiResponse;
import kr.go.mapo.mpyouth.security.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionAdvice {

    private final JwtUtils jwtUtils;
    private final MessageSource messageSource;


    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final ApiException e) {
        //e.printStackTrace();

        log.error("ApiException");

        return ResponseEntity
                .status(e.getError().getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(e.getError().getCode())
                        .errorMessage(e.getError().getMessage())
                        .build());
    }

    @ExceptionHandler({NotFoundProgramException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, NotFoundProgramException e) {
        e.printStackTrace();
        log.error("message : {}, localizedMessage : {}", e.getMessage(), e.getLocalizedMessage());

        return ResponseEntity
                .status(ExceptionEnum.NOT_FOUND_PROGRAM.getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(ExceptionEnum.NOT_FOUND_PROGRAM.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler({NotFoundDonationException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, NotFoundDonationException e) {
        e.printStackTrace();
        log.error("message : {}, localizedMessage : {}", e.getMessage(), e.getLocalizedMessage());

        return ResponseEntity
                .status(ExceptionEnum.NOT_FOUND_DONATION.getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(ExceptionEnum.NOT_FOUND_DONATION.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, RuntimeException e) {
        e.printStackTrace();
        log.error("message : {}, localizedMessage : {}", e.getMessage(), e.getLocalizedMessage());

        return ResponseEntity
                .status(ExceptionEnum.RUNTIME_EXCEPTION.getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(ExceptionEnum.RUNTIME_EXCEPTION.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final AccessDeniedException e) {
//        e.printStackTrace();

        log.error("AccessDeniedException");
        System.out.println("request:" + request.getHeader("Authorization"));
        System.out.println("request:" + request.getRequestURI());
        return ResponseEntity
                .status(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getCode())
                        .errorMessage(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getMessage())
                        .build());
    }

    /*@ExceptionHandler({Exception.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final Exception e) {
//        e.printStackTrace();

        log.error("Exception");

        return ResponseEntity
                .status(ExceptionEnum.INTERNAL_SERVER_ERROR.getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(ExceptionEnum.INTERNAL_SERVER_ERROR.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }*/

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionEntity> processValidationError(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
        }


        return ResponseEntity
                .status(ExceptionEnum.RUNTIME_EXCEPTION.getStatus())
                .body(ApiExceptionEntity.builder()
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
                        .errorCode(ExceptionEnum.NOT_FOUND_URL.getCode())
                        .errorMessage(ExceptionEnum.NOT_FOUND_URL.getMessage())
                        .build());
    }


    @ExceptionHandler(BindException.class)
    public ResponseEntity<CustomApiResponse<?>> bindExceptionHandling(BindException e, HttpServletRequest request) {

        log.info("error uri : {} {}", request.getMethod(), request.getRequestURI());
        if (e != null) {
            CustomApiResponse<?> response = CustomApiResponse.builder()
                    .success(ApiStatus.FAIL)
                    .message(messageSource.getMessage("typeMismatch.programRequest",
                            new String[]{
                                    Arrays.toString(e.getFieldErrors()
                                            .stream()
                                            .map(fieldError -> fieldError.getField() + fieldError.getDefaultMessage())
                                            .toArray())
                            }
                            , Locale.getDefault())
                    )
                    .build();

            log.error(e.getFieldErrors().stream().map(FieldError::getField).collect(Collectors.toList()).toString());
            log.error(e.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList()).toString());

            log.error("e.getMessage : {}", e.getMessage());
            log.error("e.getLocalizedMessage : {}", e.getLocalizedMessage());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomApiResponse<?>> constraintViolationHandle(ConstraintViolationException e) {
        log.error(e.toString());

        return new ResponseEntity<>(CustomApiResponse.builder()
                .success(ApiStatus.FAIL)
                .message(messageSource.getMessage("constraint.program.validation",
                        new String[]{Arrays.toString(e.getConstraintViolations().stream()
                                .map(constraintViolation -> {
                                    return constraintViolation.getPropertyPath() + " : " + constraintViolation.getMessage();
                                }).toArray())},
                        Locale.getDefault()))
                .build(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<CustomApiResponse<?>> testApiAdvice(SQLIntegrityConstraintViolationException e) {
        CustomApiResponse<?> response = CustomApiResponse.builder()
                .success(ApiStatus.FAIL)
                .message(messageSource.getMessage("constraint.program.og_fk", null, Locale.getDefault()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}