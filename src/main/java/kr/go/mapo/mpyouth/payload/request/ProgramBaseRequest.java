package kr.go.mapo.mpyouth.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.go.mapo.mpyouth.domain.Category;
import kr.go.mapo.mpyouth.domain.ContentsStatus;
import kr.go.mapo.mpyouth.domain.Organization;
import kr.go.mapo.mpyouth.domain.RecruitStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProgramBaseRequest {
    private String title;
    private String description;
    private String location;

    private Integer recruitNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime recruitStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime recruitEndDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime endDate;

    private String url;
    private String managerName;
    private String managerContact;

    private RecruitStatus recruitStatus;
    private ContentsStatus contentsStatus;

    private Long organizationId;
    private Long categoryId;
}
