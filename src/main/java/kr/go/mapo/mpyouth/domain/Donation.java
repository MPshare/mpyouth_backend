package kr.go.mapo.mpyouth.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
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

    @Size(max = 100)
    private String title;

    @Size(max = 2000)
    private String description;

    private Integer recruitNumber;

    @Size(max = 100)
    private String location;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime recruitStartDate;
    private LocalDateTime recruitEndDate;

    @Size(max = 500)
    private String url;

    @Enumerated(EnumType.STRING)
    private RecruitStatus recruitStatus;

    @Enumerated(EnumType.STRING)
    private ContentsStatus contentsStatus;

    @Size(max = 20)
    private String managerName;

    @Size(max = 50)
    private String managerContact;

    private Integer entryFee;

    @Size(max = 20)
    private String mentor;

    @Size(max = 100)
    private String targetAge;

    @Size(max = 2000)
    private String caution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oraganization_fk")
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk")
    private Category category;
}
