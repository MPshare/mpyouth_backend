package kr.go.mapo.mpyouth.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class LifeLongEdu extends ProgramBaseEntity {
    @Size(max = 100)
    private String targetAge;
    private Integer entryFee;

    @Builder
    public LifeLongEdu(Long id, String title, String description, String location, Integer recruitNumber, LocalDateTime recruitStartDate, LocalDateTime recruitEndDate, LocalDateTime startDate, LocalDateTime endDate, String url, String managerName, String managerContact, RecruitStatus recruitStatus, ContentsStatus contentsStatus, String caution, Organization organization, Category category, String targetAge, Integer entryFee) {
        super(id, title, description, location, recruitNumber, recruitStartDate, recruitEndDate, startDate, endDate, url, managerName, managerContact, recruitStatus, contentsStatus, caution, organization, category);
        this.targetAge = targetAge;
        this.entryFee = entryFee;
    }
}
