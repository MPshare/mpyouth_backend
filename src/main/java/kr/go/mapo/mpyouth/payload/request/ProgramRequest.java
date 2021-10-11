package kr.go.mapo.mpyouth.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.go.mapo.mpyouth.domain.RecruitStatus;
import kr.go.mapo.mpyouth.domain.VolunteerType;
import kr.go.mapo.mpyouth.payload.response.ProgramFileResponse;
import kr.go.mapo.mpyouth.payload.response.ProgramThumbnailResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel(value = "프로그램 저장")
@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
//@ApiModel(description = "프로그램 저장 request")
public class ProgramRequest {
    private Long programId;

    //    @NotBlank(message = "타이틀은 공백일 수 없습니다.")
    @ApiModelProperty(required = true, example = "RECRUTING")
    private String title;
    //    @NotBlank(message = "설명은 공백일 수 없습니다.")
    @NotBlank
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
    @Schema(description = "상태", allowableValues = {"RECRUITING", "DONE"})
    private RecruitStatus recruitStatus;
    private Integer entryFee;
    private String targetAge;
    private String caution;
    private String period;
    private VolunteerType volunteerType;

    private Long organizationId;

    private Long categoryId;

    private List<ProgramFileResponse> programFiles;

//    private Category category;
}
