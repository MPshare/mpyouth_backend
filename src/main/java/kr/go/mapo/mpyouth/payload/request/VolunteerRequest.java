package kr.go.mapo.mpyouth.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VolunteerRequest extends ProgramBaseRequest{
    private String targetAge;
    private Integer entryFee;
}
