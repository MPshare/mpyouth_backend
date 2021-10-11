package kr.go.mapo.mpyouth.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class TokenResponse {
    @Schema(description = "액세스토큰")
    private String accessToken;
    @Schema(description = "리프레쉬토큰")
    private String refreshToken;

    public TokenResponse(String accessToken,String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
