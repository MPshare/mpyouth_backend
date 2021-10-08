package kr.go.mapo.mpyouth.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;


@Getter
@MappedSuperclass
public class BaseEntity {
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createDate = now;
        updateDate = now;
    }

    @CreatedBy
    @Column(name = "created_by",updatable = false)
    @Schema(description = "생성자")
    protected String createdBy;


    @Schema(description = "수정자")
    @Column(name = "modified_by")
    @LastModifiedBy
    protected String modifiedBy;

    @PreUpdate
    public void preUpdate() {
        updateDate = LocalDateTime.now();
    }
}
