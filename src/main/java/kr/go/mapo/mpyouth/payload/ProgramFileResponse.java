package kr.go.mapo.mpyouth.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProgramFileResponse {
    private String originalFileName;
    private String fileName;
    private String fileUri;
    private Long fileSize;
}
