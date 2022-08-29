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

    @Column(length = 16, nullable = false)
    private String username;

    @Column(length = 300, nullable = false)
    private String password;

    @Column(length = 70, nullable = false)
    private String name;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
