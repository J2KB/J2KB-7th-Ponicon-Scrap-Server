package j2kb.ponicon.scrap.data;

import j2kb.ponicon.scrap.category.dto.UpdateCategoryReq;
import j2kb.ponicon.scrap.category.dto.UpdateCategoryRes;
import j2kb.ponicon.scrap.data.dto.*;
import j2kb.ponicon.scrap.domain.Link;

public interface LinkService {
    public PostDataSaveRes linkSave(PostDataSaveReq postDataSaveReq, Long userId, Long categoryId) throws Exception;

    public GetDataListRes links(Long userId, Long categoryId);

    public GetDataListRes allLinks(Long userId);

    /**
     * 자료 삭제
     * @param userId
     * @param linkId
     */
    public void deleteLink(Long userId, Long linkId);

    /**
     * 자료 수정
     * @param userId
     * @param linkId
     * @param patchLinkReq
     * @return
     */
    public PatchLinkRes updateLink(Long userId, Long linkId, PatchLinkReq patchLinkReq);

    public PutLinkRes putLinkRes(Long userId, Long linkId, PutLinkReq putLinkReq);

    // 자료 즐겨찾기 추가/삭제
    public PatchBookmarkLinkRes bookmark(Long userId, Long linkId);
}
