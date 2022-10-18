package j2kb.ponicon.scrap.category;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import j2kb.ponicon.scrap.category.dto.*;
import j2kb.ponicon.scrap.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "카테고리와 관련된 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/category")
public class CategoryController {

    // @Autowired 보다는 생성자 주입 방식으로 DI를 진행했습니다.
    private final CategoryServiceImpl categoryService;

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
    /**
     * 카테고리 삭제 API
     * UserId를 RequestParam으로 받아서 categoryService.DeleteCategory 후 카테고리를 삭제하는 역할을 합니다.
     * [GET] /category?category=
     * @param categoryId
     * @author 박현성
     */
    @ApiOperation(value = "카테고리 삭제 API", notes = "CategoryId를 RequestParam으로 받아서 DeleteCategory 후 카테고리를 삭제하는 역할을 합니다. /category/category=")
    @DeleteMapping()
    public void deleteCategory(@ApiParam(value = "Category의 id 값", example = "2") @RequestParam("category")Long categoryId) {
        categoryService.categoryDelete(categoryId);
    }
    /**
     * 카테고리 수정 API
     * UserId를 RequestParam으로 받아서 categoryService.DeleteCategory 후 카테고리를 수정하는 역할을 합니다.
     * [PUT] /category?category=
     * @param categoryId
     * @author 박현성
     */
    @ApiOperation(value = "카테고리 수정 API", notes = "CategoryId를 RequestParam으로 받아서 UpdateCategory 후 카테고리를 삭제하는 역할을 합니다. /category/category=")
    @PutMapping()
    public BaseResponse<UpdateCategoryRes> updateCategory(@ApiParam(value = "Category의 id 값", example = "2") @RequestBody @Valid UpdateCategoryReq updateCategoryReq, @RequestParam("category")Long categoryId) {
        UpdateCategoryRes updateCategoryRes = categoryService.updateCategory(updateCategoryReq,categoryId);
        return new BaseResponse<>(updateCategoryRes);
    }
}
