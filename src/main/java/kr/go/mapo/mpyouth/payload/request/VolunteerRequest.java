package kr.go.mapo.mpyouth.payload.request;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.go.mapo.mpyouth.domain.VolunteerType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ApiModel(value = "자원봉사 request")
public class VolunteerRequest extends ProgramBaseRequest {
    @Schema(description = "봉사유형", example = "INDIVIDUAL", allowableValues = {"INDIVIDUAL", "GROUP"}, required = true)
    @NotNull
    private VolunteerType volunteerType;

    @Schema(description = "봉사활동 주기", example = "월|화|수", required = true)
    @NotBlank
    @Size(max = 100)
    private String period;
}
