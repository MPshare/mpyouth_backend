package kr.go.mapo.mpyouth.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class SearchIdRequest {
    @NotBlank
    private String email;
}
