package kr.go.mapo.mpyouth.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Organization extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Size(max = 20)
    private String name;

//    @Size(max = 50)
    private String address;

//    @Size(max = 50)
    private String phone;

//    @Size(max = 20)
    private String representative;

//    @Size(max = 500)
    private String homepage;

//    @Size(max = 2000)
    private String introduce;

    @OneToMany(mappedBy = "organization")
    List<Program> programs = new ArrayList<>();
}
