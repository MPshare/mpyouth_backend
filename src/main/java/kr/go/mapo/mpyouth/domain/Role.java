package kr.go.mapo.mpyouth.domain;

import lombok.*;

import javax.annotation.Resource;
import javax.persistence.*;

@Getter
@Entity
@Table(name = "roles")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;



}
