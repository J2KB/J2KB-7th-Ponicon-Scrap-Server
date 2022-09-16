package j2kb.ponicon.scrap.mypage;

import j2kb.ponicon.scrap.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPageRepository extends JpaRepository<User, Long> {

}
