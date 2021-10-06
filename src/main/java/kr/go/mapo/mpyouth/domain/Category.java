package kr.go.mapo.mpyouth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Category extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ColumnDefault("1")
    @Column(nullable = false)
    private Integer level = 1;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parent;

    @JsonIgnore
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> child = new ArrayList<>();

    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
        child.setLevel(this.getLevel() + 1);
    }

    @Builder
    public Category(Long id, String name, Integer level) {
        this.id = id;
        this.name = name;
        if(level != null)
            this.level = level;
    }
}
