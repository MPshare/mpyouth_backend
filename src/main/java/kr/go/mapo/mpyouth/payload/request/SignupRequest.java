package kr.go.mapo.mpyouth.payload.request;


import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String adminLoginId;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    private Set<String> roles;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;


    @NotBlank
    @Size(min = 1, max = 10)
    private String username;


}

