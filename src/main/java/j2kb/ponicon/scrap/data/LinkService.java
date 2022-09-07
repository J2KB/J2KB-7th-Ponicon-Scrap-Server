package j2kb.ponicon.scrap.data;

import j2kb.ponicon.scrap.category.CategoryRepository;
import j2kb.ponicon.scrap.data.dto.PostDataSaveReq;
import j2kb.ponicon.scrap.data.dto.PostUrlReq;
import j2kb.ponicon.scrap.data.lib.OpenGraph;
import j2kb.ponicon.scrap.domain.Category;
import j2kb.ponicon.scrap.domain.Link;
import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LinkService {

    private final LinkRepository linkRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    @Transactional
    public void linkSave(PostUrlReq postUrlReq, Long userId) throws Exception {
        String baseURL = postUrlReq.getBaseURL();
        PostDataSaveReq postDataSaveReq = getOpenGraph(baseURL);

        Optional<User> tempUser = userRepository.findById(userId);
        User user = tempUser.get();

        Optional<Category> tempCategory = categoryRepository.findById(12L);
        Category category = tempCategory.get();

        String link = postDataSaveReq.getLink();
        String title = postDataSaveReq.getTitle();
        String imgUrl = postDataSaveReq.getImgUrl();

        Link links = new Link(link, title, imgUrl, category, user);
        linkRepository.save(links);
    }

    @Transactional(readOnly = true)
    public List<Link> links(Long userId, Long categoryId) {
        return  linkRepository.findByUserIdAndCategoryId(userId, categoryId);
    }

    private PostDataSaveReq getOpenGraph(String baseURL) throws Exception {
        PostDataSaveReq postDataSaveReq = null;

        OpenGraph page = new OpenGraph(baseURL, true);

        postDataSaveReq = new PostDataSaveReq();
        postDataSaveReq.setTitle(getContent(page, "title"));
        postDataSaveReq.setImgUrl(getContent(page, "image"));
        postDataSaveReq.setLink(getContent(page, "url"));
        log.info(String.valueOf(postDataSaveReq));

        return postDataSaveReq;
    }

    private String getContent(OpenGraph page, String propertyName) {
        log.info("page={}", String.valueOf(page));
        log.info("propertyName={}", propertyName);
        return page.getContent(propertyName);
    }
}
