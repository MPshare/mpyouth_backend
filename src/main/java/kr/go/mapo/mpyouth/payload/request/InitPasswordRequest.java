package kr.go.mapo.mpyouth.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class InitPasswordRequest {

    private String adminLoginId;
    private String email;
    private String authKey;

}
