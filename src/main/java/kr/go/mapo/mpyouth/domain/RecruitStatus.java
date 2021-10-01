package kr.go.mapo.mpyouth.domain;

import lombok.Getter;

@Getter
public enum RecruitStatus {
    RECRUITING("모집중"), DONE("모집완료");

    private final String name;

    RecruitStatus(String name) {
        this.name = name;
    }
}
