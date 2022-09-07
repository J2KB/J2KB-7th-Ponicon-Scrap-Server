package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.category.dto.PostCategoryReq;
import j2kb.ponicon.scrap.domain.Category;
import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    @Transactional
    public void categorySave(PostCategoryReq postCategoryReq, Long userId) {
        Optional<User> tempUser = userRepository.findById(userId);
        User user = tempUser.get();
        String name = postCategoryReq.getName();
        int order = 1;
        Category category = new Category(name, order, user);
        categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public List<Category> categories(Long userId) {
        return  categoryRepository.findByUserId(userId);
    }
}
