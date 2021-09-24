package kr.go.mapo.mpyouth.domain;

import lombok.Getter;

@Getter
public enum VolunteerType {
    INDIVIDUAL("개인"), GROUP("단체");

    private final String name;

    VolunteerType(String name) {
        this.name = name;
    }
}
