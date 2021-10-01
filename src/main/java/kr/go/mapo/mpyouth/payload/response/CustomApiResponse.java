package kr.go.mapo.mpyouth.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.go.mapo.mpyouth.api.ApiStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class CustomApiResponse<T> {
    private ApiStatus status;
    private String message;
    private T data;
}
