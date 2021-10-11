package kr.go.mapo.mpyouth.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class InitPasswordRequest {

    @Schema(description = "아이디",  nullable = false)
    private String adminLoginId;
    @Schema(description = "이메일",  nullable = false)
    private String email;
    @Schema(description = "인증키",  nullable = false)
    private String authKey;

}
