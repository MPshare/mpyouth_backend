package kr.go.mapo.mpyouth.domain;

import lombok.Getter;

@Getter
public enum AuthStatus {

    SUCCESS("성공"), FAIL("실패");

    private final String name;

    AuthStatus(String name) {
        this.name = name;
    }
}
