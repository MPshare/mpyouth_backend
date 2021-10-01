package kr.go.mapo.mpyouth.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Category {
    @Id @GeneratedValue
    private Long categoryId;

    private String name;
}
