package kr.go.mapo.mpyouth.api;

import lombok.Data;

@Data
public class ProgramFileResponse {
    private String originalFileName;
    private String fileName;
    private String filePath;
    private Long fileSize;
}
