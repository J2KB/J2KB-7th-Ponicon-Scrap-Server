package j2kb.ponicon.scrap.mypage;

import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.mypage.dto.GetUserRes;
import j2kb.ponicon.scrap.response.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static j2kb.ponicon.scrap.response.BaseExceptionStatus.MYPAGE_USER_NOT_FOUND;


@RequiredArgsConstructor
@Service
public class MyPageService {

    private final MyPageRepository myPageRepository;

    @Transactional(readOnly = true)
    public GetUserRes userInfo(Long userId) {
        User entity = myPageRepository.findById(userId).orElseThrow(() -> new BaseException(MYPAGE_USER_NOT_FOUND));
        return new GetUserRes(entity);
    }
}
