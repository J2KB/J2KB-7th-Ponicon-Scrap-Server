package j2kb.ponicon.scrap.mypage;

import j2kb.ponicon.scrap.mypage.dto.GetUserRes;

public interface MyPageService {
    // 마이페이지 정보 조회
    public GetUserRes userInfo(Long userId);
}
