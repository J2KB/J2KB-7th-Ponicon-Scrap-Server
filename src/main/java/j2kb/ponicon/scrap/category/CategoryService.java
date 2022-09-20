package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.category.dto.PostCategoryReq;
import j2kb.ponicon.scrap.domain.Category;
import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        int order = user.getCategories().size();
        Category category = new Category(name, order, user);
        categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public List<Category> categories(Long userId) {
        return  categoryRepository.findByUserId(userId);
    }

    // 기본 카테고리 저장
    @Transactional
    public void saveBasicCategory(User user){
        List<String> categoryNames = new ArrayList<>(List.of("분류되지 않은 자료"));

        for(int i=0; i<categoryNames.size(); i++){
            new Category(categoryNames.get(i), i, user);
            //따로 카테고리 저장 안하더라도 Cascade 설정 해둬서 자동으로 insert 됨.
        }
    }
}
