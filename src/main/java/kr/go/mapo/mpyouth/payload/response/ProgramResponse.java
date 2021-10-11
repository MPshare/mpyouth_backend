package kr.go.mapo.mpyouth.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import kr.go.mapo.mpyouth.domain.RecruitStatus;
import kr.go.mapo.mpyouth.domain.VolunteerType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "프로그램 response")
public class ProgramResponse extends ProgramBaseResponse {

    private Integer entryFee;
    private String targetAge;


    private CategoryResponse category;

    private OrganizationResponse organization;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ProgramFileResponse> programFiles;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProgramThumbnailResponse thumbnail;

}
