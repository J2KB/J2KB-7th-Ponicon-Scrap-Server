package j2kb.ponicon.scrap.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 해당 카테고리에 속한 자료들
    @OneToMany(mappedBy = "category")
    private List<Link> links = new ArrayList<>();

    // 생성 메소드
    public Category(String name, int order, User user){
        this.name = name;
        this.order = order;
        this.setUser(user);
    }

    /* 연관 편의 메소드 */
    public void setUser(User user){
        this.user = user;
        user.getCategories().add(this);
    }
}
