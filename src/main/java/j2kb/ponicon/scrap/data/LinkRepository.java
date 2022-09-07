package j2kb.ponicon.scrap.data;

import j2kb.ponicon.scrap.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, Long> {
    public List<Link> findByUserIdAndCategoryId(Long userId, Long categoryId);
}
