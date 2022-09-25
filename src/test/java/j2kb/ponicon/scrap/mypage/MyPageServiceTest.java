package j2kb.ponicon.scrap.mypage;

import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.mypage.dto.GetUserRes;
import j2kb.ponicon.scrap.response.BaseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static j2kb.ponicon.scrap.response.BaseExceptionStatus.MYPAGE_USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // 가짜 메모리환경 만들기
class MyPageServiceTest {

    @InjectMocks
    private MyPageService myPageService;

    @Mock
    private MyPageRepository myPageRepository;

    @Test
    void 유저정보테스트() {
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