package kr.go.mapo.mpyouth.payload.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrganizationResponse {

    private Long organizationId;
    private String name;
    private String address;
    private String phone;
    private String representative;
    private String homepage;
    private String introduce;
}
