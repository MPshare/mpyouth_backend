package kr.go.mapo.mpyouth.payload.request;

import kr.go.mapo.mpyouth.domain.Organization;
import lombok.Getter;
import org.aspectj.weaver.ast.Or;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Getter
public class ModifyUserInfoRequest {
    private String username;

    @Email
    private String email;
    @Pattern(regexp="^\\d{2,3}-\\d{3,4}-\\d{4}$",
            message = "전화번호는 xxx-xxxx-xxxx  또는 xx-xxxxx-xxxx여야 합니다.")
    private String phone;
    private Long organizationId;
    private Set<String> roles;

}
