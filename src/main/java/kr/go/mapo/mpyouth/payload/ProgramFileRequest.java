package kr.go.mapo.mpyouth.payload;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@ToString
public class ProgramFileRequest {
    private String originalFileName;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private Long programId;
}
