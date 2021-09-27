package kr.go.mapo.mpyouth.api;

import kr.go.mapo.mpyouth.payload.response.ResponseMessage;
import kr.go.mapo.mpyouth.service.AuthService;
import kr.go.mapo.mpyouth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final AuthService authService;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/manager")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public String managerAccess() {
        return "manager Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<ResponseMessage>> logout(HttpServletRequest request) {
        authService.logout(request);

        ApiResponse<ResponseMessage> response = ApiResponse.<ResponseMessage>builder()
                .status(ApiStatus.SUCCESS)
                .message(ResponseMessage.LOGOUT_SUCCESS)
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}