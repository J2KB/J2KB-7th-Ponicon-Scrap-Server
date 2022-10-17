package j2kb.ponicon.scrap.data;

import j2kb.ponicon.scrap.category.CategoryRepository;
import j2kb.ponicon.scrap.data.dto.*;
import j2kb.ponicon.scrap.data.lib.OpenGraph;
import j2kb.ponicon.scrap.domain.Category;
import j2kb.ponicon.scrap.domain.Link;
import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.response.BaseException;
import j2kb.ponicon.scrap.response.BaseExceptionStatus;
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

import static j2kb.ponicon.scrap.response.BaseExceptionStatus.LINCK_AND_USER_NOT_CORRECK;
import static j2kb.ponicon.scrap.response.BaseExceptionStatus.LINK_NOT_EXIST;

@Service
@RequiredArgsConstructor
@Slf4j
public class LinkServiceImpl implements LinkService {

    private final LinkRepository linkRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    @Transactional
    public PostDataSaveRes linkSave(PostUrlReq postUrlReq, Long userId, Long categoryId) throws Exception {
        // URL을 postUrlReq 가져온다.
        String baseURL = postUrlReq.getBaseURL();

        // getOpenGraph에 URL 넘겨 PostDataSaveReq을 담는다.
        PostDataSaveReq postDataSaveReq = getOpenGraph(baseURL);

        // 확인용
        System.out.println("baseURL = " + baseURL);
        System.out.println("postDataSaveReq.getLink() = " + postDataSaveReq.getLink());
        System.out.println("postDataSaveReq.getImgUrl() = " + postDataSaveReq.getImgUrl());

        Optional<User> tempUser = userRepository.findById(userId);
        User user = tempUser.get();

        Optional<Category> tempCategory = categoryRepository.findById(categoryId);
        Category category = tempCategory.get();

        String link = postDataSaveReq.getLink();
        String title = postDataSaveReq.getTitle();
        String imgUrl = postDataSaveReq.getImgUrl();
        String domain = SearchDomain(link);
        Link linkSave = new Link(link, title, imgUrl, category, user, domain);
        Link saveLink = linkRepository.save(linkSave);
        PostDataSaveRes postDataSaveRes = PostDataSaveRes.builder().linkId(saveLink.getId()).build();
        return postDataSaveRes;
    }

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
    @Transactional(readOnly = true)
    public GetDataListRes allLinks(Long userId) {
        List<DataListRes> list = linkRepository.findByUserId(userId, Sort.by(Sort.Direction.DESC, "createdAt")).stream() // createdAt 기준으로 sort(desc) linkRepository에서 넘어온 결과를
                    .map(Link::toDto) // Stream을 통해 map으로 toDto에 매핑 해준다.
                    .collect(Collectors.toList()); // collect를 사용해서 List로 변환한다.
        // list를 builder 패턴으로 객체 생성
        GetDataListRes getDataListRes = GetDataListRes.builder().links(list).build();
        return getDataListRes;
    }

    // 라이브러리 메소드
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

//        // 해당하는 자료가 없으면
//        if(link == null){
//            throw new BaseException(LINK_NOT_EXIST);
//        }

        // 해당 유저가 만든 자료가 아니라면
        if(!link.checkLinkAndUserCorrect(userId)){
            throw new BaseException(LINCK_AND_USER_NOT_CORRECK);
        }

        linkRepository.delete(link);
    }

    @Transactional(readOnly = true)
    public Link findLinkOne(Long linkId){
        Optional<Link> optLink = linkRepository.findById(linkId);
        return optLink.orElseThrow(() -> new BaseException(LINK_NOT_EXIST));
    }
}
