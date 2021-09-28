package kr.go.mapo.mpyouth.api;

import kr.go.mapo.mpyouth.payload.request.*;
import kr.go.mapo.mpyouth.payload.response.MessageResponse;
import kr.go.mapo.mpyouth.payload.response.ResponseMessage;
import kr.go.mapo.mpyouth.payload.response.UserInfoResponse;
import kr.go.mapo.mpyouth.service.AuthService;
import kr.go.mapo.mpyouth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;



//    @PreAuthorize("hasRole('ROLE_ADMIN')") admin 계정 생성후 주석 풀기
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return ResponseEntity.ok(new MessageResponse(authService.signup(signUpRequest)));
    }

    @PostMapping("/find/id")
    public ResponseEntity<ApiResponse<ResponseMessage>> findId(@Valid @RequestBody SearchIdRequest searchIdRequest) throws MessagingException {

        userService.sendSearchId(searchIdRequest);

        ApiResponse<ResponseMessage> response = ApiResponse.<ResponseMessage>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.SEND_ADMIN_LOGIN_ID_SUCESS)
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/find/password")
    public ResponseEntity<ApiResponse<ResponseMessage>> findPassword(@Valid @RequestBody SearchPasswordRequest searchPasswordRequest) throws MessagingException {

        userService.sendAuthKey(searchPasswordRequest);

        ApiResponse<ResponseMessage> response = ApiResponse.<ResponseMessage>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.SEND_AUTH_KEY_SUCCESS)
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/init/password")
    public ResponseEntity<ApiResponse<ResponseMessage>> InitPassword(@Valid @RequestBody InitPasswordRequest initPasswordRequest) throws MessagingException {
    
        userService.initPassword(initPasswordRequest);

        ApiResponse<ResponseMessage> response = ApiResponse.<ResponseMessage>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.SEND_INIT_PASSWORD_SUCESS)
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<ApiResponse<ResponseMessage>> changeyeInfo(HttpServletRequest request, @Valid @RequestBody ModifyUserInfoRequest modifyUserInfoRequest)  {
        userService.updateUser(request,modifyUserInfoRequest);

        ApiResponse<ResponseMessage> response = ApiResponse.<ResponseMessage>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.USER_INFO_UPDATE_SUCESS)
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/password")
    public ResponseEntity<ApiResponse<ResponseMessage>> changeUserPassword(HttpServletRequest request, @Valid @RequestBody ModifyUserPasswordRequest modifyUserPasswordRequest)  {
        userService.updateUserPassword(request,modifyUserPasswordRequest);

        ApiResponse<ResponseMessage> response = ApiResponse.<ResponseMessage>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.USER_PASSWROD_UPDATE_SUCESS)
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<UserInfoResponse>>> getUsers()  {

        List<UserInfoResponse> users = userService.findUsers();
        ApiResponse<List<UserInfoResponse>> response = ApiResponse.<List<UserInfoResponse>>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.USER_PASSWROD_UPDATE_SUCESS)
                .data(users)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
     public ResponseEntity<ApiResponse<UserInfoResponse>> getUser(
            @PathVariable("id") Long id
    ) {
        UserInfoResponse user  = userService.findOne(id) ;
        ApiResponse<UserInfoResponse> response = ApiResponse.<UserInfoResponse>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.USER_PASSWROD_UPDATE_SUCESS)
                .data(user)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/myInfo")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getMyInfo(HttpServletRequest request)  {

        UserInfoResponse user = userService.findMe(request);
        ApiResponse<UserInfoResponse> response = ApiResponse.<UserInfoResponse>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.USER_PASSWROD_UPDATE_SUCESS)
                .data(user)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<UserInfoResponse>> deleteUser(
            @PathVariable("id") Long id
    )  {

        userService.deleteUser(id);

        ApiResponse<UserInfoResponse> response = ApiResponse.<UserInfoResponse>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.USER_PASSWROD_UPDATE_SUCESS)
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseMessage>> changeUserInfo(HttpServletRequest request, @Valid @RequestBody ModifyUserInfoRequest modifyUserInfoRequest)  {
        userService.updateUser(request,modifyUserInfoRequest);

        ApiResponse<ResponseMessage> response = ApiResponse.<ResponseMessage>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.USER_INFO_UPDATE_SUCESS)
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
