package kr.go.mapo.mpyouth.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Program {
    @Id @GeneratedValue
    private Long id;

    private String title;
    private String description;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private LocalDateTime recruitStartDate;
    private LocalDateTime recruitEndDate;

    private int recruitNumber;

    private String location;

    private String managerName;
    private String managerContact;

    private String url;

    @Enumerated(EnumType.STRING)
    private RecruitStatus recruitStatus;

    private int entryFee;

    private String targetAge;

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
}
