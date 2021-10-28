package kr.go.mapo.mpyouth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Program extends ProgramBaseEntity {

    private Integer entryFee;

    @Size(max = 100)
    private String targetAge;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProgramFile> programFiles = new ArrayList<>();

    @OneToOne(mappedBy = "program", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private ProgramThumbnail programThumbnail;

    @Builder
    public Program(Long id, String title, String description, String location, Integer recruitNumber, LocalDateTime recruitStartDate, LocalDateTime recruitEndDate, LocalDateTime startDate, LocalDateTime endDate, String url, String managerName, String managerContact, RecruitStatus recruitStatus, ContentsStatus contentsStatus, Organization organization, Category category, Integer entryFee, String targetAge, List<ProgramFile> programFiles, ProgramThumbnail programThumbnail) {
        super(id, title, description, location, recruitNumber, recruitStartDate, recruitEndDate, startDate, endDate, url, managerName, managerContact, recruitStatus, contentsStatus, organization, category);
        this.entryFee = entryFee;
        this.targetAge = targetAge;
        this.programFiles = programFiles;
        this.programThumbnail = programThumbnail;
    }
}
