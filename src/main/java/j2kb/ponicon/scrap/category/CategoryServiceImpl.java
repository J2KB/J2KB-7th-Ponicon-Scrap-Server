package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.category.dto.*;
import j2kb.ponicon.scrap.data.LinkRepository;
import j2kb.ponicon.scrap.domain.Category;
import j2kb.ponicon.scrap.domain.User;
import j2kb.ponicon.scrap.response.BaseException;
import j2kb.ponicon.scrap.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static j2kb.ponicon.scrap.response.BaseExceptionStatus.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    private final LinkRepository linkRepository;

    // 카테고리 저장
    @Transactional
    @Override
    public PostCategorySaveRes categorySave(PostCategorySaveReq postCategoryReq, Long userId) {
        // userRepository에서 userId로 조회 후 user에 UserName을 가져온다.
        Optional<User> tempUser = userRepository.findById(userId);
        User user = tempUser.get();

        // postCategoryReq에서 카테고리 이름을 가져옴
        String name = postCategoryReq.getName();

        // 자동으로 order값 늘려줌
        int order = user.getCategories().size();

        // 카테고리 이름, order, 유저
        Category category = new Category(name, order, user);

        // categoryRepository.save(category)로 카테고리 저장
        Category saveCategory = categoryRepository.save(category);

        // PostCategorySaveRes를 builder 패턴으로 객체 생성
        PostCategorySaveRes postCategorySaveRes = PostCategorySaveRes.builder().categoryId(saveCategory.getId()).build();
        return postCategorySaveRes;
    }
    // 모든 카테고리 조회
    @Transactional(readOnly = true)
    @Override
    public GetCategoryListRes categories(Long userId) {
        List<CategoryListRes> list = categoryRepository.findByUserId(userId, Sort.by(Sort.Direction.ASC, "order")).stream() // categoryRepository에서 넘어온 결과를
                .map(Category::toDto)          // Stream을 통해 map으로 toDto에 매핑 해준다.
                .collect(Collectors.toList()); // collect를 사용해서 List로 변환한다.

        /** numOfLink를 구하기 위해 list 사이즈 만큼 for문을 수행하는데
         * linkRepository.countByCategoryId에서 카테고리id를 얻기 위해
         * 다시 list.get(i).getCategoryId로 카테고리 id를 가져온다.
         * 문제점 해당하는 카테고리의 아이디만큼 카운트 쿼리문이 실행된다.
         */
        int count = list.size(); // list의 size를 count에 담아준다.
        log.info("count ={} ", count);
        // 모든 자료 갯수 구해서 set 해준다.
        list.get(0).setNumOfLink(linkRepository.countByUserId(userId));

        // 카테고리에 속한 자료 갯수를 구한다.
        for(int i = 1; i < count; i++) {
            // 카테고리 아이디와 유저 아이디를 이용해 해당 카테고리의 속한 자료 갯수를 구한다.
            list.get(i).setNumOfLink(linkRepository.countByCategoryIdAndUserId(list.get(i).getCategoryId(), userId));
        }

        // list를 builder 패턴으로 객체 생성
        GetCategoryListRes getCategoryListRes = GetCategoryListRes.builder().categories(list).build();
        return getCategoryListRes;
    }

    // 기본 카테고리 저장
    @Transactional
    @Override
    public void saveBasicCategory(User user){
        List<String> categoryNames = new ArrayList<>(List.of("전체 자료", "분류되지 않은 자료"));
        // 위의 list의 크기만큼 for문이 반복됨
        for(int i=0; i<categoryNames.size(); i++){
            new Category(categoryNames.get(i), i, user);
            //따로 카테고리 저장 안하더라도 Cascade 설정 해둬서 자동으로 insert 됨.
        }
    }
    // 카테고리 하나 조회
    @Transactional(readOnly = true)
    @Override
    public Category findCategoryOne(Long categoryId) {
        Optional<Category> optLink = categoryRepository.findById(categoryId);
        // 해당 하는 자료가 없으면 에러 발생시키기.
        return optLink.orElseThrow(() -> {
            log.info("카테고리 한건 조회 중 에러: {}, categoryId={}", DUPULICATE_EMAIL.getMessage(), categoryId);
            return new BaseException(CATEGORY_NOT_EXIST);
        }
        );
    }
    
    // 카테고리 삭제
    @Transactional
    @Override
    public void categoryDelete(Long categoryId) {
        categoryRepository.deleteAllById(categoryId);
    }
    // 카테고리 수정
    @Transactional
    @Override
    public UpdateCategoryRes updateCategory(UpdateCategoryReq updateCategoryReq, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.info("카테고리 수정 중 에러: {}, categoryId={}", DUPULICATE_EMAIL.getMessage(), categoryId);
                return new BaseException(CATEGORY_NOT_EXIST);
                }
        );
        // updateCategoryReq에서 name을 가져온다.
        String name = updateCategoryReq.getName();
        // updateCategoryReq에서 가져온 name을 updateCategory에 담는다.
        category.updateCategory(name);

        // UpdateCategoryRes를 builder 패턴으로 객체 생성
        UpdateCategoryRes updateCategoryRes = UpdateCategoryRes.builder().categoryId(categoryId).build();

        return updateCategoryRes;
    }
    // 카테고리 순서 수정
    @Transactional
    @Override
    public void updateIdxCategory(UpdateIdxCategoryReq updateIdxCategoryReq, Long userId) {
        List<Category> list = categoryRepository.findByUserId(userId, Sort.by(Sort.Direction.ASC, "order"));

        // startIdx = 옮길 카테고리
        int startIdx = updateIdxCategoryReq.getStartIdx();

        // endIdx = 위치 시키고 싶은 위치
        int endIdx = updateIdxCategoryReq.getEndIdx();

        // 리스트에서 옮길 카테고리를 가져와 order 번호를 위치 시키고 싶은 위치로 변경한다.
        if(startIdx > endIdx) {
            list.get(startIdx).updateOrder(endIdx);
            for (int i = endIdx; i < startIdx; i++) {
                list.get(i).updateOrder(i + 1);
            }
        }
        if (startIdx < endIdx) {
            list.get(startIdx).updateOrder(endIdx - 1);
            for(int i = startIdx + 1; i < endIdx; i++) {
                list.get(i).updateOrder(i - 1);
            }
        }
    }
}