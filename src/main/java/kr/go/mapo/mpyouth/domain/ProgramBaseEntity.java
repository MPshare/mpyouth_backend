package kr.go.mapo.mpyouth.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class ProgramBaseEntity extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//
    private String title;//
    private String description;//
    private String location;//

    private Integer recruitNumber; //

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime recruitStartDate;//

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime recruitEndDate;//
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime startDate;//
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime endDate;//

    private String url; //
    private String managerName; //
    private String managerContact; //

    @Enumerated(EnumType.STRING)
    private RecruitStatus recruitStatus; //
    @Enumerated(EnumType.STRING)
    private ContentsStatus contentsStatus;

    private String caution;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Organization organization;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Category category;
}
