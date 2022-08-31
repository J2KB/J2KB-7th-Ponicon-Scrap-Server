package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
