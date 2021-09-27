package kr.go.mapo.mpyouth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(of = {"id", "originalFileName", "fileName", "filePath", "fileSize"})
public class ProgramFile extends BaseEntity {
    @Id @GeneratedValue
    private Long id;
    private String originalFileName;
    private String fileName;
    private String filePath;
    private Long fileSize;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "program_fk")
    private Program program;
}
