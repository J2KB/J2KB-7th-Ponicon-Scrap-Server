package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.category.dto.CategoryListRes;
import j2kb.ponicon.scrap.category.dto.GetCategoryListRes;
import j2kb.ponicon.scrap.category.dto.PostCategoryReq;
import j2kb.ponicon.scrap.domain.Category;
import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.response.BaseException;
import j2kb.ponicon.scrap.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static j2kb.ponicon.scrap.response.BaseExceptionStatus.CATEGORY_NAME_NULL;

@Service
@RequiredArgsConstructor
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
    public GetCategoryListRes categories(Long userId) {
        List<CategoryListRes> list = categoryRepository.findByUserId(userId).stream()
                .map(Category::toDto)
                .collect(Collectors.toList());
        GetCategoryListRes getCategoryListRes = GetCategoryListRes.builder().categories(list).build();
        return getCategoryListRes;
    }
}
