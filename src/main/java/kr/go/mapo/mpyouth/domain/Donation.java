package kr.go.mapo.mpyouth.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate
@ToString
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Donation extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Integer recruitNumber;
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

    private Integer entryFee;

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
