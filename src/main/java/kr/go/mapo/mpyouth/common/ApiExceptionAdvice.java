package kr.go.mapo.mpyouth.common;


import kr.go.mapo.mpyouth.api.ApiStatus;
import kr.go.mapo.mpyouth.payload.response.ErrorResponse;
import kr.go.mapo.mpyouth.security.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionAdvice {

    private final JwtUtils jwtUtils;

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final ApiException e) {
        //e.printStackTrace();

        return ResponseEntity
                .status(e.getError().getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(e.getError().getCode())
                        .errorMessage(e.getError().getMessage())
                        .build());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final RuntimeException e) {
        e.printStackTrace();

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
        System.out.println("request:"+request.getHeader("Authorization"));
        System.out.println("request:"+request.getRequestURI());
        return ResponseEntity
                .status(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getCode())
                        .errorMessage(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getMessage())
                        .build());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final Exception e) {
//        e.printStackTrace();
        return ResponseEntity
                .status(ExceptionEnum.INTERNAL_SERVER_ERROR.getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(ExceptionEnum.INTERNAL_SERVER_ERROR.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<ApiExceptionEntity>  processValidationError(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
//            builder.append(" 입력된 값: [");
//            builder.append(fieldError.getRejectedValue());
//            builder.append("]");
        }



        return ResponseEntity
                .status(ExceptionEnum.RUNTIME_EXCEPTION.getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(HttpStatus.BAD_REQUEST.toString())
                        .errorMessage(builder.toString())
                        .build());
//        return builder.toString();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request) {

        return ResponseEntity
                .status(ExceptionEnum.NOT_FOUND_URL.getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(ExceptionEnum.NOT_FOUND_URL.getCode())
                        .errorMessage(ExceptionEnum.NOT_FOUND_URL.getMessage())
                        .build());
    }


//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public  ResponseEntity<ApiExceptionEntity>  validException(MethodArgumentNotValidException ex) {
//
//
//        return ResponseEntity
//                .status(ExceptionEnum.NOT_FOUND_URL.getStatus())
//                .body(ApiExceptionEntity.builder()
//                        .errorCode(ExceptionEnum.NOT_FOUND_URL.getCode())
//                        .errorMessage(ExceptionEnum.NOT_FOUND_URL.getMessage())
//                        .build());
//        }
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponse> methodValidException(MethodArgumentNotValidException e, HttpServletRequest request){
//        ErrorResponse errorResponse = makeErrorResponse(e.getBindingResult());
//        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
//
//    private ErrorResponse makeErrorResponse(BindingResult bindingResult){
//        String code = "";
//        String description = "";
//        String detail = "";
//
//        //에러가 있다면
//        if(bindingResult.hasErrors()){
//            //DTO에 설정한 meaasge값을 가져온다
//            detail = bindingResult.getFieldError().getDefaultMessage();
//
//            //DTO에 유효성체크를 걸어놓은 어노테이션명을 가져온다.
//            String bindResultCode = bindingResult.getFieldError().getCode();
//
//            System.out.println(bindResultCode);
////            switch (bindResultCode){
////                case "NotNull":
////                    code = ErrorCode.NOT_NULL.getCode();
////                    description = ErrorCode.NOT_NULL.getDescription();
////                    break;
////                case "Min":
////                    code = ErrorCode.MIN_VALUE.getCode();
////                    description = ErrorCode.MIN_VALUE.getDescription();
////                    break;
////            }
//        }
//
//        return new ErrorResponse(ExceptionEnum.RUNTIME_EXCEPTION);
//    }
}