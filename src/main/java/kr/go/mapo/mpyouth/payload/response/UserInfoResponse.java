package kr.go.mapo.mpyouth.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.go.mapo.mpyouth.domain.Organization;
import kr.go.mapo.mpyouth.domain.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfoResponse {
    private String adminLoginId;
    private String username;
    private String email;
    private String phone;
    private String createdBy;
    private String moderatedBy;
    private Organization organization;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createDate;
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
