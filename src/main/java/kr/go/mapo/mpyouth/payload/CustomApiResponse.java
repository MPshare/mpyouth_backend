package kr.go.mapo.mpyouth.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
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
