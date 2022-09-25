package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.category.dto.PostCategorySaveReq;
import j2kb.ponicon.scrap.category.dto.PostCategorySaveRes;
import j2kb.ponicon.scrap.domain.Category;
import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // 가짜 메모리환경 만들기
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void 카테고리저장테스트() {
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
    void categories() {
    }
}