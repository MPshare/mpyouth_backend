package kr.go.mapo.mpyouth.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequest {
    @NotBlank
    private String adminLogId;

    @NotBlank
    private String password;

}
