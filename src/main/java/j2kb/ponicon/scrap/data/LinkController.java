package j2kb.ponicon.scrap.data;

import j2kb.ponicon.scrap.data.lib.OpenGraph;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @PostMapping("/data")
    public ResponseDto<Integer> dataSave(String url) {
        //LinkDto link = url;
        linkService.save(getOpenGraph("https://recordsoflife.tistory.com/30"));
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바 오브젝트를 JSON으로 변환하여 전송
    }

    public LinkDto getOpenGraph(String baseURL) {
        LinkDto ogVO = null;
        try {
            OpenGraph page = new OpenGraph(baseURL, true);
            //MetaElement[] meta = page.getProperties();
            ogVO = new LinkDto();
            ogVO.setTitle(getContent(page, "title"));
            ogVO.setImgUrl(getContent(page, "image"));
            ogVO.setLink(getContent(page, "url"));
            log.info(String.valueOf(ogVO));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ogVO;
    }

    private String getContent(OpenGraph page, String propertyName) {
        try {
            log.info("page={}", String.valueOf(page));
            log.info("propertyName={}", propertyName);
            return page.getContent(propertyName);
        } catch (NullPointerException e) {
            return "태그 없음";
        }
    }
}
