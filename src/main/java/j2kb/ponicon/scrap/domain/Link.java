package j2kb.ponicon.scrap.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Link {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id")
    private Long id;

    // 자료의 링크
    @Column(length = 300, nullable = false)
    private String link;

    // 자료의 제목
    @Column(length = 100)
    private String title;

    // 자료의 이미지 url
    @Column(length = 300)
    private String imgUrl;

    // 즐겨찾기
    private boolean star = false;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 카테고리
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    //
    @Builder
    public Link(String title, String link, String imgUrl, User user, Category category) {
        this.title = title;
        this.link = link;
        this.imgUrl = imgUrl;
        this.user = user;
        this.category = category;
    }
}
