package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.category.dto.PostCategorySaveRes;
import j2kb.ponicon.scrap.category.dto.GetCategoryListRes;
import j2kb.ponicon.scrap.category.dto.PostCategorySaveReq;
import j2kb.ponicon.scrap.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    // @Autowired 보다는 생성자 주입 방식으로 DI를 진행했습니다.
    private final CategoryService categoryService;

    /**
     * 카테고리 등록 API
     * UserId를 RequestParam으로 받아서 categoryService.categorySave 후 카테고리를 생성하는 역할을 합니다.
     * @valid 어노테이션으로 PostCategoryReq의 validation check를 진행합니다.
     * @ exception : ConstraintViolationException, MethodArgumentNotValidException
     * [POST] /category?id=
     * @param postCategoryReq, userId
     * @author 박현성
     */
    @PostMapping()
    public BaseResponse<PostCategorySaveRes> categorySave(@RequestBody @Valid PostCategorySaveReq postCategoryReq, @RequestParam("id") Long userId) {
        PostCategorySaveRes postCategorySaveRes = categoryService.categorySave(postCategoryReq, userId);
        return new BaseResponse<>(postCategorySaveRes);
    }
    /**
     * 카테고리 조회 API
     * UserId를 RequestParam으로 받아서 categoryService.categories 후 카테고리를 조회하는 역할을 합니다.
     * [GET] /category/all?id=
     * @param userId
     * @author 박현성
     */
    @GetMapping("/all")
    public BaseResponse<GetCategoryListRes> categoryListByUser(@RequestParam("id")Long userId) {
        GetCategoryListRes list = categoryService.categories(userId);
        return new BaseResponse<>(list);
    }
}
