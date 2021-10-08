package kr.go.mapo.mpyouth.api;


//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import kr.go.mapo.mpyouth.payload.request.LoginRequest;
import kr.go.mapo.mpyouth.payload.request.TokenRequest;
import kr.go.mapo.mpyouth.payload.response.*;
import kr.go.mapo.mpyouth.security.jwt.CustomJwtException;
import kr.go.mapo.mpyouth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@ControllerAdvice
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor


public class AuthController {

    private final AuthService authService;

//    @Operation(summary = "로그인", description = "accessToken을 이용하여 로그인합니다.", responses = {
//            @ApiResponse(responseCode = "201", description = "로그인 성공", content = @Content(mediaType = "application/json" , schema = @Schema(implementation = LoginResponse.class))),
//            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content(mediaType = "application/json" , schema = @Schema(implementation = ErrorResponse.class))),
//            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
//
//    })

    @Operation(summary = "로그인",
            responses = {
                    @ApiResponse(
                            responseCode = "401", description = "UNAUTHORIZED"
                            , content = @Content(schema = @Schema(implementation = ErrorResponse.class))) ,
                    @ApiResponse(
                            responseCode = "404", description = "NOT_FOUND"
                            , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))

                    }
    )

    @io.swagger.annotations.ApiResponses({
            @io.swagger.annotations.ApiResponse(
                    response = ErrorResponse.class, message = "UNAUTHORIZED", code = 401),
            @io.swagger.annotations.ApiResponse(
                    response = ErrorResponse.class, message = "NOT_FOUND", code = 404),
    }
    )

    @PostMapping("/signin")
//    public ResponseEntity<CustomApiResponse<? extends BasicResponse>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        public ResponseEntity <CustomApiResponse<LoginResponse>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        LoginResponse res=authService.login(loginRequest);

        CustomApiResponse<LoginResponse> response = CustomApiResponse.<LoginResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message(ResponseMessage.LOGIN_SUCCESS)
                .data(res)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/token")
    @Operation(summary = "토큰 재발급", description = "refreshToken을 이용하여 accessToken,refreshToken을 재발급받습니다.", responses = {
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
    })


    public ResponseEntity<CustomApiResponse<TokenResponse>> reissueAccessToken(@RequestBody TokenRequest tokenRequest) throws CustomJwtException {

        TokenResponse res =authService.reIssueToken(tokenRequest);
        CustomApiResponse<TokenResponse> response = CustomApiResponse.<TokenResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message(ResponseMessage.ISSUE_NEW_ACCESS_TOKEN_AND_REFRESH_TOKEN)
                .data(res)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
