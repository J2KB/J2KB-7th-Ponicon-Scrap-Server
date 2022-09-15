package j2kb.ponicon.scrap.data;

import j2kb.ponicon.scrap.data.dto.GetDataListRes;
import j2kb.ponicon.scrap.data.dto.PostUrlReq;
import j2kb.ponicon.scrap.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/data")
public class LinkController {

    private final LinkService linkService;

    /**
     * 링크 등록 API
     * [POST] /data?id=&category=
     * @param postUrlReq, userId, categoryId
     */
    @PostMapping()
    public BaseResponse dataSave(@RequestBody PostUrlReq postUrlReq, @RequestParam("id") Long userId, @RequestParam("category") Long categoryId) throws Exception {
        linkService.linkSave(postUrlReq, userId, categoryId);
        return new BaseResponse("링크를 생성하였습니다.");
    }
    /**
     * 링크 조회 API
     * [GET] /category?id=&category=
     * @param userId, categoryId
     */
    @GetMapping()
    public BaseResponse<?> dataListByUserAndCategory(@RequestParam("id") Long userId, @RequestParam("category") Long categoryId) {
        GetDataListRes list = linkService.links(userId, categoryId);
        return new BaseResponse<>(list);
    }
}
