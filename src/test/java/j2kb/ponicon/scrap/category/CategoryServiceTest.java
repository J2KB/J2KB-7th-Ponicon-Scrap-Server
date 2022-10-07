package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.category.dto.GetCategoryListRes;
import j2kb.ponicon.scrap.category.dto.PostCategorySaveReq;
import j2kb.ponicon.scrap.category.dto.PostCategorySaveRes;
import j2kb.ponicon.scrap.data.LinkRepository;
import j2kb.ponicon.scrap.domain.Category;
import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // 가짜 메모리환경 만들기
class CategoryServiceTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private LinkRepository linkRepository;

    @Mock
    private UserRepository userRepository;


    @Test
//    @DisplayName("categorySave기능이 제대로 동작하는지 확인")
    void categorySave() {
        //given
        PostCategorySaveReq dto = new PostCategorySaveReq("카테고리 생성 테스트");

        Optional<User> tempUser = Optional.of(new User("phs", "1234", "phs"));
        User user = tempUser.get();

        Long fakeUserId = 1L;
        ReflectionTestUtils.setField(user, "id", fakeUserId);

        int order = 5;
        Category category = new Category(dto.getName(), order, user);

        Long fakeCategoryId = 1L;
        ReflectionTestUtils.setField(category, "id", fakeCategoryId);

        //stub(가설)
        when(userRepository.findById(fakeUserId)).thenReturn(Optional.of(user));
        when(categoryRepository.save(any())).thenReturn(category);

        //when
        PostCategorySaveRes saveCategory = categoryService.categorySave(dto, fakeUserId);

        //then
        assertThat(saveCategory.getCategoryId()).isEqualTo(1L);
    }

    @Test
//    @DisplayName("categories 기능 테스트")
    void categories() {
        //given
        Optional<User> tempUser = Optional.of(new User("phs", "1234", "phs"));
        User user = tempUser.get();
        Long fakeUserId = 1L;
        ReflectionTestUtils.setField(user, "id", fakeUserId);

        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Junit 강의", 2, user));
        categories.add(new Category("lusida", 1, user));
        Sort sort = Sort.by("order").ascending();

        //stub
        when(categoryRepository.findByUserId(fakeUserId, sort.ascending())).thenReturn(categories);

        //when
        GetCategoryListRes getCategoryListRes = categoryService.categories(fakeUserId);

        //then
        assertThat(getCategoryListRes.getCategories().get(0).getName()).isEqualTo("Junit 강의");
        assertThat(getCategoryListRes.getCategories().get(1).getName()).isEqualTo("lusida");
        // sort를 진행하여도 test가 통과하는 이유는 sort를 진행해도 index 값이 바뀌지 않아서 통과하는 것 같다...?
    }
}