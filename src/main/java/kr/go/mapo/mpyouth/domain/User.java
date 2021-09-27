package kr.go.mapo.mpyouth.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "adminLoginId"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String adminLoginId;

    @NotBlank
    @Size(max = 10)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @OneToOne(mappedBy = "user")
    private AuthEmail authEmail;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))


    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "oraganization_fk")
    private Organization organization;

    @Builder
    public User(String adminLoginId, String email, String username, String password) {
        this.adminLoginId = adminLoginId;
        this.email = email;
        this.username=username;
        this.password = password;
    }


    public void setPassword(String password) {
        this.password = password;
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
