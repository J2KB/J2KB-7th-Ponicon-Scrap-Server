package j2kb.ponicon.scrap.data;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Parameter;
import j2kb.ponicon.scrap.data.dto.*;
import j2kb.ponicon.scrap.domain.Link;
import j2kb.ponicon.scrap.response.BaseResponse;
import j2kb.ponicon.scrap.response.validationSequence.ValidationSequence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "링크와 관련된 API")
@RestController
@Slf4j
@RequiredArgsConstructor
//@RequestMapping("/data")
public class LinkController {

    private final LinkService linkService;

    /**
     * 링크 등록 API
     * UserId와 categoryId를 RequestParam으로 받아서 linkService.linkSave 후 링크를 생성하는 역할을 합니다.
     * @valid 어노테이션으로 PostUrlReq의 validation check를 진행합니다.
     * @ exception : UnknownHostException, MalformedURLException, DataIntegrityViolationException
     * [POST] /data?id=&category=
     * @param postDataSaveReq, userId, categoryId
     * @author 박현성
     */
    @ApiOperation(value = "링크 등록 API", notes = "UserId와 categoryId를 RequestParam으로 받아서 linkService.linkSave 후 링크를 생성하는 역할을 합니다. /data?id=&category=")
    @PostMapping("/data")
    public BaseResponse<PostDataSaveRes> dataSave(@RequestBody @Valid PostDataSaveReq postDataSaveReq, @ApiParam(value = "User의 id 값", example = "2") @RequestParam("id") Long userId, @ApiParam(value = "카테고리의 id 값", example = "2") @RequestParam("category") Long categoryId) throws Exception {
        PostDataSaveRes postDataSaveRes = linkService.linkSave(postDataSaveReq, userId, categoryId);
        return new BaseResponse<PostDataSaveRes>(postDataSaveRes);
    }
    /**
     * 링크 조회 API
     * UserId와 categoryId를 RequestParam으로 받아서 linkService.links 후 링크를 조회하는 역할을 합니다.
     * [GET] /data?id=&category=
     * @param userId, categoryId
     * @author 박현성
     */
    @ApiOperation(value = "링크 조회 API", notes = "UserId와 categoryId를 RequestParam으로 받아서 linkService.links 후 링크를 조회하는 역할을 합니다. /category?id=&category=&seq=")
    @GetMapping("/auth/data")
    public BaseResponse<?> dataListByUserAndCategory(@ApiParam(value = "User의 id 값", example = "2") @RequestParam("id") Long userId, @ApiParam(value = "카테고리의 id 값", example = "2") @RequestParam("category") Long categoryId) {
        GetDataListRes list = linkService.links(userId, categoryId);
        return new BaseResponse<>(list);
    }
    /**
     * 링크 전체 조회 API
     * UserId를 RequestParam으로 받아서 linkService.allLinks 후 링크를 조회하는 역할을 합니다.
     * [GET] /data/all?id=
     * @param userId
     * @author 박현성
     */
    @ApiOperation(value = "링크 전체 조회 API", notes = "UserId를 RequestParam으로 받아서 linkService.allLinks 후 링크를 조회하는 역할을 합니다. /category/all?id=")
    @GetMapping("/auth/data/all")
    public BaseResponse<?> dataListAllByUser(@Parameter(description = "User의 id 값", example = "2") @RequestParam("id") Long userId) {
        GetDataListRes list = linkService.allLinks(userId);
        return new BaseResponse<>(list);
    }

    // 링크 삭제
    @DeleteMapping("/auth/data/{user_id}")
    public BaseResponse deleteLink(@PathVariable(name = "user_id") Long userId, @RequestParam(name = "link_id") Long linkId){

        linkService.deleteLink(userId, linkId);

        return new BaseResponse("자료 삭제에 성공했습니다");
    }

    // 링크 수정 (카테고리 수정)
    @PatchMapping("/auth/data/{user_id}")
    public BaseResponse<PatchLinkRes> updateLink(@PathVariable(name = "user_id") Long userId, @RequestParam(name = "link_id") Long linkId, @Validated(ValidationSequence.class) @RequestBody PatchLinkReq patchLinkReq){
        Link updateLink = linkService.updateLink(userId, linkId, patchLinkReq);

        PatchLinkRes patchLinkRes = PatchLinkRes.builder()
                .linkId(updateLink.getId())
                .categoryId(updateLink.getCategory().getId())
                .url(updateLink.getLink())
                .title(updateLink.getTitle())
                .imgUrl(updateLink.getImgUrl())
                .domain(updateLink.getDomain())
                .build();

        return new BaseResponse<>("자료 수정에 성공했습니다", patchLinkRes);
    }
}
