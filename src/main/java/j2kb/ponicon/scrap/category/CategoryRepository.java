package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // UserId로 카테고리 가져오기
    public List<Category> findByUserId(Long userId);

}
