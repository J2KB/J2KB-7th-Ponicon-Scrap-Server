package j2kb.ponicon.scrap.mypage;

import j2kb.ponicon.scrap.category.dto.GetCategoryListRes;
import j2kb.ponicon.scrap.mypage.dto.GetUserRes;
import j2kb.ponicon.scrap.response.BaseResponse;
import j2kb.ponicon.scrap.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;
    /**
     * 카테고리 조회 API
     * UserId를 RequestParam으로 받아서 myPageService.userInfo 후 마이페이지를 조회하는 역할을 합니다.
     * [GET] /user/mypage?id=
     * @param userId
     * @author 박현성
     */
    @GetMapping("/mypage")
    public BaseResponse<?> myPageByUser(@RequestParam("id") Long userId) {
        GetUserRes user = myPageService.userInfo(userId);
        return new BaseResponse<>(user);
    }
}
