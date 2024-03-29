package kr.go.mapo.mpyouth.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Volunteer extends ProgramBaseEntity{
    @Enumerated(EnumType.STRING)
    private VolunteerType volunteerType;

    @Size(max = 100)
    private String period;

    @Builder
    public Volunteer(Long id, String title, String description, String location, Integer recruitNumber, LocalDateTime recruitStartDate, LocalDateTime recruitEndDate, LocalDateTime startDate, LocalDateTime endDate, String url, String managerName, String managerContact, RecruitStatus recruitStatus, ContentsStatus contentsStatus, String caution, Organization organization, Category category, VolunteerType volunteerType, String period) {
        super(id, title, description, location, recruitNumber, recruitStartDate, recruitEndDate, startDate, endDate, url, managerName, managerContact, recruitStatus, contentsStatus, caution, organization, category);
        this.volunteerType = volunteerType;
        this.period = period;
    }
}
