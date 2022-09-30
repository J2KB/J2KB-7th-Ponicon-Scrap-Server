package j2kb.ponicon.scrap.category;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import j2kb.ponicon.scrap.category.dto.PostCategorySaveRes;
import j2kb.ponicon.scrap.category.dto.GetCategoryListRes;
import j2kb.ponicon.scrap.category.dto.PostCategorySaveReq;
import j2kb.ponicon.scrap.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "카테고리와 관련된 API")
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
    @ApiOperation(value = "카테고리 등록 API", notes = "UserId를 RequestParam으로 받아서 categoryService.categorySave 후 카테고리를 생성하는 역할을 합니다. /category?id=")
    @PostMapping()
    public BaseResponse<PostCategorySaveRes> categorySave(@ApiParam(value = "User의 id 값", example = "2") @RequestBody @Valid PostCategorySaveReq postCategoryReq, @RequestParam("id") Long userId) {
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
    @ApiOperation(value = "카테고리 조회 API", notes = "UserId를 RequestParam으로 받아서 categoryService.categories 후 카테고리를 조회하는 역할을 합니다. /category/all?id=")
    @GetMapping("/all")
    public BaseResponse<GetCategoryListRes> categoryListByUser(@ApiParam(value = "User의 id 값", example = "2") @RequestParam("id")Long userId) {
        GetCategoryListRes list = categoryService.categories(userId);
        return new BaseResponse<>(list);
    }
}
