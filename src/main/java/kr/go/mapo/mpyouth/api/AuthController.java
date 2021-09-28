package kr.go.mapo.mpyouth.api;


import kr.go.mapo.mpyouth.payload.request.LoginRequest;
import kr.go.mapo.mpyouth.payload.request.SignupRequest;
import kr.go.mapo.mpyouth.payload.request.InitPasswordRequest;
import kr.go.mapo.mpyouth.payload.response.MessageResponse;
import kr.go.mapo.mpyouth.payload.response.ResponseMessage;
import kr.go.mapo.mpyouth.repository.RoleRepository;
import kr.go.mapo.mpyouth.repository.UserRepository;
import kr.go.mapo.mpyouth.security.utils.JwtUtils;
import kr.go.mapo.mpyouth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }



}
