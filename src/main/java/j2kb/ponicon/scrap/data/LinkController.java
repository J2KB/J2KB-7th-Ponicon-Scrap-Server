package j2kb.ponicon.scrap.data;

import j2kb.ponicon.scrap.data.dto.DataListRes;
import j2kb.ponicon.scrap.data.dto.GetDataListRes;
import j2kb.ponicon.scrap.data.dto.PostUrlReq;
import j2kb.ponicon.scrap.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/data")
public class LinkController {

    private final LinkService linkService;

    @PostMapping()
    public BaseResponse dataSave(@RequestBody PostUrlReq postUrlReq, @RequestParam("id") Long userId) throws Exception {
        linkService.linkSave(postUrlReq, userId);
        return new BaseResponse("링크를 생성하였습니다.");
    }

    @GetMapping()
    public BaseResponse<?> dataListByUserAndCategory(@RequestParam("id") Long userId, @RequestParam("category") Long categoryId) {
        GetDataListRes list = linkService.links(userId, categoryId);
        return new BaseResponse<>(list);
    }
}
