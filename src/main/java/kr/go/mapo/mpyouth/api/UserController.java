package kr.go.mapo.mpyouth.api;

import kr.go.mapo.mpyouth.payload.request.InitPasswordRequest;
import kr.go.mapo.mpyouth.payload.request.SearchIdRequest;
import kr.go.mapo.mpyouth.payload.request.SearchPasswordRequest;
import kr.go.mapo.mpyouth.payload.response.ResponseMessage;
import kr.go.mapo.mpyouth.service.AuthService;
import kr.go.mapo.mpyouth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import kr.go.mapo.mpyouth.payload.response.ResponseMessage;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

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

}
