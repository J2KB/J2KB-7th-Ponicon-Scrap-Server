package j2kb.ponicon.scrap.data;

import j2kb.ponicon.scrap.domain.Category;
import j2kb.ponicon.scrap.domain.Link;
import j2kb.ponicon.scrap.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    public List<Link> findByUserAndCategory(User user, Category category);
}
