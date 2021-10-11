package kr.go.mapo.mpyouth.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.go.mapo.mpyouth.api.ApiStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "응답 폼")
public class CustomApiResponse<T> {
    @Schema(description = "API 상태", allowableValues = {"SUCCESS", "FAIL"})
    private ApiStatus success;
    @Schema(description = "API에 관련된 메시지")
    private String message;
    @Schema(description = "데이터 내용")
    private T data;
}
