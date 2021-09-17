package kr.go.mapo.mpyouth.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class Program {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String title;
    @NotNull
    private String description;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private LocalDateTime recruitStartDate;
    private LocalDateTime recruitEndDate;

    @NotNull
    private int recruitNumber;

    @NotNull
    private String location;

    @NotNull
    private String managerName;

    @NotNull
    private String managerContact;

    @NotNull
    private String url;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RecruitStatus recruitStatus;

    private int entryFee;

    private String targetAge;

    @NotNull
    private String caution;
    private String period;

    @Enumerated(EnumType.STRING)
    private VolunteerType volunteerType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oraganization_fk")
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk")
    private Category category;

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public void changeMangerName(String managerName) {
        this.managerName = managerName;
    }
}
