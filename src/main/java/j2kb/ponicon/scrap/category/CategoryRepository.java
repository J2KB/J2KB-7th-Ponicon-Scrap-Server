package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.domain.Category;
import j2kb.ponicon.scrap.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    public List<Category> findByUser(User user);
}
