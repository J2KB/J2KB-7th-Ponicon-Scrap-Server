package j2kb.ponicon.scrap.data;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import j2kb.ponicon.scrap.data.dto.GetDataListRes;
import j2kb.ponicon.scrap.data.dto.PostDataSaveRes;
import j2kb.ponicon.scrap.data.dto.PostUrlReq;
import j2kb.ponicon.scrap.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "링크와 관련된 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/data")
public class LinkController {

    private final LinkServiceImpl linkService;

    /**
     * 링크 등록 API
     * UserId와 categoryId를 RequestParam으로 받아서 linkService.linkSave 후 링크를 생성하는 역할을 합니다.
     * @valid 어노테이션으로 PostUrlReq의 validation check를 진행합니다.
     * @ exception : UnknownHostException, MalformedURLException, DataIntegrityViolationException
     * [POST] /data?id=&category=
     * @param postUrlReq, userId, categoryId
     * @author 박현성
     */
    @ApiOperation(value = "링크 등록 API", notes = "UserId와 categoryId를 RequestParam으로 받아서 linkService.linkSave 후 링크를 생성하는 역할을 합니다. /data?id=&category=")
    @PostMapping()
    public BaseResponse<PostDataSaveRes> dataSave(@RequestBody @Valid PostUrlReq postUrlReq, @ApiParam(value = "User의 id 값", example = "2") @RequestParam("id") Long userId, @ApiParam(value = "카테고리의 id 값", example = "2") @RequestParam("category") Long categoryId) throws Exception {
        PostDataSaveRes postDataSaveRes = linkService.linkSave(postUrlReq, userId, categoryId);
        return new BaseResponse<PostDataSaveRes>(postDataSaveRes);
    }
    /**
     * 링크 조회 API
     * UserId와 categoryId를 RequestParam으로 받아서 linkService.links 후 링크를 조회하는 역할을 합니다.
     * [GET] /category?id=&category=&seq=
     * @param userId, categoryId
     * @author 박현성
     */
    @ApiOperation(value = "링크 조회 API", notes = "UserId와 categoryId를 RequestParam으로 받아서 linkService.links 후 링크를 조회하는 역할을 합니다. /category?id=&category=&seq=")
    @GetMapping()
    public BaseResponse<?> dataListByUserAndCategory(@ApiParam(value = "User의 id 값", example = "2") @RequestParam("id") Long userId, @ApiParam(value = "카테고리의 id 값", example = "2") @RequestParam("category") Long categoryId, @ApiParam(value = "seq의 값", example = "asc") @RequestParam("seq") String seq) {
        GetDataListRes list = linkService.links(userId, categoryId, seq);
        return new BaseResponse<>(list);
    }
    /**
     * 링크 전체 조회 API
     * UserId를 RequestParam으로 받아서 linkService.allLinks 후 링크를 조회하는 역할을 합니다.
     * [GET] /category/all?id=
     * @param userId
     * @author 박현성
     */
    @ApiOperation(value = "링크 전체 조회 API", notes = "UserId를 RequestParam으로 받아서 linkService.allLinks 후 링크를 조회하는 역할을 합니다. /category/all?id=")
    @GetMapping("/all")
    public BaseResponse<?> dataListAllByUser(@ApiParam(value = "User의 id 값", example = "2") @RequestParam("id") Long userId) {
        GetDataListRes list = linkService.allLinks(userId);
        return new BaseResponse<>(list);
    }
}
