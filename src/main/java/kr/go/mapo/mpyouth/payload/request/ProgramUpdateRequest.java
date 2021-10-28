package kr.go.mapo.mpyouth.payload.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Api(tags = "청소년 프로그램 request")
@Getter
@Setter
@ToString
@ApiModel
public class ProgramUpdateRequest extends ProgramUpdateBaseRequest {
    @Schema(description = "참가비", example = "0")
    @ApiModelProperty(name = "entryFee", value = "참가비", notes = "참가비 내용", example = "0")
    private Integer entryFee;

    @Schema(description = "대상연령", example = "초|중|고")
    @ApiModelProperty(value = "대상연령", example = "초|중|고")
    private String targetAge;

}
