package kr.go.mapo.mpyouth.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString(exclude = {"organization", "category", "programFiles"})
@DynamicUpdate
public class Program extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;
    @NotNull
    private String description;

    @Future
    private LocalDateTime startDate;
    @Future
    private LocalDateTime endDate;

    @Future
    private LocalDateTime recruitStartDate;
    @Future
    private LocalDateTime recruitEndDate;

    @NotNull
    private Integer recruitNumber;

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

    private Integer entryFee;

    private String targetAge;

    @NotNull
    private String caution;
    private String period;

    @Enumerated(EnumType.STRING)
    private VolunteerType volunteerType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "oraganization_fk")
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk")
    private Category category;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private List<ProgramFile> programFiles = new ArrayList<>();


    public void setOrganization(Organization organization) {
        this.organization = organization;
        organization.getPrograms().add(this);
    }
}
