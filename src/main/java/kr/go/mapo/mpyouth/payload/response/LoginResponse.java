package kr.go.mapo.mpyouth.payload.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class LoginResponse {
    @Schema(description = "액세스토큰")
    private String accessToken;
    @Schema(description = "리프레쉬토큰")
    private String refreshToken;
    @Schema(description = "이름")
    private String username;
    @Schema(description = "이메일")
    private String email;
    @Schema(description = "권한", example ="[admin],[manager],[admin,manager]")
    private List<String> roles;

    public LoginResponse(String accessToken,String refreshToken, String username, String email, List<String> roles) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }


}
