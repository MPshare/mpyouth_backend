package kr.go.mapo.mpyouth.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgramThumbnailResponse {
    private String originalFileName;
    private String fileName;
    private String fileUri;
    private Long fileSize;
}
