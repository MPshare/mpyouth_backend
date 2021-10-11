package kr.go.mapo.mpyouth.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.go.mapo.mpyouth.domain.Organization;
import kr.go.mapo.mpyouth.domain.User;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
public class UserInfoResponse {

    @Schema(description = "아이디",nullable = false)
    private String adminLoginId;
    @Schema(description = "이름", nullable = false)
    private String username;
    @Schema(description = "이메일", nullable = false)
    private String email;
    @Schema(description = "연락처", nullable = false)
    private String phone;
    @Schema(description = "생성자")
    private String createdBy;
    @Schema(description = "수정자")
    private String moderatedBy;
    @Schema(description = "기관")
    private Organization organization;


    @Schema(description = "생성일자")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createDate;
    @Schema(description = "수정일자")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updateDate;

    public UserInfoResponse(User user) {
        adminLoginId = user.getAdminLoginId();
        username = user.getUsername(); // lazy
        email = user.getEmail();
        phone = user.getPhone();
        createdBy = user.getCreatedBy(); // lazy
        moderatedBy = user.getModifiedBy();
        organization= user.getOrganization();
    }
}
