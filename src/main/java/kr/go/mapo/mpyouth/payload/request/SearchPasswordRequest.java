package kr.go.mapo.mpyouth.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SearchPasswordRequest {


    @Schema(description = "아이디")
    private String adminLoginId;

    @Schema(description = "이메일")
    private String email;
}
