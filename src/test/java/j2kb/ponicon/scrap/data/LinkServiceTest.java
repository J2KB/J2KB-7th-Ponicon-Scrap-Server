package j2kb.ponicon.scrap.data;

import j2kb.ponicon.scrap.category.CategoryRepository;
import j2kb.ponicon.scrap.data.dto.GetDataListRes;
import j2kb.ponicon.scrap.data.dto.PostDataSaveReq;
import j2kb.ponicon.scrap.data.dto.PostDataSaveRes;
import j2kb.ponicon.scrap.data.dto.PostUrlReq;
import j2kb.ponicon.scrap.domain.Category;
import j2kb.ponicon.scrap.domain.Link;
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
class LinkServiceTest {

    @InjectMocks
    private LinkServiceImpl linkService;

    @Mock
    private LinkRepository linkRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @Test
//    @DisplayName("linkSave기능이 제대로 동작하는지 확인")
    void linkSave() throws Exception {

        //given
        //PostUrlReq postUrlReq = new PostUrlReq("https://okky.kr/");

//        PostDataSaveReq postDataSaveReq = new PostDataSaveReq();
//        postDataSaveReq.setLink("https://okky.kr/");
//
//        Optional<User> tempUser = Optional.of(new User("phs", "1234", "phs"));
//        User user = tempUser.get();
//        Long fakeUserId = 1L;
//        ReflectionTestUtils.setField(user, "id", fakeUserId);
//
//        int order = 5;
//        Optional<Category> tempCategory = Optional.of(new Category("카테고리 생상", order, user));
//        Category category = tempCategory.get();
//        Long fakeCategoryId = 1L;
//        ReflectionTestUtils.setField(category, "id", fakeCategoryId);
//
//        Link link = new Link(postDataSaveReq.getLink(), "네이버", "www.naver.com", category, user, "naver.com");
//        Long fakeLinkId = 1L;         // Id 생성 전략을 Identity를 사용하므로, 실제 DBd에 저장되야만 Id가 생성된다. 따라서 테스트에서 Id를 검증할 수 없다.
//        ReflectionTestUtils.setField(link, "id", fakeLinkId);
//
//        //stub(가설)
//        when(userRepository.findById(fakeUserId)).thenReturn(Optional.of(user));
//        when(categoryRepository.findById(fakeCategoryId)).thenReturn(Optional.of(category));
//        when(linkRepository.save(any())).thenReturn(link);
//
//        //when
//        PostDataSaveRes postDataSaveRes = linkService.linkSave(postDataSaveReq, fakeUserId, fakeCategoryId);
//
//        //then
//        assertThat(postDataSaveRes.getLinkId()).isEqualTo(1L);
    }

    @Test
    void links() {
        //given
        Optional<User> tempUser = Optional.of(new User("phs", "1234", "phs"));
        User user = tempUser.get();
        Long fakeUserId = 1L;
        ReflectionTestUtils.setField(user, "id", fakeUserId);

        int order = 5;
        Optional<Category> tempCategory = Optional.of(new Category("카테고리 생상", order, user));
        Category category = tempCategory.get();
        Long fakeCategoryId = 1L;
        ReflectionTestUtils.setField(category, "id", fakeCategoryId);

        List<Link> dataList = new ArrayList<>();
        dataList.add(new Link("www.naver.com", "네이버", "www.naver.com", category, user,"naver.com"));
        dataList.add(new Link("www.tistory.com", "티스토리", "www.tistory.com", category, user,"tistory.com"));
        Sort sort = Sort.by("createdAt").ascending();

        //stub
        when(linkRepository.findByUserIdAndCategoryId(fakeUserId, fakeCategoryId)).thenReturn(dataList);

        //when
        GetDataListRes getDataListRes = linkService.links(fakeUserId, fakeCategoryId);

        //then
        assertThat(getDataListRes.getLinks().get(0).getLink()).isEqualTo("www.naver.com");
        assertThat(getDataListRes.getLinks().get(1).getLink()).isEqualTo("www.tistory.com");
    }
}