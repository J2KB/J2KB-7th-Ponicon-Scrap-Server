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


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    @Transactional
    public void categorySave(PostCategoryReq postCategoryReq, Long userId) {
        // userRepository에서 userId로 조회 후 user에 UserName을 가져온다.
        Optional<User> tempUser = userRepository.findById(userId);
        User user = tempUser.get();

        // postCategoryReq에서 카테고리 이름을 가져옴
        String name = postCategoryReq.getName();

        // 자동으로 order값 늘려줌
        int order = user.getCategories().size();

        Category category = new Category(name, order, user);
        categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public GetCategoryListRes categories(Long userId) {
        List<CategoryListRes> list = categoryRepository.findByUserId(userId).stream() // categoryRepository에서 넘어온 결과를
                .map(Category::toDto)          // Stream을 통해 map으로 toDto에 매핑 해준다.
                .collect(Collectors.toList()); // collect를 사용해서 List로 변환한다.
        // list를 builder 패턴으로 객체 생성
        GetCategoryListRes getCategoryListRes = GetCategoryListRes.builder().categories(list).build();
        return getCategoryListRes;
    }
}
