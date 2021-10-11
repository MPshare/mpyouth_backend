package kr.go.mapo.mpyouth.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.go.mapo.mpyouth.domain.ContentsStatus;
import kr.go.mapo.mpyouth.domain.RecruitStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProgramUpdateBaseRequest {
    @Schema(description = "프로그램 제목", example = "청소년동아리지원사업 '스스로 프로젝트'")
    @ApiModelProperty(value = "프로그램 제목", example = "청소년동아리지원사업 '스스로 프로젝트'")
    private String title;
    @Schema(description = "설명", example = "마포구 청소년동아리의 균형적 발전과 청소년문화 환경을 개선하고자 ...")
    @ApiModelProperty(value = "설명", example = "마포구 청소년동아리의 균형적 발전과 청소년문화 환경을 개선하고자 ...")
    private String description;
    @Schema(description = "프로그램 위치", example = "서울특별시 마포구 도화동 353-2")
    @ApiModelProperty(value = "프로그램 위치", example = "서울특별시 마포구 도화동 353-2")
    private String location;
    @Schema(description = "모집인원수", example = "10")
    @ApiModelProperty(value = "모집인원수", example = "10")
    private Integer recruitNumber;
    @Schema(description = "인원모집 시작날짜", example = "2021-12-25 00:00:00")
    @ApiModelProperty(value = "인원모집 시작날짜", example = "2021-12-25 00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime recruitStartDate;
    @Schema(description = "인원모집 종료날짜", example = "2021-12-25 00:00:00")
    @ApiModelProperty(value = "인원모집 종료날짜", example = "2021-12-25 00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime recruitEndDate;
    @Schema(description = " 프로그램 시작날짜", example = "2021-12-25 00:00:00")
    @ApiModelProperty(value = "프로그램 시작날짜", example = "2021-12-25 00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    @Schema(description = "프로그램 종료날짜", example = "2021-12-25 00:00:00")
    @ApiModelProperty(value = "프로그램 종료날짜", example = "2021-12-25 00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
    @Schema(description = "신청 url", example = "http://mwyouth.org/")
    @ApiModelProperty(value = "신청 url", example = "http://mwyouth.org/")
    private String url;
    @Schema(description = "관리자 이름", example = "김철수")
    @ApiModelProperty(value = "관리자 이름", example = "김철수")
    private String managerName;
    @Schema(description = "관리자 연락처", example = "010-1234-5678")
    @ApiModelProperty(value = "관리자 연락처", example = "010-1234-5678")
    private String managerContact;
    @Schema(description = "모집상태", example = "RECRUITING", allowableValues = {"RECRUITING", "DONE"})
    @ApiModelProperty(value = "모집상태", example = "RECRUITING", allowableValues = "RECRUITING, DONE")
    private RecruitStatus recruitStatus;
    @Schema(description = "진행상태", example = "BEFORE", allowableValues = {"BEFORE", "PROCEEDING", "DONE"})
    @ApiModelProperty(value = "진행상태", example = "BEFORE", allowableValues = "BEFORE, PROCEEDING, DONE")
    private ContentsStatus contentsStatus;
    @Schema(description = "기관 ID", example = "1")
    @ApiModelProperty(value = "기관 ID", example = "1")
    private Long organizationId;
    @Schema(description = "카테고리 ID", example = "1")
    @ApiModelProperty(value = "카테고리 ID", example = "1")
    private Long categoryId;
}
