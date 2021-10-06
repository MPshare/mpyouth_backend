package kr.go.mapo.mpyouth.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import kr.go.mapo.mpyouth.domain.RecruitStatus;
import kr.go.mapo.mpyouth.domain.VolunteerType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProgramResponse {
    private Long programId;

//    @NotBlank(message = "타이틀은 공백일 수 없습니다.")
    private String title;
//    @NotBlank(message = "설명은 공백일 수 없습니다.")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime endDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime recruitStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime recruitEndDate;
    private Integer recruitNumber;
    private String location;
    private String managerName;
    private String managerContact;
    private String url;
    private RecruitStatus recruitStatus;
    private Integer entryFee;
    private String targetAge;
    private String caution;
    private String period;
    private VolunteerType volunteerType;

    private CategoryResponse category;

    private OrganizationResponse organization;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ProgramFileResponse> programFiles;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProgramThumbnailResponse thumbnail;

//    private Category category;
}
