package j2kb.ponicon.scrap.data;

import j2kb.ponicon.scrap.category.CategoryRepository;
import j2kb.ponicon.scrap.category.CategoryService;
import j2kb.ponicon.scrap.data.dto.*;
import j2kb.ponicon.scrap.data.lib.OpenGraph;
import j2kb.ponicon.scrap.domain.Category;
import j2kb.ponicon.scrap.domain.Link;
import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.response.BaseException;
import j2kb.ponicon.scrap.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static j2kb.ponicon.scrap.response.BaseExceptionStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class LinkServiceImpl implements LinkService {

    private final LinkRepository linkRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    private final CategoryService categoryService;

    @Transactional
    public PostDataSaveRes linkSave(PostDataSaveReq postDataSaveReq, Long userId, Long categoryId) throws Exception {
        // URL을 postUrlReq 가져온다.
        //String baseURL = postUrlReq.getBaseURL();

        //String baseURL = postDataSaveReq.getLink();

        // getOpenGraph에 URL 넘겨 PostDataSaveReq을 담는다.

        // 확인용
        //log.info("baseURL ={} ", baseURL);
        log.info("postDataSaveReq.getLink() ={} ", postDataSaveReq.getLink());
        log.info("postDataSaveReq.getImgUrl() ={} ", postDataSaveReq.getImgUrl());

        Optional<User> tempUser = userRepository.findById(userId);
        User user = tempUser.get();

        Optional<Category> tempCategory = categoryRepository.findById(categoryId);
        Category category = tempCategory.get();
        String link = postDataSaveReq.getLink();
        String title = postDataSaveReq.getTitle();
        String imgUrl = postDataSaveReq.getImgUrl();
        String domain = SearchDomain(link);

        if(title.isEmpty() || imgUrl.isEmpty()) {
            imgUrl = getOpenGraph(link).getImgUrl();
            title = getOpenGraph(link).getTitle();
        }

        Link linkSave = new Link(link, title, imgUrl, category, user, domain);
        Link saveLink = linkRepository.save(linkSave);
        PostDataSaveRes postDataSaveRes = PostDataSaveRes.builder().linkId(saveLink.getId()).build();
        return postDataSaveRes;
    }
    // 도메인을 뽑아내는 메소드
    private String SearchDomain(String url) throws MalformedURLException {
        if(url.contains("tistory")) {
            return "tistory.com";
        }
        else {
            if(!url.startsWith("http") && !url.startsWith("https")){
                url = "http://" + url;
            }
            URL netUrl = new URL(url);
            String host = netUrl.getHost();

            if(host.startsWith("www")){
                host = host.substring("www".length()+1);
                return host;
            }
            else {
                return host;
            }
        }
    }
    @Transactional(readOnly = true)
    public GetDataListRes links(Long userId, Long categoryId) {
    
        List<DataListRes> list = linkRepository.findByUserIdAndCategoryId(userId, categoryId).stream() // linkRepository에서 넘어온 결과를
                .map(Link::toDto) // Stream을 통해 map으로 toDto에 매핑 해준다.
                .collect(Collectors.toList()); // collect를 사용해서 List로 변환한다.
        // list를 builder 패턴으로 객체 생성
        GetDataListRes getDataListRes = GetDataListRes.builder().links(list).build();
        return getDataListRes;
    }

    @Transactional(readOnly = true)
    public GetDataListRes allLinks(Long userId) {

        List<DataListRes> list = linkRepository.findByUserId(userId).stream() // linkRepository에서 넘어온 결과를
                    .map(Link::toDto) // Stream을 통해 map으로 toDto에 매핑 해준다.
                    .collect(Collectors.toList()); // collect를 사용해서 List로 변환한다.
        // list를 builder 패턴으로 객체 생성
        GetDataListRes getDataListRes = GetDataListRes.builder().links(list).build();
        return getDataListRes;
    }

    // 라이브러리 메소드(프론트에서 처리하므로 현재 사용안함)
    private PostDataSaveReq getOpenGraph(String baseURL) throws Exception {
        PostDataSaveReq postDataSaveReq = null;

        OpenGraph page = new OpenGraph(baseURL, true);

        postDataSaveReq = new PostDataSaveReq();
        postDataSaveReq.setTitle(getContent(page, "title"));
        postDataSaveReq.setImgUrl(getContent(page, "image"));

        if(getContent(page, "url") == null) {
            postDataSaveReq.setLink(baseURL);
        }
        else {
            postDataSaveReq.setLink(getContent(page, "url"));
        }
        log.info(String.valueOf(postDataSaveReq));

        return postDataSaveReq;
    }
    // 라이브러리 메소드
    private String getContent(OpenGraph page, String propertyName) {
        log.info("page={}", String.valueOf(page));
        log.info("propertyName={}", propertyName);
        return page.getContent(propertyName);
    }

    // 자료 삭제
    @Transactional
    public void deleteLink(Long userId, Long linkId){
        Link link = findLinkOne(linkId);

        // 해당 유저가 만든 자료가 아니라면
        if(!link.checkLinkAndUserCorrect(userId)){
            throw new BaseException(LINK_AND_USER_NOT_CORRECT);
        }

        linkRepository.delete(link);
    }

    // 자료 수정 - 자료의 카테고리 변경
    @Transactional
    public PatchLinkRes updateLink(Long userId, Long linkId, PatchLinkReq patchLinkReq){

        Link link = findLinkOne(linkId);
        // 해당 유저가 만든 자료가 아니라면
        if(!link.checkLinkAndUserCorrect(userId)){
            log.info("자료 수정중 에러: {}, user={}, link={}", LINK_AND_USER_NOT_CORRECT.getMessage(), userId, linkId);
            throw new BaseException(LINK_AND_USER_NOT_CORRECT);
        }

        // 변경될 카테고리
        Category changedCategory = categoryService.findCategoryOne(patchLinkReq.getCategoryId());
        // 해당 유저가 만든 카테고리가 아니라면
        if(!changedCategory.checkCategoryAndUserCorrect(userId)){
            log.info("자료 수정중 에러: {}, user={}, link={}", CATEGORY_AND_USER_NOT_CORRECT.getMessage(), userId, linkId);
            throw new BaseException(CATEGORY_AND_USER_NOT_CORRECT);
        }

        // 자료의 카테고리 변경
        link.setCategory(changedCategory);

        linkRepository.save(link);

        PatchLinkRes patchLinkRes = PatchLinkRes.builder()
                .linkId(link.getId())
                .categoryId(link.getCategory().getId())
                .url(link.getLink())
                .title(link.getTitle())
                .imgUrl(link.getImgUrl())
                .domain(link.getDomain())
                .build();

        return patchLinkRes;
    }

    // 자료 찾기
    @Transactional(readOnly = true)
    public Link findLinkOne(Long linkId){
        Optional<Link> optLink = linkRepository.findById(linkId);
        // 해당 하는 자료가 없으면 에러 발생시키기.
        if(optLink.isEmpty()){
            log.info("에러: {}", LINK_NOT_EXIST.getMessage());
        }
        return optLink.orElseThrow(() -> new BaseException(LINK_NOT_EXIST));
    }
}
