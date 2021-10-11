package kr.go.mapo.mpyouth.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VolunteerResponse extends ProgramBaseResponse {
    private String targetAge;
    private Integer entryFee;
}
