package kr.go.mapo.mpyouth.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import kr.go.mapo.mpyouth.payload.request.*;
import kr.go.mapo.mpyouth.payload.response.*;
import kr.go.mapo.mpyouth.service.AuthService;
import kr.go.mapo.mpyouth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;


@RestControllerAdvice
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;



//    @Secured("ROLE_ADMIN")
    @Operation(summary = "관리자 등록", description = "관리자를 등록합니다", responses = {
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
            @ApiResponse(responseCode = "409", description = "CONFLICT"),
    })

    @PostMapping("/register")
    public ResponseEntity<CustomApiResponse<ResponseMessage>>   registerUser(@Valid @RequestBody SignupRequest signUpRequest)  {
        authService.signup(signUpRequest);

        CustomApiResponse<ResponseMessage> response = CustomApiResponse.<ResponseMessage>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.CREATED_USER)
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @Operation(summary = "아이디 찾기", description = "이메일 정보로 아이디를 찾습니다.", responses = {
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
            @ApiResponse(responseCode = "409", description = "CONFLICT"),
    })
    @PostMapping("/find/id")
    public ResponseEntity<CustomApiResponse<ResponseMessage>> findId(@Valid @RequestBody SearchIdRequest searchIdRequest) throws MessagingException {

        userService.sendSearchId(searchIdRequest);

        CustomApiResponse<ResponseMessage> response = CustomApiResponse.<ResponseMessage>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.SEND_ADMIN_LOGIN_ID_SUCESS)
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "비밀번호 찾기", description = "adminLoginId와 email를 이용하여 인증키 값을 이메일로 받습니다.", responses = {
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
            @ApiResponse(responseCode = "409", description = "CONFLICT"),
    })
    @PostMapping("/find/password")
    public ResponseEntity<CustomApiResponse<ResponseMessage>> findPassword(@Valid @RequestBody SearchPasswordRequest searchPasswordRequest) throws MessagingException {

        userService.sendAuthKey(searchPasswordRequest);

        CustomApiResponse<ResponseMessage> response = CustomApiResponse.<ResponseMessage>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.SEND_AUTH_KEY_SUCCESS)
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "비밀번호 초기화", description = " adminLoginId, 이메일로 받은 인증키, email를 이용하여 비밀번호를 초기화합니다.", responses = {
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
            @ApiResponse(responseCode = "409", description = "CONFLICT"),
    })
    @PostMapping("/init/password")
    public ResponseEntity<CustomApiResponse<ResponseMessage>> InitPassword(@Valid @RequestBody InitPasswordRequest initPasswordRequest) throws MessagingException {
    
        userService.initPassword(initPasswordRequest);

        CustomApiResponse<ResponseMessage> response = CustomApiResponse.<ResponseMessage>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.SEND_INIT_PASSWORD_SUCESS)
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "비밀번호 변경", description = " 현재 비밀번호, 새 비밀번호, 확인 비밀번호를 이용하여 비밀번호를 변경합니다.", responses = {
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
            @ApiResponse(responseCode = "409", description = "CONFLICT"),
    })

    @Secured({"ROLE_ADMIN","ROLE_MANAGER"})
    @PutMapping("/password")
    public ResponseEntity<CustomApiResponse<ResponseMessage>> changeUserPassword(HttpServletRequest request, @Valid @RequestBody ModifyUserPasswordRequest modifyUserPasswordRequest)  {
        userService.updateUserPassword(request,modifyUserPasswordRequest);

        CustomApiResponse<ResponseMessage> response = CustomApiResponse.<ResponseMessage>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.USER_PASSWROD_UPDATE_SUCESS)
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "관리자 리스트 조회", description = "관리자 리스트를 조회합니다.", responses = {
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
    })

    @Secured({"ROLE_ADMIN"})
    @GetMapping("")
    public ResponseEntity<CustomApiResponse<List<UserInfoResponse>>> getUsers()  {

        List<UserInfoResponse> users = userService.findUsers();
        CustomApiResponse<List<UserInfoResponse>> response = CustomApiResponse.<List<UserInfoResponse>>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.USERS_SELECT_SUCCESS)
                .data(users)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "관리자 조회", description = "관리자를 조회합니다.", responses = {
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
    })

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/{id}")
     public ResponseEntity<CustomApiResponse<UserInfoResponse>> getUser(
            @PathVariable("id") Long id
    ) {
        UserInfoResponse user  = userService.findOne(id) ;
        CustomApiResponse<UserInfoResponse> response = CustomApiResponse.<UserInfoResponse>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.USER_SELECT_SUCCESS)
                .data(user)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Operation(summary = "내 정보 조회", description = "내 정보를 조회합니다.", responses = {
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
    })

    @Secured({"ROLE_ADMIN","ROLE_MANAGER"})
    @GetMapping("/me")
    public ResponseEntity<CustomApiResponse<UserInfoResponse>> getMyInfo(HttpServletRequest request)  {
        UserInfoResponse user = userService.findMe(request);
        CustomApiResponse<UserInfoResponse> response = CustomApiResponse.<UserInfoResponse>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.USER_SELECT_SUCCESS)
                .data(user)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "내 정보 변경", description = "내 정보를 변경합니다.", responses = {
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
    })

    @Secured({"ROLE_ADMIN","ROLE_MANAGER"})
    @PutMapping("/me")
    public ResponseEntity<CustomApiResponse<ResponseMessage>> changeMyInfo(HttpServletRequest request, @Valid @RequestBody ModifyUserInfoRequest modifyUserInfoRequest)  {
        userService.updateUser(request,modifyUserInfoRequest);

        CustomApiResponse<ResponseMessage> response = CustomApiResponse.<ResponseMessage>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.USER_INFO_UPDATE_SUCESS)
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "관리자 삭제", description = "관리자를 삭제합니다.", responses = {
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
    })
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<UserInfoResponse>> deleteUser(
            @PathVariable("id") Long id
    )  {

        userService.deleteUser(id);

        CustomApiResponse<UserInfoResponse> response = CustomApiResponse.<UserInfoResponse>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.USER_DELETE_SUCCESS)
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @Operation(summary = "관리자 정보 변경", description = "관리자 정보를 변경합니다.", responses = {
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
    })
    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<CustomApiResponse<ResponseMessage>> changeUserInfo(HttpServletRequest request, @Valid @RequestBody ModifyUserInfoRequest modifyUserInfoRequest)  {
        userService.updateUser(request,modifyUserInfoRequest);

        CustomApiResponse<ResponseMessage> response = CustomApiResponse.<ResponseMessage>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.USER_INFO_UPDATE_SUCESS)
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @Operation(summary = "로그아웃", description = "로그아웃합니다.", responses = {
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
    })
    @GetMapping("/logout")
    public ResponseEntity<CustomApiResponse<ResponseMessage>> logout(HttpServletRequest request) {
        authService.logout(request);

        CustomApiResponse<ResponseMessage> response = CustomApiResponse.<ResponseMessage>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.LOGOUT_SUCCESS)
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}
