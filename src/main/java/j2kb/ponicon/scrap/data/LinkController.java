package j2kb.ponicon.scrap.data;

import j2kb.ponicon.scrap.data.dto.PostUrlReq;
import j2kb.ponicon.scrap.domain.Link;
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

//    @PostMapping("/data")
//    public ResponseDto<Integer> dataSave(String url, PostDataSaveReq postDataSaveReq) {
        //String link = url;
        //userDto.setId(1L);
        //categoryDto.setId(1L);
//        linkService.save(getOpenGraph("https://recordsoflife.tistory.com/30"), userDto, categoryDto);
//        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바 오브젝트를 JSON으로 변환하여 전송
//    }

    @PostMapping()
    public void dataSave(@RequestBody PostUrlReq postUrlReq, @RequestParam Long userId) {
        try {
            linkService.linkSave(postUrlReq, userId);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping()
    public List<Link> dataListByUserAndCategory(@RequestParam("id") Long userId, @RequestParam("category") Long categoryId) {
        return linkService.links(userId, categoryId);
    }
}
