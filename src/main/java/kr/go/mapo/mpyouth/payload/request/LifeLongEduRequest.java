package kr.go.mapo.mpyouth.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LifeLongEduRequest extends ProgramBaseRequest{
    @Schema(description = "대상연령", example = "초|중|고", required = true)
    private String targetAge;
    @Schema(description = "참가비", example = "0", required = true)
    private Integer entryFee;
}
