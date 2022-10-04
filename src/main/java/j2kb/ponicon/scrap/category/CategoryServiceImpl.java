package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.category.dto.CategoryListRes;
import j2kb.ponicon.scrap.category.dto.PostCategorySaveRes;
import j2kb.ponicon.scrap.category.dto.GetCategoryListRes;
import j2kb.ponicon.scrap.category.dto.PostCategorySaveReq;
import j2kb.ponicon.scrap.data.LinkRepository;
import j2kb.ponicon.scrap.domain.Category;
import j2kb.ponicon.scrap.domain.User;
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


@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    private final LinkRepository linkRepository;

    @Transactional
    public PostCategorySaveRes categorySave(PostCategorySaveReq postCategoryReq, Long userId) {
        // userRepository에서 userId로 조회 후 user에 UserName을 가져온다.
        Optional<User> tempUser = userRepository.findById(userId);
        User user = tempUser.get();

        // postCategoryReq에서 카테고리 이름을 가져옴
        String name = postCategoryReq.getName();

        // 자동으로 order값 늘려줌
        int order = user.getCategories().size();

        Category category = new Category(name, order, user);
        Category saveCategory = categoryRepository.save(category);

        PostCategorySaveRes postCategorySaveRes = PostCategorySaveRes.builder().categoryId(saveCategory.getId()).build();

        return postCategorySaveRes;
    }

    @Transactional(readOnly = true)
    public GetCategoryListRes categories(Long userId) {
        //System.out.println("no = " + no);
        List<CategoryListRes> list = categoryRepository.findByUserId(userId, Sort.by(Sort.Direction.ASC, "order")).stream() // categoryRepository에서 넘어온 결과를
                .map(Category::toDto)          // Stream을 통해 map으로 toDto에 매핑 해준다.
                .collect(Collectors.toList()); // collect를 사용해서 List로 변환한다.

        /** numOfLink를 구하기 위해 list 사이즈 만큼 for문을 수행하는데
         * linkRepository.countByCategoryId에서 카테고리id를 얻기 위해
         * 다시 list.get(i).getCategoryId로 카테고리 id를 가져온다.
         * 문제점 해당하는 카테고리의 아이디만큼 카운트 쿼리문이 실행된다.
         */
        //int count = list.size();

        // for each
        for(CategoryListRes i : list) {
            i.setNumOfLink(linkRepository.countByCategoryId(i.getCategoryId()));
        }
//        for(int i=0; i< count; i++) {
//            list.get(i).setNumOfLink(linkRepository.countByCategoryId(list.get(i).getCategoryId()));
//        }

        // list를 builder 패턴으로 객체 생성
        GetCategoryListRes getCategoryListRes = GetCategoryListRes.builder().categories(list).build();
        return getCategoryListRes;
    }

    // 기본 카테고리 저장
    @Transactional
    public void saveBasicCategory(User user){
        List<String> categoryNames = new ArrayList<>(List.of("분류되지 않은 자료"));

        for(int i=0; i<categoryNames.size(); i++){
            new Category(categoryNames.get(i), i, user);
            //따로 카테고리 저장 안하더라도 Cascade 설정 해둬서 자동으로 insert 됨.
        }
    }
}