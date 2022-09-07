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
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    // 아이디
    @Column(length = 16, nullable = false, unique = true)
    private String username;

    // 비밀번호
    @Column(length = 300, nullable = false)
    private String password;

    // 이름
    @Column(length = 70, nullable = false)
    private String name;

    @CreationTimestamp
    private LocalDateTime createdAt;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Link> links = new ArrayList<>();


    // 생성 메소드
    public User(String username, String pw, String name){
        this.username = username;
        this.password = pw;
        this.name = name;
    }

}
