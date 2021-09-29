package kr.go.mapo.mpyouth.api;


import kr.go.mapo.mpyouth.payload.request.LoginRequest;
import kr.go.mapo.mpyouth.payload.request.TokenRequest;
import kr.go.mapo.mpyouth.payload.response.*;
import kr.go.mapo.mpyouth.security.jwt.CustomJwtException;
import kr.go.mapo.mpyouth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@ControllerAdvice
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<ApiResponse<LoginResponse>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse res=authService.login(loginRequest);

        ApiResponse<LoginResponse> response = ApiResponse.<LoginResponse>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.LOGIN_SUCCESS)
                .data(res)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/token")
    public ResponseEntity<ApiResponse<TokenResponse>> reissueAccessToken(@RequestBody TokenRequest tokenRequest) throws CustomJwtException {

        TokenResponse res =authService.reIssueToken(tokenRequest);
        ApiResponse<TokenResponse> response = ApiResponse.<TokenResponse>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.ISSUE_NEW_ACCESS_TOKEN_AND_REFRESH_TOKEN)
                .data(res)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
