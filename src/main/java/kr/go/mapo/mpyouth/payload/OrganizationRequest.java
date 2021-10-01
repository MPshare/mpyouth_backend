package kr.go.mapo.mpyouth.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrganizationRequest {

    private Long organizationId;
    private String name;
    private String address;
    private String phone;
    private String representative;
    private String homepage;
    private String introduce;
}
