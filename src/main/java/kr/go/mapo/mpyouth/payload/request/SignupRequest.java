package kr.go.mapo.mpyouth.payload.request;


import io.swagger.v3.oas.annotations.media.Schema;
import kr.go.mapo.mpyouth.domain.Organization;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    @Schema(description = "아이디",  nullable = false,example ="길이는 3~20")
    private String adminLoginId;

    @NotBlank
    @Size(max = 50)
    @Email
    @Schema(description = "이메일",nullable = false, example ="mapo@mapoyouth.com")
    private String email;
    @Schema(description = "권한", example ="[admin],[manager],[admin,manager]")
    private Set<String> roles;

    @NotBlank
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    @Schema(description = "패스워드", example ="비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;


    @NotBlank
    @Pattern(regexp="^\\d{2,3}-\\d{3,4}-\\d{4}$",
             message = "전화번호는 xxx-xxxx-xxxx  또는 xx-xxxxx-xxxx여야 합니다.")
    @Schema(description = "전화번호", example ="전화번호는 xxx-xxxx-xxxx  또는 xx-xxxxx-xxxx여야 합니다.")
    private String phone;


    @NotBlank
    @Size(min = 1, max = 10)
    @Schema(description = "이름", example ="길이 1~10")
    private String username;
    @Schema(description = "기관 id", nullable = false)
    private Long organizationId;


}

