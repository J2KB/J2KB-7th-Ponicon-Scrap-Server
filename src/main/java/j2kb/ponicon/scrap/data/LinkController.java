package j2kb.ponicon.scrap.data;

import j2kb.ponicon.scrap.category.CategoryDto;
import j2kb.ponicon.scrap.category.UserDto;
import j2kb.ponicon.scrap.data.lib.OpenGraph;
import j2kb.ponicon.scrap.domain.Link;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @PostMapping("/data")
    public ResponseDto<Integer> dataSave(String url, UserDto userDto, CategoryDto categoryDto) {
        //String link = url;
        //userDto.setId(1L);
        //categoryDto.setId(1L);
        linkService.save(getOpenGraph("https://recordsoflife.tistory.com/30"), userDto, categoryDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바 오브젝트를 JSON으로 변환하여 전송
    }
    // 1. 일단 로그인한 user의 user.getId를 받아온다.
    // 2. 그 유저의 카테고리 id를 받아와서 카테고리 id로 묶여있는 link들을 가져온다.
    // 3. 자료 순서를 정할 seq도 받는다.
    @GetMapping("/datalist")
    public List<Link> dataListByUserAndCategoryAndSeq(UserDto userDto, @RequestParam Long id) {
        userDto.setId(2L);
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(id);
        return linkService.links(userDto, categoryDto);
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
