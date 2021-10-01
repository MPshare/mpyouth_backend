package kr.go.mapo.mpyouth.domain;

import lombok.Getter;

@Getter
public enum ContentsStatus {
    BEFORE("진행전"), PROCEEDING("진행중"), DONE("완료됨");

    private final String name;

    ContentsStatus(String name) {
        this.name = name;
    }
}
