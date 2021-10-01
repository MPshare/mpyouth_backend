package kr.go.mapo.mpyouth.global;

import kr.go.mapo.mpyouth.payload.CustomApiResponse;
import kr.go.mapo.mpyouth.payload.ApiStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
    private final MessageSource messageSource;

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
