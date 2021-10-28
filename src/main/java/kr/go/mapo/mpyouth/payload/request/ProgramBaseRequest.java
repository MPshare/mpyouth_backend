package kr.go.mapo.mpyouth.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.go.mapo.mpyouth.domain.ContentsStatus;
import kr.go.mapo.mpyouth.domain.RecruitStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProgramBaseRequest {
    @ApiModelProperty(value = "프로그램 제목", example = "청소년동아리지원사업 '스스로 프로젝트'", required = true)
    @NotBlank
    @Size(max = 100)
    private String title;

    @ApiModelProperty(value = "설명", example = "마포구 청소년동아리의 균형적 발전과 청소년문화 환경을 개선하고자 ...", required = true)
    @NotBlank
    @Size(max = 2000)
    private String description;

    @ApiModelProperty(value = "프로그램 위치", example = "서울특별시 마포구 도화동 353-2", required = true)
    @NotBlank
    @Size(max = 100)
    private String location;

    @ApiModelProperty(value = "모집인원수", example = "10", required = true)
    @PositiveOrZero
    @NotNull
    private Integer recruitNumber;

    @ApiModelProperty(value = "인원모집 시작날짜", example = "2021-12-25 00:00:00", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private LocalDateTime recruitStartDate;

    @ApiModelProperty(value = "인원모집 종료날짜", example = "2021-12-25 00:00:00", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private LocalDateTime recruitEndDate;

    @ApiModelProperty(value = "프로그램 시작날짜", example = "2021-12-25 00:00:00", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private LocalDateTime startDate;

    @ApiModelProperty(value = "프로그램 종료날짜", example = "2021-12-25 00:00:00", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private LocalDateTime endDate;

    @ApiModelProperty(value = "신청 url", example = "http://mwyouth.org/", required = true)
    @NotBlank
    @Size(max = 2083)
    private String url;

    @ApiModelProperty(value = "관리자 이름", example = "김철수", required = true)
    @NotBlank
    @Size(max = 20)
    private String managerName;

    @ApiModelProperty(value = "관리자 연락처", example = "010-1234-5678", required = true)
    @NotBlank
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$")
    private String managerContact;

    @ApiModelProperty(value = "모집상태", example = "RECRUITING", allowableValues = "RECRUITING, DONE", required = true)
    @NotNull
    private RecruitStatus recruitStatus;

    @ApiModelProperty(value = "진행상태", example = "BEFORE", allowableValues = "BEFORE, PROCEEDING, DONE", required = true)
    @NotNull
    private ContentsStatus contentsStatus;

    @ApiModelProperty(value = "주의사항", example = "참여하시기 전에 다음 사항을 숙지해주세요...", required = true)
    @Parameter(name = "caution", in = ParameterIn.QUERY, array = @ArraySchema( schema = @Schema(type = "string")))
    @NotBlank
    @Size(max = 2000)
    private String caution;

    @ApiModelProperty(value = "기관 ID", example = "1", required = true)
    @NotNull
    private Long organizationId;

    @ApiModelProperty(value = "카테고리 ID", example = "1", required = true)
    @NotNull
    private Long categoryId;

}
