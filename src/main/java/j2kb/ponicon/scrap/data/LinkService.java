package j2kb.ponicon.scrap.data;

import j2kb.ponicon.scrap.category.CategoryRepository;
import j2kb.ponicon.scrap.data.dto.*;
import j2kb.ponicon.scrap.data.lib.OpenGraph;
import j2kb.ponicon.scrap.domain.Category;
import j2kb.ponicon.scrap.domain.Link;
import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LinkService {

    private final LinkRepository linkRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    @Transactional
    public PostDataSaveRes linkSave(PostUrlReq postUrlReq, Long userId, Long categoryId) throws Exception {
        // URL을 postUrlReq 가져온다.
        String baseURL = postUrlReq.getBaseURL();

        // getOpenGraph에 URL 넘겨 PostDataSaveReq을 담는다.
        PostDataSaveReq postDataSaveReq = getOpenGraph(baseURL);

        Optional<User> tempUser = userRepository.findById(userId);
        User user = tempUser.get();

        Optional<Category> tempCategory = categoryRepository.findById(categoryId);
        Category category = tempCategory.get();

        String link = postDataSaveReq.getLink();
        String title = postDataSaveReq.getTitle();
        String imgUrl = postDataSaveReq.getImgUrl();

        Link linkSave = new Link(link, title, imgUrl, category, user);
        linkRepository.save(linkSave);
        PostDataSaveRes postDataSaveRes = PostDataSaveRes.builder().linkId(linkSave.getId()).build();
        return postDataSaveRes;
    }

    @Transactional(readOnly = true)
    public GetDataListRes links(Long userId, Long categoryId, String seq) {
        if(seq.equals("desc")) {
            List<DataListRes> list = linkRepository.findByUserIdAndCategoryId(userId, categoryId, Sort.by(Sort.Direction.DESC, "createdAt")).stream() // createdAt 기준으로 sort(desc) linkRepository에서 넘어온 결과를
                    .map(Link::toDto) // Stream을 통해 map으로 toDto에 매핑 해준다.
                    .collect(Collectors.toList()); // collect를 사용해서 List로 변환한다.
            // list를 builder 패턴으로 객체 생성
            GetDataListRes getDataListRes = GetDataListRes.builder().links(list).build();
            return getDataListRes;
        }
        else {
            List<DataListRes> list = linkRepository.findByUserIdAndCategoryId(userId, categoryId, Sort.by(Sort.Direction.ASC, "createdAt")).stream() // createdAt 기준으로 sort(asc) linkRepository에서 넘어온 결과를
                    .map(Link::toDto) // Stream을 통해 map으로 toDto에 매핑 해준다.
                    .collect(Collectors.toList()); // collect를 사용해서 List로 변환한다.
            // list를 builder 패턴으로 객체 생성
            GetDataListRes getDataListRes = GetDataListRes.builder().links(list).build();
            return getDataListRes;
        }
    }
    // 라이브러리 메소드
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
    // 라이브러리 메소드
    private String getContent(OpenGraph page, String propertyName) {
        log.info("page={}", String.valueOf(page));
        log.info("propertyName={}", propertyName);
        return page.getContent(propertyName);
    }
}
