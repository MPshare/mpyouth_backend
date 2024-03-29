package kr.go.mapo.mpyouth.payload.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(description = "기관정보 Response")
public class OrganizationResponse {
    @Schema(description = "기관ID")
    private Long organizationId;
    @Schema(description = "기관이름", example = "마포청소년문화의집")
    private String name;
    @Schema(description = "주소", example = "주소")
    private String address;
    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phone;
    @Schema(description = "기관대표", example = "김철수")
    private String representative;
    @Schema(description = "홈페이지", example = "https://url.com")
    private String homepage;
    @Schema(description = "자기소개", example = "자기소개")
    private String introduce;
}
