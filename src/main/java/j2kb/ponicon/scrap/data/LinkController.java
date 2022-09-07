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

    @PostMapping()
    public void dataSave(@RequestBody PostUrlReq postUrlReq, @RequestParam("id") Long userId) {
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
