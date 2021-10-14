package kr.go.mapo.mpyouth.payload.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Api(tags = "청소년 프로그램 request")
@Getter
@Setter
@ToString
@ApiModel
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProgramRequest extends ProgramBaseRequest {
    @ApiModelProperty(name = "entryFee", value = "참가비", notes = "참가비 내용", example = "0", required = true)
    @NotNull
    @PositiveOrZero
    private Integer entryFee;
    @ApiModelProperty(value = "대상연령", example = "초|중|고", required = true)
    @NotBlank
    private String targetAge;

}
