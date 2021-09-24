package kr.go.mapo.mpyouth.payload.request;

import lombok.Getter;

@Getter
public class SearchPasswordRequest {

    private String adminLoginId;
    private String email;
}
