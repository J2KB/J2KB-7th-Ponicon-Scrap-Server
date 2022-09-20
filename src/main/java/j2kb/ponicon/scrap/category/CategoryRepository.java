package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // UserId로 카테고리 리스트 조회하기
    public List<Category> findByUserId(Long userId);
    // 카테고리별 속한 링크 개수 구하기(해당 유저id의 카테고리id의 링크 수)
    //public Long countByUserIdAndCategoryId(Long userId, Long categoryId);

}
