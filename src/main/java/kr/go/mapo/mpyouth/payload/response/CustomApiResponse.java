package kr.go.mapo.mpyouth.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.go.mapo.mpyouth.api.ApiStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomApiResponse<T> {
    private ApiStatus success;
    private String message;
    private T data;
}
