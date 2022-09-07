package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public List<Category> findByUserId(Long userId);

}
