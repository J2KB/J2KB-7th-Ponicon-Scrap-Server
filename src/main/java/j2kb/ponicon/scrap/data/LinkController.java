package j2kb.ponicon.scrap.data;

import j2kb.ponicon.scrap.data.dto.GetDataListRes;
import j2kb.ponicon.scrap.data.dto.PostUrlReq;
import j2kb.ponicon.scrap.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/data")
public class LinkController {

    private final LinkService linkService;

    /**
     * 링크 등록 API
     * UserId와 categoryId를 RequestParam으로 받아서 linkService.linkSave 후 링크를 생성하는 역할을 합니다.
     * @valid 어노테이션으로 PostUrlReq의 validation check를 진행합니다.
     * @ exception : UnknownHostException, MalformedURLException
     * [POST] /data?id=&category=
     * @param postUrlReq, userId, categoryId
     * @author 박현성
     */
    @PostMapping()
    public BaseResponse dataSave(@RequestBody @Valid PostUrlReq postUrlReq, @RequestParam("id") Long userId, @RequestParam("category") Long categoryId) throws Exception {
        linkService.linkSave(postUrlReq, userId, categoryId);
        return new BaseResponse("링크를 생성하였습니다.");
    }
    /**
     * 링크 조회 API
     * UserId와 categoryId를 RequestParam으로 받아서 linkService.links 후 링크를 조회하는 역할을 합니다.
     * [GET] /category?id=&category=
     * @param userId, categoryId
     * @author 박현성
     */
    @GetMapping()
    public BaseResponse<?> dataListByUserAndCategory(@RequestParam("id") Long userId, @RequestParam("category") Long categoryId) {
        GetDataListRes list = linkService.links(userId, categoryId);
        return new BaseResponse<>(list);
    }
}
