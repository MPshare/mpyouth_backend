package kr.go.mapo.mpyouth.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Donation {
    @Id @GeneratedValue
    private Long id;

    private String title;
    private String description;
    private int recruitNumber;
    private String location;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private LocalDateTime recruitStartDate;
    private LocalDateTime recruitEndDate;

    private String url;

    @Enumerated(EnumType.STRING)
    private RecruitStatus recruitStatus;

    @Enumerated(EnumType.STRING)
    private ContentsStatus contentsStatus;

    private String managerName;
    private String managerContact;

    private int entryFee;

    private String mentor;

    private String targetAge;

    private String caution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oraganization_fk")
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk")
    private Category category;
}
