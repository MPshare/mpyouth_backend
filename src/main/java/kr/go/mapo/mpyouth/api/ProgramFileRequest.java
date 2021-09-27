package kr.go.mapo.mpyouth.api;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

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
