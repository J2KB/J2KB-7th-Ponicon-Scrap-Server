package j2kb.ponicon.scrap.data;

import j2kb.ponicon.scrap.domain.Link;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {
    // UserId와 CategoryId로 해당 링크 조회
    public List<Link> findByUserIdAndCategoryId(Long userId, Long categoryId);
    // CategoryId와 UserId로 해당 카테고리의 속한 링크 갯수 구하기
    public int countByCategoryIdAndUserId(Long categoryId, Long userId);

    // 모든 자료 갯수 구하기 위해 유저 id로 해당하는 카테고리 링크 전체 갯수 구하기
    public int countByUserId(Long userId);

    // userId로 해당하는 링크 리스트 조회
    public List<Link> findByUserId(Long userId);
}
