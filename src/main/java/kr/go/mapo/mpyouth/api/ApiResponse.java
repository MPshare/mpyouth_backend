package kr.go.mapo.mpyouth.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
    private ApiStatus status;
    private String message;
    private T data;
}
