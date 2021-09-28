package kr.go.mapo.mpyouth.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate

public class Organization {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String representative;
    private String homepage;
    private String introduce;
}
