package j2kb.ponicon.scrap.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
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
}
