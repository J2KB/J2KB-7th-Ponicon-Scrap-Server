package j2kb.ponicon.scrap.mypage;

import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.mypage.dto.GetUserRes;
import j2kb.ponicon.scrap.response.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static j2kb.ponicon.scrap.response.BaseExceptionStatus.DUPULICATE_EMAIL;
import static j2kb.ponicon.scrap.response.BaseExceptionStatus.MYPAGE_USER_NOT_FOUND;


@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class MyPageServiceImpl implements MyPageService{

    private final MyPageRepository myPageRepository;

    // 마이페이지 정보 조회
    public GetUserRes userInfo(Long userId) {
        User entity = myPageRepository.findById(userId).orElseThrow(() -> {
            log.info("마이페이지 조회 중 에러: {}, userId={}", MYPAGE_USER_NOT_FOUND.getMessage(), userId);
            return new BaseException(MYPAGE_USER_NOT_FOUND);
        });
        return new GetUserRes(entity);
    }
}
