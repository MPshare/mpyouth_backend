package kr.go.mapo.mpyouth.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequest {
    @NotBlank
    @Schema(description = "아이디",  nullable = false)
    private String adminLogId;

    @NotBlank

    @Schema(description = "비밀번호",  nullable = false)
    private String password;

}
