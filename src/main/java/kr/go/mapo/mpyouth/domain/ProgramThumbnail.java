package kr.go.mapo.mpyouth.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@EntityListeners(AuditingEntityListener.class)

public class ProgramThumbnail extends BaseEntity {
    @Id @GeneratedValue
    private Long id;

    private String originalFileName;
    private String fileName;
    private String filePath;
    private Long fileSize;
}
