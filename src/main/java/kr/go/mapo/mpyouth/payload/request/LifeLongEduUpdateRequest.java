package kr.go.mapo.mpyouth.payload.request;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Api(tags = "평생교육 request")
public class LifeLongEduUpdateRequest extends ProgramUpdateBaseRequest{
    @Schema(description = "대상연령", example = "초|중|고", required = true)
    @NotBlank
    @Size(max = 100)
    private String targetAge;

    @Schema(description = "참가비", example = "0", required = true)
    @NotNull
    private Integer entryFee;
}
