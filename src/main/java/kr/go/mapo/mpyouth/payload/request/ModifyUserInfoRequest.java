package kr.go.mapo.mpyouth.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.go.mapo.mpyouth.domain.Organization;
import lombok.Getter;
import org.aspectj.weaver.ast.Or;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Getter
public class ModifyUserInfoRequest {

    @Schema(description = "이름", nullable = true)
    private String username;

    @Email
    @Schema(description = "이메일", nullable = true, example = "mapo@mapoyouth.com")
    private String email;

    @Schema(description = "전화번호", nullable = true, example= "전화번호는 xxx-xxxx-xxxx  또는 xx-xxxxx-xxxx여야 합니다.")
    @Pattern(regexp="^\\d{2,3}-\\d{3,4}-\\d{4}$",
            message = "전화번호는 xxx-xxxx-xxxx  또는 xx-xxxxx-xxxx여야 합니다.")
    private String phone;
    @Schema(description = "기관 번호", nullable = true)
    private Long organizationId;

    @Schema(description = "권한", nullable = true,example="[admin],[manager],[admin,manaer]")
    private Set<String> roles;

}
