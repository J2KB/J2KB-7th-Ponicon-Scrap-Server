package j2kb.ponicon.scrap.mypage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import j2kb.ponicon.scrap.mypage.dto.GetUserRes;
import j2kb.ponicon.scrap.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "마이페이지와 관련된 API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageServiceImpl myPageService;
    /**
     * 카테고리 조회 API
     * UserId를 RequestParam으로 받아서 myPageService.userInfo 후 마이페이지를 조회하는 역할을 합니다.
     * [GET] /user/mypage?id=
     * @param userId
     * @author 박현성
     */
    @ApiOperation(value = "카테고리 조회 API", notes = "UserId를 RequestParam으로 받아서 myPageService.userInfo 후 마이페이지를 조회하는 역할을 합니다. /user/mypage?id=")
    @GetMapping("/mypage")
    public BaseResponse<?> myPageByUser(@ApiParam(value = "User의 id 값", example = "2") @RequestParam("id") Long userId) {
        GetUserRes user = myPageService.userInfo(userId);
        return new BaseResponse<>(user);
    }
}
