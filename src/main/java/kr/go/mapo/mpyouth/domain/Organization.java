package kr.go.mapo.mpyouth.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
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
