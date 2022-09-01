package j2kb.ponicon.scrap.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    // 카테고리 이름
    @Column(length = 60, nullable = false)
    private String name;

    // 카테고리 순서
    @Column(name = "category_order", nullable = false)
    private int order;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // 유저
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 해당 카테고리에 속한 자료들
    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Link> links = new ArrayList<>();

    @Builder
    public Category(Long id, String name, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }
}
