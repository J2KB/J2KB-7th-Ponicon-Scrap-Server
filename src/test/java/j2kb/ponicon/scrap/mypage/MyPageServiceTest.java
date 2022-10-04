package j2kb.ponicon.scrap.mypage;

import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.mypage.dto.GetUserRes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // SpringContainer를 로드하지않고 테스트를 위한 기능만 제공한다.(가짜 메모리환경 만들기)
class MyPageServiceTest {

    @InjectMocks
    private MyPageServiceImpl myPageService;

    @Mock
    private MyPageRepository myPageRepository;

    @Test
    @DisplayName("userInfo기능이 제대로 동작하는지 확인")
    void userInfo() {
        //given
        User user = new User("phs", "1234", "phs");
        Long fakeUserId = 1L;
        ReflectionTestUtils.setField(user, "id", fakeUserId);


        //stub(가설)
        when(myPageRepository.findById(fakeUserId)).thenReturn(Optional.of(user));

        //when
        GetUserRes getUserRes = myPageService.userInfo(fakeUserId);

        //then
        assertThat(getUserRes.getUsername()).isEqualTo("phs");
        assertThat(getUserRes.getName()).isEqualTo("phs");
    }
}