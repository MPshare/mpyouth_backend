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
    public Program(
            Long id, @Size(max = 100) String title,
            @Size(max = 2000) String description,
            @Size(max = 100) String location,
            Integer recruitNumber,
            LocalDateTime recruitStartDate,
            LocalDateTime recruitEndDate,
            LocalDateTime startDate,
            LocalDateTime endDate,
            @Size(max = 500) String url,
            @Size(max = 20) String managerName,
            @Size(max = 50) String managerContact,
            RecruitStatus recruitStatus,
            ContentsStatus contentsStatus,
            String caution,
            Organization organization,
            Category category,
            Integer entryFee,
            String targetAge,
            List<ProgramFile> programFiles,
            ProgramThumbnail programThumbnail
    ) {
        super(id, title, description, location, recruitNumber, recruitStartDate, recruitEndDate, startDate, endDate, url, managerName, managerContact, recruitStatus, contentsStatus, caution, organization, category);
        this.entryFee = entryFee;
        this.targetAge = targetAge;
        this.programFiles = programFiles;
        this.programThumbnail = programThumbnail;
    }
}
