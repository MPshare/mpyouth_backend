package kr.go.mapo.mpyouth.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class ProgramBaseEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    private String title;

    @Size(max = 2000)
    private String description;

    @Size(max = 100)
    private String location;

    private Integer recruitNumber;

    private LocalDateTime recruitStartDate;

    private LocalDateTime recruitEndDate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Size(max = 500)
    private String url;

    @Size(max = 20)
    private String managerName;

    @Size(max = 50)
    private String managerContact;

    @Enumerated(EnumType.STRING)
    private RecruitStatus recruitStatus;

    @Enumerated(EnumType.STRING)
    private ContentsStatus contentsStatus;

    private String caution;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "oraganization_fk")
    private Organization organization;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_fk")
    private Category category;
}
