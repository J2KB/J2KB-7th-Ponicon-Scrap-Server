package j2kb.ponicon.scrap.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import j2kb.ponicon.scrap.data.dto.DataListRes;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Link {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id")
    private Long id;

    // 자료의 링크
    @Column(length = 300, nullable = false)
    private String link;

    // 자료의 제목
    @Column(length = 300)
    private String title;

    // 자료의 이미지 url
    @Column(length = 300)
    private String imgUrl;

    // 자료의 도메인
    @Column(length = 30)
    private String domain;
    // 즐겨찾기
    private boolean star = false;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // 유저
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 카테고리
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // 생성 메소드
    public Link(String link, String title, String imgUrl, Category category, User user, String domain){
        this.link = link;
        this.title = title;
        this.imgUrl = imgUrl;
        this.domain = domain;
        this.setCategory(category);
        this.setUser(user);
    }

    public void setCategory(Category category){
        this.category = category;
        category.getLinks().add(this);
    }

    public void setUser(User user){
        this.user = user;
        user.getLinks().add(this);
    }
    /* Entity를 Dto로 변환하는 메소드 */
    public DataListRes toDto(){
        return DataListRes.builder()
                .linkId(id)
                .link(link)
                .title(title)
                .domain(domain)
                .imgUrl(imgUrl)
                .build();
    }

    // 링크id와 유저id가 일치하는지
    public boolean checkLinkAndUserCorrect(Long userId){
        User user = this.user;

        if(user.getId().equals(userId)){
            return true;
        }
        else {
            return false;
        }
    }
    /* 카테고리 업데이트하는 메소드 */
    public void putLink(String title){
        this.title = title;
    }
}
