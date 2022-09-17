package j2kb.ponicon.scrap.category;

import j2kb.ponicon.scrap.category.dto.GetCategoryListRes;
import j2kb.ponicon.scrap.category.dto.PostCategoryReq;
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
     * [POST] /category?id=
     * @param postCategoryReq, userId
     */
    @PostMapping()
    public BaseResponse categorySave(@RequestBody @Valid PostCategoryReq postCategoryReq, @RequestParam("id") Long userId) {
        categoryService.categorySave(postCategoryReq, userId);
        return new BaseResponse("카테고리를 생성하였습니다.");
    }
    /**
     * 카테고리 조회 API
     * [GET] /category/all?id=
     * @param userId
     */
    @GetMapping("/all")
    public BaseResponse<?> categoryListByUser(@RequestParam("id")Long userId) {
        GetCategoryListRes list = categoryService.categories(userId);
        return new BaseResponse<>(list);
    }
}
