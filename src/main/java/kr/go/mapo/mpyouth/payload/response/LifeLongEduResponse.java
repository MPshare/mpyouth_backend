package kr.go.mapo.mpyouth.payload.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LifeLongEduResponse extends ProgramBaseResponse {
    @Schema(description = "대상연령", example = "초|중|고", required = true)
    private String targetAge;
    @Schema(description = "참가비", example = "0", required = true)
    private Integer entryFee;
}
