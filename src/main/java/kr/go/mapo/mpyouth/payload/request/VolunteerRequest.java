package kr.go.mapo.mpyouth.payload.request;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.go.mapo.mpyouth.domain.VolunteerType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "자원봉사 request")
public class VolunteerRequest extends ProgramBaseRequest {
    @Schema(description = "봉사유형", example = "INDIVIDUAL", allowableValues = {"INDIVIDUAL", "GROUP"}, required = true)
    private VolunteerType volunteerType;
    @Schema(description = "봉사활동 주기", example = "월|화|수", required = true)
    private String period;
}
