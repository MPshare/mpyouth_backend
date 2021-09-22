package kr.go.mapo.mpyouth.api;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ApiResponse<T> {
    private HttpStatus status;
    private String message;
    private T data;
}
