package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.domain.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // UserId로 카테고리 리스트 조회하기
    List<Category> findByUserId(Long userId, Sort order);

    // 카테고리 id로 삭제하기
    Long deleteAllById(Long categoryId);
}
