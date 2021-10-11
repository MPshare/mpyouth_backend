package kr.go.mapo.mpyouth.payload.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@Setter
@ToString
@ApiModel(description = "기관 저장/수정용 Request")
public class OrganizationRequest {
    @Schema(hidden = true)
    private Long organizationId;
    @Schema(description = "기관이름", example = "마포청소년문화의집", required = true)
    private String name;
    @Schema(description = "주소", example = "주소", required = true)
    private String address;
    @Schema(description = "전화번호", example = "010-1234-5678", required = true)
    private String phone;
    @Schema(description = "기관대표", example = "김철수", required = true)
    private String representative;
    @Schema(description = "홈페이지", example = "http://url.com", required = true)
    private String homepage;
    @Size(max = 1000)
    private String introduce;
}
