package kr.go.mapo.mpyouth.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProgramThumbnail extends BaseEntity {
    @Id @GeneratedValue
    private Long id;

    private String originalFileName;
    private String fileName;
    private String filePath;
    private Long fileSize;
}
