package kr.go.mapo.mpyouth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ProgramThumbnail extends BaseEntity {
    @Id @GeneratedValue
    private Long id;

    private String originalFileName;
    private String fileName;
    private String filePath;
    private Long fileSize;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "program_fk")
    private Program program;
}
