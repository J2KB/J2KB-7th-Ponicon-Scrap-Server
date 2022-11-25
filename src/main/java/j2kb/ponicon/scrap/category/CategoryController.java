package j2kb.ponicon.scrap.category;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import j2kb.ponicon.scrap.category.dto.*;
import j2kb.ponicon.scrap.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "카테고리와 관련된 API")
@RestController
@RequiredArgsConstructor
@Slf4j
//@RequestMapping("/category")
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
    @PostMapping("/auth/category")
    public BaseResponse<PostCategorySaveRes> categorySave(@ApiParam(value = "User의 id 값", example = "2") @RequestBody @Valid PostCategorySaveReq postCategoryReq, @RequestParam("id") Long userId) {
        log.info("카테고리 등록 시도: 카테고리 이름={}, 유저 idx={}", postCategoryReq, userId);
        PostCategorySaveRes postCategorySaveRes = categoryService.categorySave(postCategoryReq, userId);
        log.info("카테고리 등록 성공: 카테고리 idx={}, 유저 idx={}", postCategorySaveRes.getCategoryId(), userId);
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
    @GetMapping("/auth/category/all")
    public BaseResponse<GetCategoryListRes> categoryListByUser(@ApiParam(value = "User의 id 값", example = "2") @RequestParam("id")Long userId) {
        log.info("카테고리 조회 시도: 카테고리 유저 idx={}", userId);
        GetCategoryListRes list = categoryService.categories(userId);
        log.info("카테고리 조회 성공: 카테고리 유저 idx={}", userId);
        return new BaseResponse<>(list);
    }

    /**
     * 카테고리 삭제 API
     * UserId를 RequestParam으로 받아서 categoryService.categoryDelete 후 카테고리를 삭제하는 역할을 합니다.
     * [GET] /category?category=
     * @param categoryId
     * @author 박현성
     */
    @ApiOperation(value = "카테고리 삭제 API", notes = "CategoryId를 RequestParam으로 받아서 DeleteCategory 후 카테고리를 삭제하는 역할을 합니다. /category/category=")
    @DeleteMapping("/auth/category")
    public BaseResponse deleteCategory(@ApiParam(value = "Category의 id 값", example = "2") @RequestParam("category")Long categoryId) {
        log.info("카테고리 삭제 시도: 카테고리 idx={}", categoryId);
        categoryService.categoryDelete(categoryId);
        log.info("카테고리 삭제 성공: 카테고리 idx={}", categoryId);
        return new BaseResponse("카테고리 삭제에 성공했습니다");
    }

    /**
     * 카테고리 수정 API
     * UserId를 RequestParam으로 받아서 categoryService.updateCategory 후 카테고리를 수정하는 역할을 합니다.
     * [PUT] /category?category=
     * @param categoryId
     * @author 박현성
     */
    @ApiOperation(value = "카테고리 수정 API", notes = "CategoryId를 RequestParam으로 받아서 UpdateCategory 후 카테고리를 삭제하는 역할을 합니다. /category/category=")
    @PutMapping("/auth/category")
    public BaseResponse<UpdateCategoryRes> updateCategory(@ApiParam(value = "Category의 id 값", example = "2") @RequestBody @Valid UpdateCategoryReq updateCategoryReq, @RequestParam("category")Long categoryId) {
        log.info("카테고리 수정 시도: 카테고리 이름={}", categoryId);
        UpdateCategoryRes updateCategoryRes = categoryService.updateCategory(updateCategoryReq,categoryId);
        log.info("카테고리 수정 완료: 카테고리 idx={}", updateCategoryRes.getCategoryId());
        return new BaseResponse<>(updateCategoryRes);
    }

    /**
     * 카테고리 순서 수정 API
     * startIdx와 endIdx RequestParam으로 받아서 categoryService.DeleteCategory 후 카테고리를 수정하는 역할을 합니다.
     * [PUT] /category?category=
     * @param
     * @author 박현성
     */
    @ApiOperation(value = "카테고리 수정 API", notes = "CategoryId를 RequestParam으로 받아서 UpdateCategory 후 카테고리를 삭제하는 역할을 합니다. /category/category=")
    @PutMapping("/auth/category/all")
    public BaseResponse updateIdxCategory(@ApiParam(value = "Category의 id 값", example = "2") @RequestBody UpdateIdxCategoryReq updateIdxCategoryReq, @RequestParam("id") Long userId) {
        log.info("카테고리 순서 수정 시도: 카테고리 start={}, 카테고리 end={}, 유저 idx={}", updateIdxCategoryReq.getStartIdx(), updateIdxCategoryReq.getEndIdx(), userId);
        categoryService.updateIdxCategory(updateIdxCategoryReq, userId);
        log.info("카테고리 순서 수정 완료: 유저 idx={}", userId);
        return new BaseResponse<>("요청에 성공했습니다");
    }
}
