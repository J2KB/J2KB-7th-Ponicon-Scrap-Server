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
        log.info("링크 등록 시도: 링크 url={}, 링크 이미지 url={}, 링크 제목={}, 유저 idx={}, 카테고리 idx={}", postDataSaveReq.getLink(), postDataSaveReq.getImgUrl(), postDataSaveReq.getTitle(), userId, categoryId);
        PostDataSaveRes postDataSaveRes = linkService.linkSave(postDataSaveReq, userId, categoryId);
        log.info("링크 등록 완료: 링크 url={}, 링크 이미지 url={}, 링크 제목={}, 유저 idx={}, 카테고리 idx={}", postDataSaveReq.getLink(), postDataSaveReq.getImgUrl(), postDataSaveReq.getTitle(), userId, categoryId);
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
        log.info("링크 조회 시도: 유저 idx={}, 카테고리 idx={}", userId, categoryId);
        GetDataListRes list = linkService.links(userId, categoryId);
        log.info("링크 조회 완료: 유저 idx={}, 카테고리 idx={}", userId, categoryId);
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
        log.info("링크 전체 조회 시도: 유저 idx={}", userId);
        GetDataListRes list = linkService.allLinks(userId);
        log.info("링크 전체 조회 완룐: 유저 idx={}", userId);
        return new BaseResponse<>(list);
    }

    /**
     * 자료 삭제
     * [DELETE] /auth/data/{user_id}?link_id
     * @param userId
     * @param linkId
     * @return
     */
    @DeleteMapping("/auth/data/{user_id}")
    public BaseResponse deleteLink(@PathVariable(name = "user_id") Long userId, @RequestParam(name = "link_id") Long linkId){

        log.info("자료 삭제 시도: user={}, link={}", userId, linkId);
        linkService.deleteLink(userId, linkId);

        log.info("자료 삭제 성공: user={}, link={}", userId, linkId);
        return new BaseResponse("자료 삭제에 성공했습니다");
    }

    /**
     * 링크 수정 (카테고리 수정)
     * [PATCH] /auth/data/{user_id}?link_id=
     * @param userId
     * @param linkId
     * @param patchLinkReq
     * @return
     */
    @PatchMapping("/auth/data/{user_id}")
    public BaseResponse<PatchLinkRes> updateLink(@PathVariable(name = "user_id") Long userId, @RequestParam(name = "link_id") Long linkId, @Validated(ValidationSequence.class) @RequestBody PatchLinkReq patchLinkReq){

        log.info("자료 수정 시도: user={}, link={}", userId, linkId);

        PatchLinkRes patchLinkRes = linkService.updateLink(userId, linkId, patchLinkReq);

        log.info("자료 수정 성공: idx={}, link={}", userId, linkId);
        return new BaseResponse<>("자료 수정에 성공했습니다", patchLinkRes);
    }
}
