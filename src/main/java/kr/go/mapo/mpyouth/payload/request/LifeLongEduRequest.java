package kr.go.mapo.mpyouth.payload.request;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Api(tags = "평생교육 request")
public class LifeLongEduRequest extends ProgramBaseRequest{
    @Schema(description = "대상연령", example = "초|중|고")
    @NotEmpty
    @Size(max = 100)
    private String targetAge;

    @Schema(description = "참가비", example = "0")
    @NotNull
    private Integer entryFee;
}
