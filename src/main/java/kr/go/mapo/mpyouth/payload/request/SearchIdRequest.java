package kr.go.mapo.mpyouth.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class SearchIdRequest {
    @NotBlank
    @Schema(description = "이메일")
    private String email;
}
