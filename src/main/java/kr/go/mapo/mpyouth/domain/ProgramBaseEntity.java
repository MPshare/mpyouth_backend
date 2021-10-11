package kr.go.mapo.mpyouth.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class ProgramBaseEntity extends BaseEntity{
    @Id @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private String location;

    private Integer recruitNumber;
    private LocalDateTime recruitStartDate;
    private LocalDateTime recruitEndDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String url;
    private String managerName;
    private String managerContact;

    @Enumerated(EnumType.STRING)
    private RecruitStatus recruitStatus;
    @Enumerated(EnumType.STRING)
    private ContentsStatus contentsStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organization organization;
    @OneToOne(fetch = FetchType.LAZY)
    private Category category;
}
