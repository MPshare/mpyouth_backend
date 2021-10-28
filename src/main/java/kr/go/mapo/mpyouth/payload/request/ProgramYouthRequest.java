package kr.go.mapo.mpyouth.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.go.mapo.mpyouth.domain.RecruitStatus;
import kr.go.mapo.mpyouth.domain.VolunteerType;
import kr.go.mapo.mpyouth.payload.response.ProgramFileResponse;
import kr.go.mapo.mpyouth.payload.response.ProgramThumbnailResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Api(tags = "청소년 프로그램 request")
@Getter
@Setter
@ToString
@ApiModel
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProgramYouthRequest extends ProgramBaseRequest {
    @Schema(description = "참가비", example = "0", required = true)
    @ApiModelProperty(name = "entryFee", value = "참가비", notes = "참가비 내용", example = "0", required = true)
    @NotNull
    private Integer entryFee;

    @Schema(description = "대상연령", example = "초|중|고", required = true)
    @ApiModelProperty(value = "대상연령", example = "초|중|고", required = true)
    @NotEmpty
    @Size(max = 100)
    private String targetAge;

}
