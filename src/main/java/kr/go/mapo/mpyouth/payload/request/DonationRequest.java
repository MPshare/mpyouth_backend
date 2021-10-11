package kr.go.mapo.mpyouth.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.go.mapo.mpyouth.domain.ContentsStatus;
import kr.go.mapo.mpyouth.domain.RecruitStatus;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@ToString
@Getter
public class DonationRequest {
    @Schema(description = "재능기부 제목", example = "청소년동아리지원사업 '스스로 프로젝트'", required = true)
    private String title;

    @Schema(description = "설명", example = "마포구 청소년동아리의 균형적 발전과 청소년문화 환경을 개선하고자 ...", required = true)
    private String description;

    @Schema(description = "모집인원수", example = "10", required = true)
    private Integer recruitNumber;
    @Schema(description = "재능기부 위치", example = "서울특별시 마포구 도화동 353-2", required = true)
    private String location;

    @Schema(description = " 재능기부 시작날짜", example = "2021-12-25 00:00:00", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime startDate;

    @Schema(description = "재능기부 종료날짜", example = "2021-12-25 00:00:00", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime endDate;

    @Schema(description = "인원모집 시작날짜", example = "2021-12-25 00:00:00", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime recruitStartDate;

    @Schema(description = "인원모집 종료날짜", example = "2021-12-25 00:00:00", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime recruitEndDate;

    @Schema(description = "신청 url", example = "http://mwyouth.org/", required = true)
    private String url;

    @Schema(description = "모집상태", example = "RECRUITING", allowableValues = {"RECRUITING", "DONE"}, required = true)
    private RecruitStatus recruitStatus;

    @Schema(description = "진행상태", example = "BEFORE", allowableValues = {"BEFORE", "PROCEEDING", "DONE"}, required = true)
    private ContentsStatus contentsStatus;

    @Schema(description = "관리자 이름", example = "김철수", required = true)
    private String managerName;

    @Schema(description = "관리자 연락처", example = "010-1234-5678", required = true)
    private String managerContact;

    @Schema(description = "참가비", example = "0", required = true)
    private Integer entryFee;

    @Schema(description = "멘토", example = "이영희", required = true)
    private String mentor;

    @Schema(description = "대상연령", example = "초|중|고", required = true)
    private String targetAge;

    @Schema(description = "주의사항", example = "참여하시기 전에 다음 사항을 숙지해주세요...", required = true)
    private String caution;

    @Schema(description = "기관 ID", example = "1", required = true)
    private Long organizationId;

    @Schema(description = "카테고리 ID", example = "1", required = true)
    private Long categoryId;

}
